package com.airplane.game.Managers;

import com.airplane.game.Airplane;
import com.airplane.game.AirplaneScene1;
import com.airplane.game.GameObjects.Meteor;
import com.airplane.game.GameObjects.Plane;
import com.airplane.game.GameObjects.Terrain;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.xml.soap.Text;

import static com.airplane.game.Managers.GameManager.gameState;

public class TextManager {

    private BitmapFont font; // отображаем текст на экране через эту переменную
    private BitmapFont fontGameOver;
    private float widthTextManager;
    private float heightTextManager;
    private PickUpSpawnManager pickUpSpawnManager;

    public TextManager(PickUpSpawnManager pickUpSpawnManager) {

        this.pickUpSpawnManager = pickUpSpawnManager;

    }

    public void initialize (float width, float height){

        font = new BitmapFont(Gdx.files.internal("appetitenew2.fnt"));
        fontGameOver = new BitmapFont(Gdx.files.internal("appetitenew2.fnt"));
        widthTextManager = width;
        heightTextManager = height;
        font.setColor(Color.BLACK); // устанавливаем цвет шрифта
        font.getData().setScale(width/1500); // масштабируем размер шрифта
        fontGameOver.setColor(Color.RED);
        fontGameOver.getData().setScale(width/1000);

    }

    public void displayMessage(SpriteBatch batch){

        GlyphLayout glyphLayoutHeight = new GlyphLayout();
        GlyphLayout glyphLayoutWidth = new GlyphLayout();
        GlyphLayout glyphLayoutTouchPositionX = new GlyphLayout();
        GlyphLayout glyphLayoutTouchPositionY = new GlyphLayout();
        GlyphLayout glyphLayoutINIT = new GlyphLayout();
        GlyphLayout glyphLayoutGAMEOVER = new GlyphLayout();
        GlyphLayout glyphLayoutSHIELD = new GlyphLayout();
        GlyphLayout glyphLayoutSTAR = new GlyphLayout();

        glyphLayoutWidth.setText(font, "width (x) = " + Gdx.graphics.getWidth());
        glyphLayoutHeight.setText(font, "height (y) = " + Gdx.graphics.getHeight());
        glyphLayoutTouchPositionX.setText(font, "touchPosition X = " + InputManager.touchPosition.x);
        glyphLayoutTouchPositionY.setText(font, "touchPosition Y = " + InputManager.touchPosition.y);
        glyphLayoutINIT.setText(font, "Tap next to the airplane to make it move ");
        glyphLayoutGAMEOVER.setText(fontGameOver, "Game Over");
        glyphLayoutSHIELD.setText(font, "Shield " + (int)pickUpSpawnManager.getShieldCount() + " sec.");
        glyphLayoutSTAR.setText(font, "Stars collected " + pickUpSpawnManager.getStarCount());

        if (gameState == GameManager.GameState.INIT)
            font.draw(batch, glyphLayoutINIT, (float) (widthTextManager/3.4), (float) (heightTextManager/2.1));

        if (gameState == GameManager.GameState.GAME_OVER)
            fontGameOver.draw(batch, glyphLayoutGAMEOVER, widthTextManager/2 - 100, heightTextManager/2 );

        if (pickUpSpawnManager.getShieldCount() > 0){
            font.draw(batch, glyphLayoutSHIELD,widthTextManager/100 , heightTextManager - heightTextManager/40);
        }

        font.draw(batch, glyphLayoutSTAR, widthTextManager/100 , heightTextManager - heightTextManager/9);

    }

}
