package com.airplane.game.Managers;

import com.airplane.game.AirplaneGame;
import com.airplane.game.GameObjects.Meteor;
import com.airplane.game.GameObjects.Plane;
import com.airplane.game.GameObjects.RockPillar;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;



public class GameManager {

    public static TextureAtlas atlas;
    public static TextureRegion terrainBelow;
    public static TextureRegion terrainAbove;
    public static float startPointTerrainAboveY;
    public static float terrainOffset;
    private static TextureRegion backGroundRegion;
    public static GameState gameState;


    public enum GameState{
        INIT, ACTION, GAME_OVER
    }

    public static void initialize(float width, float height){

    atlas = new TextureAtlas(Gdx.files.internal("Airplane.pack"));
    backGroundRegion = atlas.findRegion("background");
    terrainBelow = atlas.findRegion("groundSnow");
    terrainAbove = new TextureRegion(terrainBelow);
    terrainAbove.flip(true,true);
    gameState = GameState.INIT;


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
        batch.draw(terrainBelow, terrainOffset, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 9); // отрисовываем первый ландшафт по ширине экрана
        batch.draw(terrainBelow, terrainOffset + Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 9); // отрисовываем второй ландшафт, "пркрепляя его ко второму"

        batch.draw(terrainAbove, terrainOffset, Gdx.graphics.getHeight() - terrainAbove.getRegionHeight(), Gdx.graphics.getWidth(), terrainAbove.getRegionHeight()); // отрисовываем первый ландшафт по ширине экрана
        batch.draw(terrainAbove, terrainOffset + Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - terrainAbove.getRegionHeight(), Gdx.graphics.getWidth(), terrainAbove.getRegionHeight()); // отрисовываем второй ландшафт, "пркрепляя его ко второму"

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

                terrainOffset -= Plane.planePosition.x - Plane.planeDefaultPosition.x;
                System.out.println("terrainOffset = " + terrainOffset);
                RockPillar.updatePillar();
                Meteor.updateMeteor();
                Plane.planePosition.x = Plane.planeDefaultPosition.x;

                if (terrainOffset * -1 > Gdx.graphics.getWidth()) {
                    terrainOffset = 0;
                }
                if (terrainOffset > 0) {
                    terrainOffset = -Gdx.graphics.getWidth();
                }



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
