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

import java.lang.reflect.Array;


public class GameManager {

    private TextureRegion backGroundRegion;
    public static GameState gameState;
    private Music mainMusic;
    private Terrain terrain;
    private TextManager textManager;
    private Meteor meteor;
    private RockPillar rockPillar;
    private TextureAtlas atlas;
    private PickUpSpawnManager pickUpSpawnManager;

    public GameManager(Airplane airplane) {

        atlas = airplane.atlas;
        terrain = new Terrain(airplane);
        meteor = new Meteor(airplane);
        rockPillar = new RockPillar(airplane);
        textManager = new TextManager();
        pickUpSpawnManager = new PickUpSpawnManager(airplane, rockPillar);

    }

    public enum GameState{
        INIT, ACTION, GAME_OVER
    }

    public void initialize(float width, float height){

        System.out.println("atlas = " + atlas);
        //atlas = new TextureAtlas(Gdx.files.internal("Airplane.pack"));
        backGroundRegion = atlas.findRegion("background");

        mainMusic = Gdx.audio.newMusic(Gdx.files.internal("journey.mp3"));
        mainMusic.setLooping(true);
        mainMusic.play();

        gameState = GameState.INIT;

        terrain.initializeTerrain();
        textManager.initialize(width,height);
        rockPillar.initializePillar();
        System.out.println("rockPillar in GM" + rockPillar);
        meteor.initializeMeteor();

    }

    public void renderGame(SpriteBatch batch) {

        batch.disableBlending(); // blending - смешивание
        batch.draw(backGroundRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // отрисовываем задний фон
        batch.enableBlending(); // blending - смешивание
        rockPillar.renderPillar(batch);
        meteor.renderMeteor(batch);
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
                rockPillar.updatePillar();
                terrain.updateTerrain();
                pickUpSpawnManager.updatePickUp();
                Plane.planePosition.x = Plane.planeDefaultPosition.x; // Имитация нахождения самолета на одном месте по x. Самолет стоит на месте. Все остальные объекты перемещаются относительно него
                meteor.updateMeteor();
                pickUpSpawnManager.checkAndCreatePickUp(Gdx.graphics.getDeltaTime());


                break;

            case GAME_OVER:

                break;

            default:

                break;
        }
    }

    public RockPillar getRockPillar(){
        return rockPillar;
    }

    public void dispose(){

        //atlas.dispose();
        mainMusic.dispose();
    }

}
