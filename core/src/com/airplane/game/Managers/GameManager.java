package com.airplane.game.Managers;

import com.airplane.game.AirplaneGame;
import com.airplane.game.GameObjects.Plane;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameManager {

    private static TextureAtlas atlas;
    private static TextureRegion terrainBelow;
    public static float terrainOffset;
    private static TextureRegion backGroundRegion;

    public static void initialize(float width, float height){

    atlas = new TextureAtlas(Gdx.files.internal("Airplane.pack"));
    backGroundRegion = atlas.findRegion("background");
    terrainBelow = atlas.findRegion("groundSnow");


    Plane.initialize(width, height);
    TextManager.initialize(width,height);

    }

    public static void renderGame(SpriteBatch batch) {

        batch.disableBlending(); // blending - смешивание
        batch.draw(backGroundRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // отрисовываем задний фон
        batch.enableBlending(); // blending - смешивание
        batch.draw(terrainBelow, terrainOffset, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 7); // отрисовываем первый ландшафт по ширине экрана
        batch.draw(terrainBelow, terrainOffset + Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 7); // отрисовываем второй ландшафт, "пркрепляя его ко второму"

        TextManager.displayMessage(batch);

    }

    public static void updateScene(){

        terrainOffset -= Plane.planePosition.x - Plane.planeDefaultPosition.x;
        Plane.planePosition.x = Plane.planeDefaultPosition.x;
        if (terrainOffset*-1> Gdx.graphics.getWidth()){
            terrainOffset = 0;
        }
        if (terrainOffset>0){
            terrainOffset =- Gdx.graphics.getWidth();
        }

    }


    public static void dispose(){

        atlas.dispose();

    }

}
