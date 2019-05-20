package com.airplane.game.Managers;

import com.airplane.game.Airplane;
import com.airplane.game.AirplaneScene1;
import com.airplane.game.GameObjects.Meteor;
import com.airplane.game.GameObjects.Pickup;
import com.airplane.game.GameObjects.Plane;
import com.airplane.game.GameObjects.RockPillar;
import com.airplane.game.GameObjects.Terrain;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class GameManager3 {

    private TextureRegion backGroundRegion;
    private GameState gameState;
    private Music mainMusic;
    private Terrain terrain;
    private TextManager textManager;
    private Meteor meteor;
    private Meteor meteor2;
    private Meteor meteor3;
    private RockPillar rockPillar;
    private RockPillar rockPillar2;
    private RockPillar rockPillar3;
    private TextureAtlas atlas;
    private PickUpSpawnManager pickUpSpawnManager;
    private Plane plane;
    private Airplane airplane;

    public GameManager3(Airplane airplane, Plane plane) {

        this.airplane = airplane;
        atlas = airplane.atlas;
        this.plane = plane;
        terrain = new Terrain(airplane, plane, this);
        meteor = new Meteor(airplane, this);
        meteor2 = new Meteor(airplane, this);
        meteor3 = new Meteor(airplane, this);
        rockPillar = new RockPillar(airplane, plane, this);
        rockPillar2 = new RockPillar(airplane, plane, this);
        rockPillar3 = new RockPillar(airplane, plane, this);
        pickUpSpawnManager = new PickUpSpawnManager(airplane, rockPillar, plane, this); // нам нужно получить именно ту скалу, которая отрисована на данный момент
        textManager = new TextManager(pickUpSpawnManager, this);
    }

    public enum GameState{
        INIT, ACTION, GAME_OVER
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void initialize(float width, float height){

        System.out.println("atlas = " + atlas);
        //atlas = new TextureAtlas(Gdx.files.internal("Airplane.pack"));
        backGroundRegion = atlas.findRegion("background");

        if (airplane.soundEnabled) {
            mainMusic = Gdx.audio.newMusic(Gdx.files.internal("journey.mp3"));
            mainMusic.setLooping(true);
            mainMusic.play();
        }
        gameState = GameState.INIT;

        terrain.initializeTerrain();
        textManager.initialize(width,height);
        rockPillar.initializePillar();
        rockPillar2.initializePillar();
        rockPillar3.initializePillar();
        System.out.println("rockPillar in GM" + rockPillar);
        meteor.initializeMeteor();
        meteor2.initializeMeteor();
        meteor3.initializeMeteor();

    }

    public void renderGame(SpriteBatch batch) {

        batch.disableBlending(); // blending - смешивание
        batch.draw(backGroundRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // отрисовываем задний фон
        batch.enableBlending(); // blending - смешивание
        rockPillar.renderPillar(batch);
        rockPillar2.renderPillar(batch);
        rockPillar3.renderPillar(batch);
        meteor.renderMeteor(batch);
        meteor2.renderMeteor(batch);
        meteor3.renderMeteor(batch);
        terrain.renderTerrain(batch);
        textManager.displayMessage(batch);
        pickUpSpawnManager.drawPickUp(batch);
    }

    public void updateScene(){

        switch (gameState){

            case INIT:

                if(Gdx.input.justTouched()) {
                    gameState = GameState.ACTION;
                }
                break;

            case ACTION:

                // объекты, движущиеся относительно самолета должны обновляться перед присваиванием дефолтной позиции самолета по x
                rockPillar.updatePillar(pickUpSpawnManager);
                rockPillar2.updatePillar(pickUpSpawnManager);
                rockPillar3.updatePillar(pickUpSpawnManager);
                terrain.updateTerrain();
                pickUpSpawnManager.updatePickUp();
                plane.getPlanePosition().x = plane.getPlaneDefaultPosition().x; // Имитация нахождения самолета на одном месте по x. Самолет стоит на месте. Все остальные объекты перемещаются относительно него
                meteor.updateMeteor(pickUpSpawnManager);
                meteor2.updateMeteor(pickUpSpawnManager);
                meteor3.updateMeteor(pickUpSpawnManager);
                pickUpSpawnManager.checkAndCreatePickUp(Gdx.graphics.getDeltaTime());

                break;

            case GAME_OVER:

                if(Gdx.input.justTouched()){

                    gameState = GameState.INIT;
                    resetScene();
                }
                break;

            default:

                break;
        }
    }

    public void resetScene(){

        meteor.resetMeteor();
        meteor2.resetMeteor();
        meteor3.resetMeteor();
        plane.resetPlane();
        pickUpSpawnManager.resetPickup();
        rockPillar.resetPillar();
        rockPillar2.resetPillar();
        rockPillar3.resetPillar();
    }

    public RockPillar getRockPillar(){
        return rockPillar;
    }

    public void dispose(){

        //atlas.dispose();
        mainMusic.dispose();
    }

}
