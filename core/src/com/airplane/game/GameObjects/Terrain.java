package com.airplane.game.GameObjects;

import com.airplane.game.Managers.GameManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static com.airplane.game.Managers.GameManager.atlas;

public class Terrain {

    protected static TextureRegion terrainBelow;
    protected static TextureRegion terrainAbove;
    public static float terrainOffset;
    private static Rectangle terrainAboveRectangle1;
    private static Rectangle terrainAboveRectangle2;
    private static Rectangle terrainAboveRectangle3;
    private static Rectangle terrainAboveRectangle4;
    private static Rectangle terrainAboveRectangle5;
    private static Rectangle terrainAboveRectangle6;
    private static Rectangle terrainAboveRectangle7;
    private static Rectangle terrainAboveRectangle8;
    private static Rectangle terrainAboveRectangle9;
    private static Rectangle terrainBelowRectangle1;
    private static Rectangle terrainBelowRectangle2;
    private static Rectangle terrainBelowRectangle3;
    private static Rectangle terrainBelowRectangle4;
    private static Rectangle terrainBelowRectangle5;
    private static Rectangle terrainBelowRectangle6;
    private static Rectangle terrainBelowRectangle7;
    private static Rectangle terrainBelowRectangle8;
    private static Rectangle terrainBelowRectangle9;
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

        //batch.draw(testOverlapsTerrain1,terrainOffset + Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/18, (float) (Gdx.graphics.getWidth()/8), Gdx.graphics.getHeight()/18);
        //batch.draw(testOverlapsTerrain2, terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5) + (float) (Gdx.graphics.getWidth()/13) + (float) (Gdx.graphics.getWidth()/5.3) + (float) (Gdx.graphics.getWidth()/10.5) + (float) (Gdx.graphics.getWidth()/6.8) + (float) (Gdx.graphics.getWidth()/8.5), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/20, (float) (Gdx.graphics.getWidth()/5.2), Gdx.graphics.getHeight()/20);

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
            terrainBelowRectangle1.set(terrainOffset, 0, (float) (Gdx.graphics.getWidth()/5.2), Gdx.graphics.getHeight()/20);
            terrainBelowRectangle2.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2), 0, (float) (Gdx.graphics.getWidth()/8.5), Gdx.graphics.getHeight()/11);
            terrainBelowRectangle3.set(terrainOffset+ (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5), 0, (float) (Gdx.graphics.getWidth()/6.8), Gdx.graphics.getHeight()/16);
            terrainBelowRectangle4.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5) + (float) (Gdx.graphics.getWidth()/6.8), 0, (float) (Gdx.graphics.getWidth()/10.5), Gdx.graphics.getHeight()/13);
            terrainBelowRectangle5.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5) + (float) (Gdx.graphics.getWidth()/6.8) + (float) (Gdx.graphics.getWidth()/10.5), 0, (float) (Gdx.graphics.getWidth()/5.3), Gdx.graphics.getHeight()/40);
            terrainBelowRectangle6.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5) + (float) (Gdx.graphics.getWidth()/6.8) + (float) (Gdx.graphics.getWidth()/10.5) + (float) (Gdx.graphics.getWidth()/5.3), 0, (float) (Gdx.graphics.getWidth()/13), Gdx.graphics.getHeight()/17);
            terrainBelowRectangle7.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5) + (float) (Gdx.graphics.getWidth()/6.8) + (float) (Gdx.graphics.getWidth()/10.5) + (float) (Gdx.graphics.getWidth()/5.3) + (float) (Gdx.graphics.getWidth()/13), 0, (float) (Gdx.graphics.getWidth()/10), Gdx.graphics.getHeight()/10);
            terrainBelowRectangle8.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5) + (float) (Gdx.graphics.getWidth()/6.8) + (float) (Gdx.graphics.getWidth()/10.5) + (float) (Gdx.graphics.getWidth()/5.3) + (float) (Gdx.graphics.getWidth()/13) + (float) (Gdx.graphics.getWidth()/10), 0, (float) (Gdx.graphics.getWidth()/11.8), Gdx.graphics.getHeight()/18);
            terrainBelowRectangle9.set(terrainOffset + Gdx.graphics.getWidth(), 0, (float) (Gdx.graphics.getWidth()/5.2), Gdx.graphics.getHeight()/20);

            terrainAboveRectangle1.set(terrainOffset, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/18, (float) (Gdx.graphics.getWidth()/12.2), Gdx.graphics.getHeight()/18);
            terrainAboveRectangle2.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/10, (float) (Gdx.graphics.getWidth()/9.5), Gdx.graphics.getHeight()/10);
            terrainAboveRectangle3.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/17, (float) (Gdx.graphics.getWidth()/13), Gdx.graphics.getHeight()/17);
            terrainAboveRectangle4.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5) + (float) (Gdx.graphics.getWidth()/13), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/40, (float) (Gdx.graphics.getWidth()/5.3), Gdx.graphics.getHeight()/40);
            terrainAboveRectangle5.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5) + (float) (Gdx.graphics.getWidth()/13) + (float) (Gdx.graphics.getWidth()/5.3), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/13, (float) (Gdx.graphics.getWidth()/10.5), Gdx.graphics.getHeight()/13);
            terrainAboveRectangle6.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5) + (float) (Gdx.graphics.getWidth()/13) + (float) (Gdx.graphics.getWidth()/5.3) + (float) (Gdx.graphics.getWidth()/10.5), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/16, (float) (Gdx.graphics.getWidth()/6.8), Gdx.graphics.getHeight()/16);
            terrainAboveRectangle7.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5) + (float) (Gdx.graphics.getWidth()/13) + (float) (Gdx.graphics.getWidth()/5.3) + (float) (Gdx.graphics.getWidth()/10.5) + (float) (Gdx.graphics.getWidth()/6.8), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/11, (float) (Gdx.graphics.getWidth()/8.5), Gdx.graphics.getHeight()/11);
            terrainAboveRectangle8.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5) + (float) (Gdx.graphics.getWidth()/13) + (float) (Gdx.graphics.getWidth()/5.3) + (float) (Gdx.graphics.getWidth()/10.5) + (float) (Gdx.graphics.getWidth()/6.8) + (float) (Gdx.graphics.getWidth()/8.5), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/20, (float) (Gdx.graphics.getWidth()/5.2), Gdx.graphics.getHeight()/20);
            terrainAboveRectangle9.set(terrainOffset + Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/18, (float) (Gdx.graphics.getWidth()/8), Gdx.graphics.getHeight()/18);
        }
        else if (Gdx.graphics.getWidth() > 1280){
            terrainBelowRectangle1.set(terrainOffset, 0, (float) (Gdx.graphics.getWidth()/5.2), Gdx.graphics.getHeight()/20);
            terrainBelowRectangle2.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2), 0, (float) (Gdx.graphics.getWidth()/8.5), Gdx.graphics.getHeight()/11);
            terrainBelowRectangle3.set(terrainOffset+ (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5), 0, (float) (Gdx.graphics.getWidth()/6.8), Gdx.graphics.getHeight()/16);
            terrainBelowRectangle4.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5) + (float) (Gdx.graphics.getWidth()/6.8), 0, (float) (Gdx.graphics.getWidth()/10.5), Gdx.graphics.getHeight()/13);
            terrainBelowRectangle5.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5) + (float) (Gdx.graphics.getWidth()/6.8) + (float) (Gdx.graphics.getWidth()/10.5), 0, (float) (Gdx.graphics.getWidth()/5.3), Gdx.graphics.getHeight()/40);
            terrainBelowRectangle6.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5) + (float) (Gdx.graphics.getWidth()/6.8) + (float) (Gdx.graphics.getWidth()/10.5) + (float) (Gdx.graphics.getWidth()/5.3), 0, (float) (Gdx.graphics.getWidth()/13), Gdx.graphics.getHeight()/17);
            terrainBelowRectangle7.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5) + (float) (Gdx.graphics.getWidth()/6.8) + (float) (Gdx.graphics.getWidth()/10.5) + (float) (Gdx.graphics.getWidth()/5.3) + (float) (Gdx.graphics.getWidth()/13), 0, (float) (Gdx.graphics.getWidth()/10), Gdx.graphics.getHeight()/10);
            terrainBelowRectangle8.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5) + (float) (Gdx.graphics.getWidth()/6.8) + (float) (Gdx.graphics.getWidth()/10.5) + (float) (Gdx.graphics.getWidth()/5.3) + (float) (Gdx.graphics.getWidth()/13) + (float) (Gdx.graphics.getWidth()/10), 0, (float) (Gdx.graphics.getWidth()/11.8), Gdx.graphics.getHeight()/18);
            terrainBelowRectangle9.set(terrainOffset + Gdx.graphics.getWidth(), 0, (float) (Gdx.graphics.getWidth()/5.2), Gdx.graphics.getHeight()/20);

            terrainAboveRectangle1.set(terrainOffset, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/18, (float) (Gdx.graphics.getWidth()/12.2), Gdx.graphics.getHeight()/18);
            terrainAboveRectangle2.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/10, (float) (Gdx.graphics.getWidth()/9.5), Gdx.graphics.getHeight()/10);
            terrainAboveRectangle3.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/17, (float) (Gdx.graphics.getWidth()/13), Gdx.graphics.getHeight()/17);
            terrainAboveRectangle4.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5) + (float) (Gdx.graphics.getWidth()/13), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/40, (float) (Gdx.graphics.getWidth()/5.3), Gdx.graphics.getHeight()/40);
            terrainAboveRectangle5.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5) + (float) (Gdx.graphics.getWidth()/13) + (float) (Gdx.graphics.getWidth()/5.3), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/13, (float) (Gdx.graphics.getWidth()/10.5), Gdx.graphics.getHeight()/13);
            terrainAboveRectangle6.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5) + (float) (Gdx.graphics.getWidth()/13) + (float) (Gdx.graphics.getWidth()/5.3) + (float) (Gdx.graphics.getWidth()/10.5), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/16, (float) (Gdx.graphics.getWidth()/6.8), Gdx.graphics.getHeight()/16);
            terrainAboveRectangle7.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5) + (float) (Gdx.graphics.getWidth()/13) + (float) (Gdx.graphics.getWidth()/5.3) + (float) (Gdx.graphics.getWidth()/10.5) + (float) (Gdx.graphics.getWidth()/6.8), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/11, (float) (Gdx.graphics.getWidth()/8.5), Gdx.graphics.getHeight()/11);
            terrainAboveRectangle8.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5) + (float) (Gdx.graphics.getWidth()/13) + (float) (Gdx.graphics.getWidth()/5.3) + (float) (Gdx.graphics.getWidth()/10.5) + (float) (Gdx.graphics.getWidth()/6.8) + (float) (Gdx.graphics.getWidth()/8.5), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/20, (float) (Gdx.graphics.getWidth()/5.2), Gdx.graphics.getHeight()/20);
            terrainAboveRectangle9.set(terrainOffset + Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/18, (float) (Gdx.graphics.getWidth()/8), Gdx.graphics.getHeight()/18);
        }
        else{
            terrainBelowRectangle1.set(terrainOffset, 0, (float) (Gdx.graphics.getWidth()/5.2), Gdx.graphics.getHeight()/20);
            terrainBelowRectangle2.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2), 0, (float) (Gdx.graphics.getWidth()/8.5), Gdx.graphics.getHeight()/11);
            terrainBelowRectangle3.set(terrainOffset+ (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5), 0, (float) (Gdx.graphics.getWidth()/6.8), Gdx.graphics.getHeight()/16);
            terrainBelowRectangle4.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5) + (float) (Gdx.graphics.getWidth()/6.8), 0, (float) (Gdx.graphics.getWidth()/10.5), Gdx.graphics.getHeight()/13);
            terrainBelowRectangle5.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5) + (float) (Gdx.graphics.getWidth()/6.8) + (float) (Gdx.graphics.getWidth()/10.5), 0, (float) (Gdx.graphics.getWidth()/5.3), Gdx.graphics.getHeight()/40);
            terrainBelowRectangle6.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5) + (float) (Gdx.graphics.getWidth()/6.8) + (float) (Gdx.graphics.getWidth()/10.5) + (float) (Gdx.graphics.getWidth()/5.3), 0, (float) (Gdx.graphics.getWidth()/13), Gdx.graphics.getHeight()/17);
            terrainBelowRectangle7.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5) + (float) (Gdx.graphics.getWidth()/6.8) + (float) (Gdx.graphics.getWidth()/10.5) + (float) (Gdx.graphics.getWidth()/5.3) + (float) (Gdx.graphics.getWidth()/13), 0, (float) (Gdx.graphics.getWidth()/10), Gdx.graphics.getHeight()/10);
            terrainBelowRectangle8.set(terrainOffset + (float) (Gdx.graphics.getWidth()/5.2) + (float) (Gdx.graphics.getWidth()/8.5) + (float) (Gdx.graphics.getWidth()/6.8) + (float) (Gdx.graphics.getWidth()/10.5) + (float) (Gdx.graphics.getWidth()/5.3) + (float) (Gdx.graphics.getWidth()/13) + (float) (Gdx.graphics.getWidth()/10), 0, (float) (Gdx.graphics.getWidth()/11.8), Gdx.graphics.getHeight()/18);
            terrainBelowRectangle9.set(terrainOffset + Gdx.graphics.getWidth(), 0, (float) (Gdx.graphics.getWidth()/5.2), Gdx.graphics.getHeight()/20);

            terrainAboveRectangle1.set(terrainOffset, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/18, (float) (Gdx.graphics.getWidth()/12.2), Gdx.graphics.getHeight()/18);
            terrainAboveRectangle2.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/10, (float) (Gdx.graphics.getWidth()/9.5), Gdx.graphics.getHeight()/10);
            terrainAboveRectangle3.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/17, (float) (Gdx.graphics.getWidth()/13), Gdx.graphics.getHeight()/17);
            terrainAboveRectangle4.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5) + (float) (Gdx.graphics.getWidth()/13), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/40, (float) (Gdx.graphics.getWidth()/5.3), Gdx.graphics.getHeight()/40);
            terrainAboveRectangle5.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5) + (float) (Gdx.graphics.getWidth()/13) + (float) (Gdx.graphics.getWidth()/5.3), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/13, (float) (Gdx.graphics.getWidth()/10.5), Gdx.graphics.getHeight()/13);
            terrainAboveRectangle6.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5) + (float) (Gdx.graphics.getWidth()/13) + (float) (Gdx.graphics.getWidth()/5.3) + (float) (Gdx.graphics.getWidth()/10.5), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/16, (float) (Gdx.graphics.getWidth()/6.8), Gdx.graphics.getHeight()/16);
            terrainAboveRectangle7.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5) + (float) (Gdx.graphics.getWidth()/13) + (float) (Gdx.graphics.getWidth()/5.3) + (float) (Gdx.graphics.getWidth()/10.5) + (float) (Gdx.graphics.getWidth()/6.8), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/11, (float) (Gdx.graphics.getWidth()/8.5), Gdx.graphics.getHeight()/11);
            terrainAboveRectangle8.set(terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5) + (float) (Gdx.graphics.getWidth()/13) + (float) (Gdx.graphics.getWidth()/5.3) + (float) (Gdx.graphics.getWidth()/10.5) + (float) (Gdx.graphics.getWidth()/6.8) + (float) (Gdx.graphics.getWidth()/8.5), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/20, (float) (Gdx.graphics.getWidth()/5.2), Gdx.graphics.getHeight()/20);
            terrainAboveRectangle9.set(terrainOffset + Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/18, (float) (Gdx.graphics.getWidth()/8), Gdx.graphics.getHeight()/18);

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
