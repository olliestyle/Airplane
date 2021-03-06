package com.airplane.game.Managers;

import com.airplane.game.Airplane;
import com.airplane.game.AirplaneScene1;
import com.airplane.game.AirplaneScene2;
import com.airplane.game.AirplaneScene3;
import com.airplane.game.MenuScene;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class TextManager {

    private BitmapFont font;
    //private BitmapFont fontGameOver;
    private float widthTextManager;
    private float heightTextManager;
    private PickUpSpawnManager pickUpSpawnManager;
    private GameManager gameManager;
    private GameManager2 gameManager2;
    private GameManager3 gameManager3;
    private MenuScene menuScene;
    private Airplane airplane;

    public TextManager(Airplane airplane){

        this.airplane = airplane;
    }

    public TextManager(Airplane airplane, PickUpSpawnManager pickUpSpawnManager, GameManager gameManager) {

        this.airplane = airplane;
        this.pickUpSpawnManager = pickUpSpawnManager;
        this.gameManager = gameManager;
    }

    public TextManager(Airplane airplane, PickUpSpawnManager pickUpSpawnManager, GameManager2 gameManager2) {

        this.airplane = airplane;
        this.pickUpSpawnManager = pickUpSpawnManager;
        this.gameManager2 = gameManager2;
    }

    public TextManager(Airplane airplane, PickUpSpawnManager pickUpSpawnManager, GameManager3 gameManager3) {

        this.airplane = airplane;
        this.pickUpSpawnManager = pickUpSpawnManager;
        this.gameManager3 = gameManager3;
    }

    public void initialize (float width, float height){

        //font = new BitmapFont(Gdx.files.internal("june.fnt"));
        font = airplane.manager.get("june.fnt");
        //fontGameOver = new BitmapFont(Gdx.files.internal("june.fnt"));
        //fontGameOver = airplane.manager.get("june.fnt");

        widthTextManager = width;
        heightTextManager = height;
        font.setColor(Color.BLACK); // устанавливаем цвет шрифта
        font.getData().setScale(width/2000); // масштабируем размер шрифта
        //fontGameOver.setColor(Color.BLACK);
        //fontGameOver.getData().setScale(width/1500);
    }

    public BitmapFont getFont() {
        return font;
    }

    public void displayMenuMessage(SpriteBatch batch){

        GlyphLayout glyphLayoutHelpTip1 = new GlyphLayout();
        GlyphLayout glyphLayoutHelpTip2 = new GlyphLayout();
        GlyphLayout glyphLayoutHelpTip3 = new GlyphLayout();
        GlyphLayout glyphLayoutHelpTip4 = new GlyphLayout();

        glyphLayoutHelpTip1.setText(font, "Tap around the plane to move it!");
        glyphLayoutHelpTip2.setText(font, "Collect the Stars to get the HighScore!");
        glyphLayoutHelpTip3.setText(font, "Collect Fuel to keep fly!");
        glyphLayoutHelpTip4.setText(font, "Collect Shield to be invincible to Rocks and Meteors!");

        if (MenuScene.isIsMenuSceneInitialised()){

            font.draw(batch, glyphLayoutHelpTip1, widthTextManager/2 - glyphLayoutHelpTip1.width/2, 10*glyphLayoutHelpTip1.height);
            font.draw(batch, glyphLayoutHelpTip2, widthTextManager/2 - glyphLayoutHelpTip2.width/2, 8*glyphLayoutHelpTip2.height);
            font.draw(batch, glyphLayoutHelpTip3, widthTextManager/2 - glyphLayoutHelpTip3.width/2, 6*glyphLayoutHelpTip3.height);
            font.draw(batch, glyphLayoutHelpTip4, widthTextManager/2 - glyphLayoutHelpTip4.width/2, 4*glyphLayoutHelpTip4.height);
        }
    }

    public void displayMessage(SpriteBatch batch){

        GlyphLayout glyphLayoutHeight = new GlyphLayout();
        GlyphLayout glyphLayoutWidth = new GlyphLayout();
        GlyphLayout glyphLayoutTouchPositionX = new GlyphLayout();
        GlyphLayout glyphLayoutTouchPositionY = new GlyphLayout();
        GlyphLayout glyphLayoutINIT = new GlyphLayout();
        //GlyphLayout glyphLayoutGAMEOVER = new GlyphLayout();
        GlyphLayout glyphLayoutSHIELD = new GlyphLayout();
        GlyphLayout glyphLayoutSTAR = new GlyphLayout();

        glyphLayoutWidth.setText(font, "width (x) = " + Gdx.graphics.getWidth());
        glyphLayoutHeight.setText(font, "height (y) = " + Gdx.graphics.getHeight());
        glyphLayoutTouchPositionX.setText(font, "touchPosition X = " + InputManager.touchPosition.x);
        glyphLayoutTouchPositionY.setText(font, "touchPosition Y = " + InputManager.touchPosition.y);
        glyphLayoutINIT.setText(font, "The closer you touch the plane, the faster it will fly");
        //glyphLayoutGAMEOVER.setText(fontGameOver, "Game Over. Tap to try again.");
        glyphLayoutSHIELD.setText(font, "Shield " + (int)pickUpSpawnManager.getShieldCount() + " sec.");
        glyphLayoutSTAR.setText(font, "Stars collected  " + pickUpSpawnManager.getStarCount());

        /*
        if (gameState == GameManager.GameState.INIT)
            font.draw(batch, glyphLayoutINIT, (float) (widthTextManager/3.4), (float) (heightTextManager/2.1));

        if (gameState == GameManager.GameState.GAME_OVER)
            fontGameOver.draw(batch, glyphLayoutGAMEOVER, widthTextManager/2 - 100, heightTextManager/2 );
        */

        if(AirplaneScene1.isIsAirplaneScene1Initialized()) {
            if (gameManager.getGameState() == GameManager.GameState.INIT)
                font.draw(batch, glyphLayoutINIT, (widthTextManager/2 - glyphLayoutINIT.width/2), (float) (heightTextManager / 2.1));

            /*if (gameManager.getGameState() == GameManager.GameState.GAME_OVER)
                fontGameOver.draw(batch, glyphLayoutGAMEOVER, widthTextManager / 2 - glyphLayoutGAMEOVER.width/2, heightTextManager / 2);*/
        }

        if(AirplaneScene2.isIsAirplaneScene2Initialized()) {
            if (gameManager2.getGameState() == GameManager2.GameState.INIT)
                font.draw(batch, glyphLayoutINIT, (widthTextManager/2 - glyphLayoutINIT.width/2), (float) (heightTextManager / 2.1));

           /* if (gameManager2.getGameState() == GameManager2.GameState.GAME_OVER)
                fontGameOver.draw(batch, glyphLayoutGAMEOVER, widthTextManager / 2 - glyphLayoutGAMEOVER.width/2, heightTextManager / 2);*/
        }

        if(AirplaneScene3.isIsAirplaneScene3Initialized()) {
            if (gameManager3.getGameState() == GameManager3.GameState.INIT)
                font.draw(batch, glyphLayoutINIT, (widthTextManager/2 - glyphLayoutINIT.width/2), (float) (heightTextManager / 2.1));

            /*if (gameManager3.getGameState() == GameManager3.GameState.GAME_OVER)
                fontGameOver.draw(batch, glyphLayoutGAMEOVER, widthTextManager / 2 - glyphLayoutGAMEOVER.width/2, heightTextManager / 2);*/
        }

        if (pickUpSpawnManager.getShieldCount() > 0){
            font.draw(batch, glyphLayoutSHIELD,widthTextManager/100 , heightTextManager - heightTextManager/40);
        }

        font.draw(batch, glyphLayoutSTAR, widthTextManager/100 , heightTextManager - heightTextManager/9);
    }
}
