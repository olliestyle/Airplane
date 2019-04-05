package com.airplane.game;

import com.airplane.game.GameObjects.Plane;
import com.airplane.game.Managers.GameManager;

import com.airplane.game.Managers.InputManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


public class AirplaneScene1 extends ScreenAdapter {

	//private static SpriteBatch batch; // область для отрисовки спрайтов нашей игры
	//OrthographicCamera camera; // область просмотра нашей игры + устанавливаем переменные высоты и ширины в качестве области просмотра нашей игры
	//private static Viewport viewport;

	Airplane game;
	SpriteBatch batch;
	OrthographicCamera camera;
	TextureAtlas atlas;
	GameManager gameManager;
	Plane plane;

	public AirplaneScene1 (Airplane airplane){

		System.out.println("I'm in the GAME");
		game = airplane;
		batch = game.batch;
		camera = game.camera;
		atlas = game.atlas;
		plane = new Plane(game);
		gameManager = new GameManager(game);

		//float height = Gdx.graphics.getHeight();
		//float width = Gdx.graphics.getWidth();
		//camera = new OrthographicCamera();
		//camera.setToOrtho(false);// этим методом мы центруем камеру на половину высоты и половину ширины экрана устройства и устанавливаем переменные высоты и ширины устройства в качестве области просмотра нашей игры
		//viewport = new FillViewport(width, height,camera);
		//batch = new SpriteBatch();

        plane.initialize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gameManager.initialize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(new InputManager(camera, plane));// доступ класса InputManager для получения касаний/нажатий
	}

	@Override
	public void show() {

		System.out.println("SHOW HERE");
	}

	@Override
	public void render(float delta) {

		/*System.out.println("RENDER HERE");
		System.out.println("HEIGHT HERE " + Airplane.camera.viewportHeight);
		System.out.println("WIDTH HERE " + Airplane.camera.viewportWidth);
		System.out.println("Current State = " + gameState);*/

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
		batch.setProjectionMatrix(camera.combined); // устанавливаем в экземпляр spritebatch вид с камеры (области просмотра)
        batch.begin();
        gameManager.renderGame(batch);
        gameManager.updateScene();
        plane.renderPlane(batch);
        plane.update();
        batch.end();

	}

	@Override
	public void resize(int width, int height) {

		System.out.println("RESIZE HERE");
		System.out.println("width = " + width);
		System.out.println("height = " + height);

	    //viewport.update(width,height);

	}

	@Override
	public void pause() {

		System.out.println("PAUSE HERE");

	}

	@Override
	public void resume() {

		System.out.println("RESUME HERE");

	}

	@Override
	public void hide() {

		System.out.println("HIDE HERE");
		dispose();
	}

	@Override
	public void dispose () {

		System.out.println("DISPOSE HERE");
		gameManager.dispose();

	}
}
