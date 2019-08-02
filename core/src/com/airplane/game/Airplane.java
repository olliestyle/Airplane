package com.airplane.game;

import com.airplane.game.Managers.SaveManager;
import com.airplane.game.matsemann.libgdxloadingscreen.screen.LoadingScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Airplane extends Game {

    public AssetManager manager = new AssetManager();
    public OrthographicCamera camera; // область просмотра нашей игры + устанавливаем переменные высоты и ширины в качестве области просмотра нашей игры
    public SpriteBatch batch; // область для отрисовки спрайтов нашей игры
    public TextureAtlas atlas;
    public TextureAtlas menuAtlas;
    //private ParticleEffectLoader.ParticleEffectParameter pep;
    private Viewport viewport;
    private boolean soundEnabled;
    private Preferences preferences;
    private SaveManager saveManager;

    public Airplane() {
        System.out.println("In Airplane constructor");
    }

    public SaveManager getSaveManager(){
        return saveManager;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
    }

    protected Preferences getPrefs(){

        if (preferences == null){
            preferences = Gdx.app.getPreferences("Airplane");
        }
        if (preferences == null){
            Gdx.app.log("info", "null preferences");
        }
        return preferences;
    }

    public void saveSoundStatus(){
        getPrefs().putBoolean("soundStatus", soundEnabled);
    }

    public boolean loadSoundStatus(){
        return getPrefs().getBoolean("soundStatus", true);
    }

    public void flushPref(){
        getPrefs().flush();
    }

    public void saveAll(){
        System.out.println("Saving Preferences");
        saveSoundStatus();
        flushPref();
    }

    private void prepareLocalScores(){

        saveManager = new SaveManager(true);
        if (saveManager.loadDataValue("Score1", int.class) == null){
            for (int i = 1; i <= 30; i++){
                saveManager.saveDataValue("Score " + i, 0);
            }
        }
    }

    @Override
    public void create() {
        System.out.println("In MainGame");

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false);// этим методом мы центруем камеру на половину высоты и половину ширины экрана устройства и устанавливаем переменные высоты и ширины устройства в качестве области просмотра нашей игры
        viewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        soundEnabled = loadSoundStatus();
        prepareLocalScores();
        setScreen(new LoadingScreen(this)); //this - экземпляр класса Airplane, который мы создаем и для которого вызывается этот конструктор

        //pep = new ParticleEffectLoader.ParticleEffectParameter();
        //pep.atlasFile = "Airplane.pack";

        /*manager.load("journey.mp3", Music.class);
        manager.load("pop.ogg", Sound.class);
        manager.load("alarm.ogg", Sound.class);
        manager.load("crash.ogg", Sound.class);
        manager.load("star.ogg", Sound.class);
        manager.load("shield.ogg", Sound.class);
        manager.load("fuel.ogg", Sound.class);
        manager.load("Airplane.pack", TextureAtlas.class);
        manager.load("fuelBar.png", Texture.class);
        manager.load("Smoke", ParticleEffect.class, pep);
        manager.load("Explosion", ParticleEffect.class, pep);
        manager.finishLoading();
        */

        //atlas = manager.get("Airplane.pack", TextureAtlas.class);
        //setScreen(new AirplaneScene1(this)); //this - экземпляр класса Airplane, который мы создаем и для которого вызывается этот конструктор
    }

    @Override
    public void render() {
        //System.out.println("In render");
        super.render();
    }

    @Override
    public void resize(int width, int height) {

        super.resize(width, height);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
        manager.dispose();
    }

    public Viewport getViewport() {
        return viewport;
    }
}