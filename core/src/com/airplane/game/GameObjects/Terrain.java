package com.airplane.game.GameObjects;

import com.airplane.game.Airplane;
import com.airplane.game.AirplaneScene1;
import com.airplane.game.AirplaneScene2;
import com.airplane.game.AirplaneScene3;
import com.airplane.game.Managers.GameManager;
import com.airplane.game.Managers.GameManager2;
import com.airplane.game.Managers.GameManager3;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Terrain {

    protected TextureRegion terrainBelow;
    protected TextureRegion terrainAbove;
    public float terrainOffset;
    private Rectangle terrainAboveRectangle1;
    private Rectangle terrainAboveRectangle2;
    private Rectangle terrainAboveRectangle3;
    private Rectangle terrainAboveRectangle4;
    private Rectangle terrainAboveRectangle5;
    private Rectangle terrainAboveRectangle6;
    private Rectangle terrainAboveRectangle7;
    private Rectangle terrainAboveRectangle8;
    private Rectangle terrainAboveRectangle9;
    private Rectangle terrainBelowRectangle1;
    private Rectangle terrainBelowRectangle2;
    private Rectangle terrainBelowRectangle3;
    private Rectangle terrainBelowRectangle4;
    private Rectangle terrainBelowRectangle5;
    private Rectangle terrainBelowRectangle6;
    private Rectangle terrainBelowRectangle7;
    private Rectangle terrainBelowRectangle8;
    private Rectangle terrainBelowRectangle9;
    private Texture testOverlapsTerrain1;
    private Texture testOverlapsTerrain2;
    private TextureAtlas atlas;
    private Sound crashSound;
    private AssetManager manager;
    private Plane plane;
    private GameManager gameManager;
    private GameManager2 gameManager2;
    private GameManager3 gameManager3;
    private Airplane airplane;

    public Terrain(Airplane airplane, Plane plane, GameManager gameManager) {

        this.airplane = airplane;
        System.out.println("game in terrain = " + airplane);
        atlas = airplane.atlas;
        manager = airplane.manager;
        this.plane = plane;
        this.gameManager = gameManager;
    }

    public Terrain(Airplane airplane, Plane plane, GameManager2 gameManager2) {

        this.airplane = airplane;
        System.out.println("game in terrain = " + airplane);
        atlas = airplane.atlas;
        manager = airplane.manager;
        this.plane = plane;
        this.gameManager2 = gameManager2;
    }

    public Terrain(Airplane airplane, Plane plane, GameManager3 gameManager3) {

        this.airplane = airplane;
        System.out.println("game in terrain = " + airplane);
        atlas = airplane.atlas;
        manager = airplane.manager;
        this.plane = plane;
        this.gameManager3 = gameManager3;
    }


    public void initializeTerrain(){

        crashSound = manager.get("crash.ogg");
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

    public void renderTerrain(SpriteBatch batch){

        batch.draw(terrainBelow, terrainOffset, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 9); // отрисовываем первый ландшафт  по ширине экрана
        batch.draw(terrainBelow, terrainOffset + Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 9); // отрисовываем второй ландшафт, "пркрепляя его ко второму"

        batch.draw(terrainAbove, terrainOffset, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 9, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 9); // отрисовываем первый ландшафт по ширине экрана
        batch.draw(terrainAbove, terrainOffset + Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 9, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 9); // отрисовываем второй ландшафт, "пркрепляя его ко второму"

        //batch.draw(testOverlapsTerrain1,terrainOffset + Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/18, (float) (Gdx.graphics.getWidth()/8), Gdx.graphics.getHeight()/18);
        //batch.draw(testOverlapsTerrain2, terrainOffset + (float) (Gdx.graphics.getWidth()/12.2) + (float) (Gdx.graphics.getWidth()/9.5) + (float) (Gdx.graphics.getWidth()/13) + (float) (Gdx.graphics.getWidth()/5.3) + (float) (Gdx.graphics.getWidth()/10.5) + (float) (Gdx.graphics.getWidth()/6.8) + (float) (Gdx.graphics.getWidth()/8.5), Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/20, (float) (Gdx.graphics.getWidth()/5.2), Gdx.graphics.getHeight()/20);

    }

    public void updateTerrain(){

        terrainOffset -= plane.getPlanePosition().x - plane.getPlaneDefaultPosition().x; // движение рельефа относительно самолета влево

        //System.out.println("terrainOffset = " + terrainOffset);
        //System.out.println("planePosition.x = " + Plane.planePosition.x);
        //System.out.println("planeDefaultPosition.x = " + Plane.planeDefaultPosition.x);

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
            System.out.println("Plane collides with Terrain");
            if(AirplaneScene1.isIsAirplaneScene1Initialized()){
                if (gameManager.getGameState() != GameManager.GameState.GAME_OVER){
                    gameManager.setGameState(GameManager.GameState.GAME_OVER);
                }
            }

            if(AirplaneScene2.isIsAirplaneScene2Initialized()){
                if (gameManager2.getGameState() != GameManager2.GameState.GAME_OVER){
                    gameManager2.setGameState(GameManager2.GameState.GAME_OVER);
                }
            }

            if(AirplaneScene3.isIsAirplaneScene3Initialized()){
                if (gameManager3.getGameState() != GameManager3.GameState.GAME_OVER){
                    gameManager3.setGameState(GameManager3.GameState.GAME_OVER);
                }
            }
        }
    }

    private boolean isPlaneCollisionWithTerrain(){
        if(Plane.planeRect.overlaps(terrainAboveRectangle1) || Plane.planeRect.overlaps(terrainAboveRectangle2) || Plane.planeRect.overlaps(terrainAboveRectangle3)
                || Plane.planeRect.overlaps(terrainAboveRectangle4) || Plane.planeRect.overlaps(terrainAboveRectangle5) || Plane.planeRect.overlaps(terrainAboveRectangle6)
                || Plane.planeRect.overlaps(terrainAboveRectangle7) || Plane.planeRect.overlaps(terrainAboveRectangle8) || Plane.planeRect.overlaps(terrainAboveRectangle9)
                || Plane.planeRect.overlaps(terrainBelowRectangle1) || Plane.planeRect.overlaps(terrainBelowRectangle2) || Plane.planeRect.overlaps(terrainBelowRectangle3)
                || Plane.planeRect.overlaps(terrainBelowRectangle4) || Plane.planeRect.overlaps(terrainBelowRectangle5) || Plane.planeRect.overlaps(terrainBelowRectangle6)
                || Plane.planeRect.overlaps(terrainBelowRectangle7) || Plane.planeRect.overlaps(terrainBelowRectangle8) || Plane.planeRect.overlaps(terrainBelowRectangle9)){
            if(airplane.soundEnabled) {
                crashSound.play();
            }
            Gdx.input.vibrate(100);
            return true;
        }
        return false;
    }
}
