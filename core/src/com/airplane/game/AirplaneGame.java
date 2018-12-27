package com.airplane.game;

import com.airplane.game.GameObjects.Plane;
import com.airplane.game.Managers.GameManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;



public class AirplaneGame implements Screen {

	private static SpriteBatch batch; // область для отрисовки спрайтов нашей игры
	private static OrthographicCamera camera; // область просмотра нашей игры
	MainGame game;
	private static Viewport viewport;

	public AirplaneGame (MainGame game){

		System.out.println("I'm in the GAME");
		this.game = game;

		float height = Gdx.graphics.getHeight();
		float width = Gdx.graphics.getWidth();
		camera = new OrthographicCamera(width,height);// устанавливаем переменные высоты и ширины в качестве области просмотра нашей игры
		camera.setToOrtho(false);// этим методом мы центруем камеру на половину высоты и половину ширины экрана устройства
		viewport = new FillViewport(width,height,camera);
		batch = new SpriteBatch();
		GameManager.initialize(width,height);
	}

	@Override
	public void show() {

		System.out.println("SHOW HERE");

	}

	@Override
	public void render(float delta) {

		System.out.println("RENDER HERE");

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined); // устанавливаем в экземпляр spritebatch вид с камеры (области просмотра)
        batch.begin();
        GameManager.renderGame(batch);
        GameManager.updateScene();
        Plane.renderPlane(batch);
        Plane.update();
        batch.end();

	}

	@Override
	public void resize(int width, int height) {

		System.out.println("RESIZE HERE");
		System.out.println("width = " + width);
		System.out.println("height = " + height);
	    viewport.update(width,height);


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
		batch.dispose();
		GameManager.dispose();

	}
}
