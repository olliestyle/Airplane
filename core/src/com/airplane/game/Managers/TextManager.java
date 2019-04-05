package com.airplane.game.Managers;

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

        // объект класса GlyphLayout хранит в себе информацию о шрифте и содержании текста

        GlyphLayout glyphLayoutHeight = new GlyphLayout();
        GlyphLayout glyphLayoutWidth = new GlyphLayout();
        GlyphLayout glyphLayoutTerrainOffset = new GlyphLayout();

        GlyphLayout glyphLayoutDeltaTime = new GlyphLayout();

        //GlyphLayout glyphLayoutPlaneAnimTime = new GlyphLayout();
        GlyphLayout glyphLayoutTouchPositionX = new GlyphLayout();
        GlyphLayout glyphLayoutTouchPositionY = new GlyphLayout();
        /*
        GlyphLayout glyphLayoutTapDrawTime = new GlyphLayout();
        GlyphLayout glyphLayoutPlanePostitionX = new GlyphLayout();
        GlyphLayout glyphLayoutPlanePostitionY = new GlyphLayout();
        */
        GlyphLayout glyphLayoutINIT = new GlyphLayout();
        GlyphLayout glyphLayoutGAMEOVER = new GlyphLayout();
        GlyphLayout glyphLayoutNextMeteorIn = new GlyphLayout();
        GlyphLayout glyphLayoutMeteorPositionY = new GlyphLayout();
        GlyphLayout glyphLayoutMeteorPositionX = new GlyphLayout();
        GlyphLayout glyphLayoutDestinationX = new GlyphLayout();
        GlyphLayout glyphLayoutDestinationY = new GlyphLayout();
        GlyphLayout glyphLayoutMeteorVelocityX = new GlyphLayout();
        GlyphLayout glyphLayoutMeteorVelocityY = new GlyphLayout();


        glyphLayoutWidth.setText(font, "width (x) = " + Gdx.graphics.getWidth());
        glyphLayoutHeight.setText(font, "height (y) = " + Gdx.graphics.getHeight());


        //glyphLayoutPlaneAnimTime.setText(font, "planeAnimTime = " + Plane.planeAnimTime);
        glyphLayoutTouchPositionX.setText(font, "touchPosition X = " + InputManager.touchPosition.x);
        glyphLayoutTouchPositionY.setText(font, "touchPosition Y = " + InputManager.touchPosition.y);
        /*
        glyphLayoutTapDrawTime.setText(font, "tapDrawTime = " + Plane.tapDrawTime);
        glyphLayoutPlanePostitionX.setText(font, "planePositionX = " + Plane.planePosition.x);
        glyphLayoutPlanePostitionY.setText(font, "planePositionY = " + Plane.planePosition.y);
        */
        glyphLayoutINIT.setText(font, "Tap next to the airplane to make it move ");
        //glyphLayoutGAMEOVER.setText(font, "Tap next to the airplane to make it move ");
        glyphLayoutGAMEOVER.setText(fontGameOver, "Game Over");





//        font.draw(batch, glyphLayoutWidth, (float) (width*0.01), (float) (height*0.76));
//        font.draw(batch, glyphLayoutHeight, (float) (width*0.01), (float) (height*0.73));
//        font.draw(batch, glyphLayoutTerrainOffset, (float) (width*0.01), (float) (height*0.64));
//
//        font.draw(batch, glyphLayoutDeltaTime, (float) (width*0.01), (float) (height));
//
//        //font.draw(batch, glyphLayoutPlaneAnimTime, (float) (width*0.01), (float) (height*0.88));
//        font.draw(batch, glyphLayoutTouchPositionX, (float) (width*0.01), (float) (height*0.70));
//        font.draw(batch, glyphLayoutTouchPositionY, (float) (width*0.01), (float) (height*0.67));
//        /*
//        font.draw(batch, glyphLayoutTapDrawTime, (float) (width*0.01), (float) (height*0.79));
//        font.draw(batch, glyphLayoutPlanePostitionX, (float) (width*0.01), (float) (height*0.76));
//        font.draw(batch, glyphLayoutPlanePostitionY, (float) (width*0.01), (float) (height*0.73));
//        */
//        font.draw(batch, glyphLayoutNextMeteorIn, (float) (widthTextManager*0.01), (float) (heightTextManager*0.97));
//        font.draw(batch, glyphLayoutMeteorPositionX, (float) (width*0.01), (float) (height*0.94));
//        font.draw(batch, glyphLayoutMeteorPositionY, (float) (width*0.01), (float) (height*0.91));
//        font.draw(batch, glyphLayoutDestinationX, (float) (width*0.01), (float) (height*0.88));
//        font.draw(batch, glyphLayoutDestinationY, (float) (width*0.01), (float) (height*0.85));
//        font.draw(batch, glyphLayoutMeteorVelocityX, (float) (width*0.01), (float) (height*0.82));
//        font.draw(batch, glyphLayoutMeteorVelocityY, (float) (width*0.01), (float) (height*0.79));

        if (gameState == GameManager.GameState.INIT)
            font.draw(batch, glyphLayoutINIT, (float) (widthTextManager/3.4), (float) (heightTextManager/2.1));

        if (gameState == GameManager.GameState.GAME_OVER)
            fontGameOver.draw(batch, glyphLayoutGAMEOVER, widthTextManager/2 - 100, heightTextManager/2 );


    }

}
