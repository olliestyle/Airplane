package com.airplane.game.Managers;

import com.airplane.game.AirplaneGame;
import com.airplane.game.GameObjects.Meteor;
import com.airplane.game.GameObjects.Plane;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.xml.soap.Text;

import static com.airplane.game.Managers.GameManager.gameState;

public class TextManager {

    private static BitmapFont font; // отображаем текст на экране через эту переменную
    private static BitmapFont fontGameOver;
    private static float width;
    private static float height;


    public static void initialize (float width, float height){

        font = new BitmapFont(Gdx.files.internal("appetitenew2.fnt"));
        fontGameOver = new BitmapFont(Gdx.files.internal("appetitenew2.fnt"));
        TextManager.width = width;
        TextManager.height = height;
        font.setColor(Color.BLACK); // устанавливаем цвет шрифта
        font.getData().setScale(width/1500); // масштабируем размер шрифта
        fontGameOver.setColor(Color.RED);
        fontGameOver.getData().setScale(width/1000);

    }

    public static void displayMessage(SpriteBatch batch){

        // объект класса GlyphLayout хранит в себе информацию о шрифте и содержании текста
        /*
        GlyphLayout glyphLayoutHeight = new GlyphLayout();
        GlyphLayout glyphLayoutWidth = new GlyphLayout();
        GlyphLayout glyphLayoutTerrainOffset = new GlyphLayout();
        */
        GlyphLayout glyphLayoutDeltaTime = new GlyphLayout();
        /*
        GlyphLayout glyphLayoutPlaneAnimTime = new GlyphLayout();
        GlyphLayout glyphLayoutTouchPositionX = new GlyphLayout();
        GlyphLayout glyphLayoutTouchPositionY = new GlyphLayout();
        GlyphLayout glyphLayoutTapDrawTime = new GlyphLayout();
        GlyphLayout glyphLayoutPlanePostitionX = new GlyphLayout();
        GlyphLayout glyphLayoutPlanePostitionY = new GlyphLayout();
        */
        GlyphLayout glyphLayoutINIT = new GlyphLayout();
        GlyphLayout glyphLayoutGAMEOVER = new GlyphLayout();
        GlyphLayout glyphLayoutNextMeteorIn = new GlyphLayout();

        /*
        glyphLayoutWidth.setText(font, "width (x) = " + Gdx.graphics.getWidth());
        glyphLayoutHeight.setText(font, "height (y) = " + Gdx.graphics.getHeight());
        glyphLayoutTerrainOffset.setText(font, "terrainOffset = " + GameManager.terrainOffset);
        */
        glyphLayoutDeltaTime.setText(font, "deltaTime = " + Plane.deltaTime);
        /*
        glyphLayoutPlaneAnimTime.setText(font, "planeAnimTime = " + Plane.planeAnimTime);
        glyphLayoutTouchPositionX.setText(font, "touchPosition X = " + InputManager.touchPosition.x);
        glyphLayoutTouchPositionY.setText(font, "touchPosition Y = " + InputManager.touchPosition.y);
        glyphLayoutTapDrawTime.setText(font, "tapDrawTime = " + Plane.tapDrawTime);
        glyphLayoutPlanePostitionX.setText(font, "planePositionX = " + Plane.planePosition.x);
        glyphLayoutPlanePostitionY.setText(font, "planePositionY = " + Plane.planePosition.y);
        */
        glyphLayoutINIT.setText(font, "Tap next to the airplane to make it move ");
        //glyphLayoutGAMEOVER.setText(font, "Tap next to the airplane to make it move ");
        glyphLayoutGAMEOVER.setText(fontGameOver, "Game Over");
        glyphLayoutNextMeteorIn.setText(font, "nextMeteorIn = " + Meteor.nextMeteorIn);

        /*
        font.draw(batch, glyphLayoutWidth, (float) (width*0.01), (float) (height));
        font.draw(batch, glyphLayoutHeight, (float) (width*0.01), (float) (height*0.97));
        font.draw(batch, glyphLayoutTerrainOffset, (float) (width*0.01), (float) (height*0.94));
        */
        font.draw(batch, glyphLayoutDeltaTime, (float) (width*0.01), (float) (height*0.91));
        /*
        font.draw(batch, glyphLayoutPlaneAnimTime, (float) (width*0.01), (float) (height*0.88));
        font.draw(batch, glyphLayoutTouchPositionX, (float) (width*0.01), (float) (height*0.85));
        font.draw(batch, glyphLayoutTouchPositionY, (float) (width*0.01), (float) (height*0.82));
        font.draw(batch, glyphLayoutTapDrawTime, (float) (width*0.01), (float) (height*0.79));
        font.draw(batch, glyphLayoutPlanePostitionX, (float) (width*0.01), (float) (height*0.76));
        font.draw(batch, glyphLayoutPlanePostitionY, (float) (width*0.01), (float) (height*0.73));
        */
        font.draw(batch, glyphLayoutNextMeteorIn, (float) (width*0.01), (float) (height*0.97));




        if (gameState == GameManager.GameState.INIT)
            font.draw(batch, glyphLayoutINIT, (float) (width/3.4), (float) (height/2.1));

        if (gameState == GameManager.GameState.GAME_OVER)
            fontGameOver.draw(batch, glyphLayoutGAMEOVER, width/2 - 100, height/2 );


    }

}
