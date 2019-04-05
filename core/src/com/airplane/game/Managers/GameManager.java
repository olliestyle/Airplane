package com.airplane.game.Managers;

import com.airplane.game.Airplane;
import com.airplane.game.AirplaneScene1;
import com.airplane.game.GameObjects.Meteor;
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



public class GameManager {

    //public static TextureAtlas atlas;
    private TextureRegion backGroundRegion;
    public static GameState gameState;
    private Music mainMusic;
    public static Sound tapSound, crashSound, meteorSpawnSound;
    public Terrain terrain;
    public TextManager textManager;
    public Meteor meteor;
    public RockPillar rockPillar;
    Airplane game;
    TextureAtlas atlas;
    OrthographicCamera camera;

    public GameManager(Airplane airplane) {

        game = airplane;
        camera = game.camera;
        atlas = game.atlas;
        terrain = new Terrain(game);
        meteor = new Meteor(game);
        rockPillar = new RockPillar(game);
        textManager = new TextManager();

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

        tapSound = Gdx.audio.newSound(Gdx.files.internal("pop.ogg"));
        crashSound = Gdx.audio.newSound(Gdx.files.internal("crash.ogg"));
        meteorSpawnSound = Gdx.audio.newSound(Gdx.files.internal("alarm.ogg"));

        gameState = GameState.INIT;

        terrain.initializeTerrain();
        textManager.initialize(width,height);
        rockPillar.initializePillar();
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
    }

    public void updateScene(){

        switch (gameState){

            case INIT:

                if(Gdx.input.justTouched()) {
                    gameState = GameState.ACTION;
                }
                break;

            case ACTION:

                rockPillar.updatePillar();
                terrain.updateTerrain();
                Plane.planePosition.x = Plane.planeDefaultPosition.x; // Имитация нахождения самолета на одном месте по x. Самолет стоит на месте. Все остальные объекты перемещаются относительно него
                meteor.updateMeteor();

                break;

            case GAME_OVER:

                break;

            default:
                break;
        }
    }

    public void dispose(){

        //atlas.dispose();
        mainMusic.dispose();
        tapSound.dispose();
        meteorSpawnSound.dispose();
        crashSound.dispose();

    }

}
