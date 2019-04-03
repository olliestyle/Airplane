package com.airplane.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Airplane extends Game {

    public AssetManager manager = new AssetManager();
    public OrthographicCamera camera; // область просмотра нашей игры + устанавливаем переменные высоты и ширины в качестве области просмотра нашей игры
    public SpriteBatch batch; // область для отрисовки спрайтов нашей игры
    public TextureAtlas atlas;

    public Airplane() {
        System.out.println("In Airplane constructor");
    }

    @Override
    public void create() {
        System.out.println("In MainGame");
        camera = new OrthographicCamera();
        camera.setToOrtho(false);// этим методом мы центруем камеру на половину высоты и половину ширины экрана устройства и устанавливаем переменные высоты и ширины устройства в качестве области просмотра нашей игры
        batch = new SpriteBatch();
        //atlas = new TextureAtlas(Gdx.files.internal("Airplane.pack"));

        manager.load("journey.mp3", Music.class);
        manager.load("pop.ogg", Sound.class);
        manager.load("alarm.ogg", Sound.class);
        manager.load("crash.ogg", Sound.class);
        manager.load("Airplane.pack", TextureAtlas.class);
        manager.finishLoading();

        atlas=manager.get("Airplane.pack", TextureAtlas.class);
        setScreen(new AirplaneScene1(this)); //this - экземпляр класса Airplane, которым мы создаем и для которого вызывается этот конструктор
    }

    @Override
    public void render() {
        //System.out.println("In render");
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
    }
}