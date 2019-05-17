package com.airplane.game;

import com.airplane.game.GameObjects.Plane;

import com.airplane.game.Managers.GameManager2;
import com.airplane.game.Managers.GameManager3;
import com.airplane.game.Managers.InputManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class AirplaneScene3 extends ScreenAdapter {

    //private static SpriteBatch batch; // область для отрисовки спрайтов нашей игры
    //OrthographicCamera camera; // область просмотра нашей игры + устанавливаем переменные высоты и ширины в качестве области просмотра нашей игры
    //private static Viewport viewport;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private GameManager3 gameManager3;
    private Plane plane;
    private static boolean isAirplaneScene3Initialized;

    public AirplaneScene3 (Airplane airplane){

        batch = airplane.batch;
        camera = airplane.camera;
        plane = new Plane(airplane);
        gameManager3 = new GameManager3(airplane, plane);
        plane.setGameManager3(gameManager3);

        //float height = Gdx.graphics.getHeight();
        //float width = Gdx.graphics.getWidth();
        //camera = new OrthographicCamera();
        //camera.setToOrtho(false);// этим методом мы центруем камеру на половину высоты и половину ширины экрана устройства и устанавливаем переменные высоты и ширины устройства в качестве области просмотра нашей игры
        //viewport = new FillViewport(3000, 1200, camera);
        //batch = new SpriteBatch();

        plane.initialize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gameManager3.initialize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(new InputManager(camera, plane));// доступ класса InputManager для получения касаний/нажатий
        AirplaneScene1.setIsAirplaneScene1Initialized(false);
        isAirplaneScene3Initialized = true;
    }

    public static boolean isIsAirplaneScene3Initialized() {
        return isAirplaneScene3Initialized;
    }

    public static void setIsAirplaneScene3Initialized(boolean isAirplaneScene3Initialized) {
        AirplaneScene3.isAirplaneScene3Initialized = isAirplaneScene3Initialized;
    }

    @Override
    public void show() {
        System.out.println("In AirplaneScene2 show method");
    }

    @Override
    public void render(float delta) {

        //System.out.println("In AirplaneScene1 render method");
		/*System.out.println("HEIGHT HERE " + Airplane.camera.viewportHeight);
		System.out.println("WIDTH HERE " + Airplane.camera.viewportWidth);
		System.out.println("Current State = " + gameState);*/

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined); // устанавливаем в экземпляр spritebatch вид с камеры (области просмотра)
        batch.begin();
        gameManager3.renderGame(batch);
        gameManager3.updateScene();
        plane.renderPlane(batch);
        plane.update();
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

        System.out.println("In AirplaneScene2 resize method");
		/*
		System.out.println("RESIZE HERE");
		System.out.println("width = " + width);
		System.out.println("height = " + height);
		*/

        //viewport.update(width,height);

    }

    @Override
    public void pause() {
        System.out.println("In AirplaneScene2 pause method");
        //System.out.println("PAUSE HERE");

    }

    @Override
    public void resume() {
        System.out.println("In AirplaneScene2 resume method");
        //System.out.println("RESUME HERE");

    }

    @Override
    public void hide() {
        System.out.println("In AirplaneScene2 hide method");
        //System.out.println("HIDE HERE");
        isAirplaneScene3Initialized = false;
        dispose();
    }

    @Override
    public void dispose () {
        System.out.println("In AirplaneScene2 dispose method");
        //System.out.println("DISPOSE HERE");
        gameManager3.dispose();

    }

}
