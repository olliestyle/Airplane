package com.airplane.game.GameObjects;

import com.airplane.game.Managers.GameManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static com.airplane.game.Managers.GameManager.atlas;

public class Terrain {

    public static TextureRegion terrainBelow;
    public static TextureRegion terrainAbove;
    public static float terrainOffset;
    public static Rectangle terrainAboveRectangle1;
    public static Rectangle terrainAboveRectangle2;
    public static Rectangle terrainAboveRectangle3;
    public static Rectangle terrainAboveRectangle4;
    public static Rectangle terrainAboveRectangle5;
    public static Rectangle terrainAboveRectangle6;
    public static Rectangle terrainAboveRectangle7;
    public static Rectangle terrainAboveRectangle8;
    public static Rectangle terrainAboveRectangle9;
    public static Rectangle terrainBelowRectangle1;
    public static Rectangle terrainBelowRectangle2;
    public static Rectangle terrainBelowRectangle3;
    public static Rectangle terrainBelowRectangle4;
    public static Rectangle terrainBelowRectangle5;
    public static Rectangle terrainBelowRectangle6;
    public static Rectangle terrainBelowRectangle7;
    public static Rectangle terrainBelowRectangle8;
    public static Rectangle terrainBelowRectangle9;
    private static Texture testOverlapsTerrain1;
    private static Texture testOverlapsTerrain2;

    public static void initializeTerrain(){

        terrainBelow = atlas.findRegion("groundSnow");
        terrainAbove = new TextureRegion(terrainBelow);
        terrainAbove.flip(true,true);
        terrainAboveRectangle1 = new Rectangle();
        terrainAboveRectangle2 = new Rectangle();
        terrainAboveRectangle3 = new Rectangle();
        terrainAboveRectangle4 = new Rectangle();
        terrainAboveRectangle5 = new Rectangle();
        terrainAboveRectangle6 = new Rectangle();
        terrainAboveRectangle7 = new Rectangle();
        terrainAboveRectangle8 = new Rectangle();
        terrainAboveRectangle9 = new Rectangle();
        terrainBelowRectangle1 = new Rectangle();
        terrainBelowRectangle2 = new Rectangle();
        terrainBelowRectangle3 = new Rectangle();
        terrainBelowRectangle4 = new Rectangle();
        terrainBelowRectangle5 = new Rectangle();
        terrainBelowRectangle6 = new Rectangle();
        terrainBelowRectangle7 = new Rectangle();
        terrainBelowRectangle8 = new Rectangle();
        terrainBelowRectangle9 = new Rectangle();
        testOverlapsTerrain1 = new Texture(Gdx.files.internal("testoverlaps.png"));
        testOverlapsTerrain2 = new Texture(Gdx.files.internal("testoverlaps.png"));
    }

    public static void renderTerrain(SpriteBatch batch){

        batch.draw(terrainBelow, terrainOffset, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 9); // отрисовываем первый ландшафт  по ширине экрана
        batch.draw(terrainBelow, terrainOffset + Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 9); // отрисовываем второй ландшафт, "пркрепляя его ко второму"

        batch.draw(terrainAbove, terrainOffset, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 9, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 9); // отрисовываем первый ландшафт по ширине экрана
        batch.draw(terrainAbove, terrainOffset + Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 9, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 9); // отрисовываем второй ландшафт, "пркрепляя его ко второму"

        batch.draw(testOverlapsTerrain1, terrainOffset+ (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5),
                0, (float) (Gdx.graphics.getWidth()/6.8), Gdx.graphics.getHeight()/16);
        batch.draw(testOverlapsTerrain2, terrainOffset + (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5) + (float) (Gdx.graphics.getWidth()/6.8), 0, (float) (Gdx.graphics.getWidth()/10.5), Gdx.graphics.getHeight()/13);

    }

    public static void updateTerrain(){

        terrainOffset -= Plane.planePosition.x - Plane.planeDefaultPosition.x;
        System.out.println("terrainOffset = " + terrainOffset);

        if (terrainOffset * -1 > Gdx.graphics.getWidth()) {
            terrainOffset = 0;
        }
        if (terrainOffset > 0) {
            terrainOffset = -Gdx.graphics.getWidth();
        }

        if (Gdx.graphics.getWidth() <= 800){

        }
        else if (Gdx.graphics.getWidth() > 1280){

        }
        else{
            terrainBelowRectangle1.set(terrainOffset, 0, (float) (Gdx.graphics.getWidth()/5.2), Gdx.graphics.getHeight()/20);
            terrainBelowRectangle2.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2), 0, (float) (Gdx.graphics.getWidth()/8.5), Gdx.graphics.getHeight()/11);
            terrainBelowRectangle3.set(terrainOffset+ (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5), 0, (float) (Gdx.graphics.getWidth()/6.8), Gdx.graphics.getHeight()/16);
            terrainBelowRectangle4.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5) + (float) (Gdx.graphics.getWidth()/6.8), 0, (float) (Gdx.graphics.getWidth()/10.5), Gdx.graphics.getHeight()/13);

        }

        if (isPlaneCollisionWithTerrain()){
            if (GameManager.gameState != GameManager.GameState.GAME_OVER){
                GameManager.gameState = GameManager.GameState.GAME_OVER;
            }
        }

    }

    private static boolean isPlaneCollisionWithTerrain(){
        if(Plane.planeRect.overlaps(terrainAboveRectangle1) || Plane.planeRect.overlaps(terrainAboveRectangle2) || Plane.planeRect.overlaps(terrainAboveRectangle3)
                || Plane.planeRect.overlaps(terrainAboveRectangle4) || Plane.planeRect.overlaps(terrainAboveRectangle5) || Plane.planeRect.overlaps(terrainAboveRectangle6)
                || Plane.planeRect.overlaps(terrainAboveRectangle7) || Plane.planeRect.overlaps(terrainAboveRectangle8) || Plane.planeRect.overlaps(terrainAboveRectangle9)
                || Plane.planeRect.overlaps(terrainBelowRectangle1) || Plane.planeRect.overlaps(terrainBelowRectangle2) || Plane.planeRect.overlaps(terrainBelowRectangle3)
                || Plane.planeRect.overlaps(terrainBelowRectangle4) || Plane.planeRect.overlaps(terrainBelowRectangle5) || Plane.planeRect.overlaps(terrainBelowRectangle6)
                || Plane.planeRect.overlaps(terrainBelowRectangle7) || Plane.planeRect.overlaps(terrainBelowRectangle8) || Plane.planeRect.overlaps(terrainBelowRectangle9)){
            return true;
        }
        return false;
    }



}
