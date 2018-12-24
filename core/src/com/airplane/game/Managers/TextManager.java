package com.airplane.game.Managers;

import com.airplane.game.AirplaneGame;
import com.airplane.game.GameObjects.Plane;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.xml.soap.Text;

public class TextManager {

    private static BitmapFont font; // отображаем текст на экране через эту переменную
    private static float width;
    private static float height;


    public static void initialize (float width, float height){

        font = new BitmapFont(Gdx.files.internal("appetitenew2.fnt"));
        TextManager.width = width;
        TextManager.height = height;
        font.setColor(Color.BLACK); // устанавливаем цвет шрифта
        font.getData().setScale(width/1000); // масштабируем размер шрифта

    }

    public static void displayMessage(SpriteBatch batch){

        // объект класса GlyphLayout хранит в себе информацию о шрифте и содержании текста
        GlyphLayout glyphLayoutHeight = new GlyphLayout();
        GlyphLayout glyphLayoutWidth = new GlyphLayout();
        GlyphLayout glyphLayoutTerrainOffset = new GlyphLayout();
        GlyphLayout glyphLayoutDeltaTime = new GlyphLayout();
        GlyphLayout glyphLayoutPlaneAnimTime = new GlyphLayout();
        glyphLayoutWidth.setText(font, "width (x) = " + Gdx.graphics.getWidth());
        glyphLayoutHeight.setText(font, "height (y) = " + Gdx.graphics.getHeight());
        glyphLayoutTerrainOffset.setText(font, "terrainOffset = " + GameManager.terrainOffset);
        glyphLayoutDeltaTime.setText(font, "deltaTime = " + Plane.deltaTime);
        glyphLayoutPlaneAnimTime.setText(font, "planeAnimTime = " + Plane.planeAnimTime);

        font.draw(batch, glyphLayoutWidth, (float) (width*0.01), (float) (height*0.99));
        font.draw(batch, glyphLayoutHeight, (float) (width*0.01), (float) (height*0.95));
        font.draw(batch, glyphLayoutTerrainOffset, (float) (width*0.01), (float) (height*0.90));
        font.draw(batch, glyphLayoutDeltaTime, (float) (width*0.01), (float) (height*0.85));
        font.draw(batch, glyphLayoutPlaneAnimTime, (float) (width*0.01), (float) (height*0.80));


    }

}
