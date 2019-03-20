package com.airplane.game.Managers;

import com.airplane.game.AirplaneGame;
import com.airplane.game.GameObjects.Meteor;
import com.airplane.game.GameObjects.Plane;
import com.airplane.game.GameObjects.RockPillar;
import com.airplane.game.GameObjects.Terrain;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;



public class GameManager {

    public static TextureAtlas atlas;
    private static TextureRegion backGroundRegion;
    public static GameState gameState;

    public enum GameState{
        INIT, ACTION, GAME_OVER
    }

    public static void initialize(float width, float height){

        atlas = new TextureAtlas(Gdx.files.internal("Airplane.pack"));
        backGroundRegion = atlas.findRegion("background");
        gameState = GameState.INIT;

        Terrain.initializeTerrain();
        Plane.initialize(width, height);
        TextManager.initialize(width,height);
        RockPillar.initializePillar();
        Meteor.initializeMeteor();

    }

    public static void renderGame(SpriteBatch batch) {

        batch.disableBlending(); // blending - смешивание
        batch.draw(backGroundRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // отрисовываем задний фон
        batch.enableBlending(); // blending - смешивание
        RockPillar.renderPillar(batch);
        Meteor.renderMeteor(batch);
        Terrain.renderTerrain(batch);

        TextManager.displayMessage(batch);

    }

    public static void updateScene(){


        switch (gameState){

            case INIT:

                if(Gdx.input.justTouched()) {
                    gameState = GameState.ACTION;
                }
                break;

            case ACTION:

                Terrain.updateTerrain();
                RockPillar.updatePillar();
                Meteor.updateMeteor();
                Plane.planePosition.x = Plane.planeDefaultPosition.x;

                break;

            case GAME_OVER:

                break;

            default:
                break;
        }
    }

    public static void dispose(){

        atlas.dispose();

    }

}
