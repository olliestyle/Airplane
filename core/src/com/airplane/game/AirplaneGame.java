package com.airplane.game;

import com.airplane.game.GameObjects.Plane;
import com.airplane.game.Managers.GameManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class AirplaneGame implements Screen {

	SpriteBatch batch; // область для отрисовки спрайтов нашей игры
	FPSLogger fpsLogger;
	OrthographicCamera camera; // область просмотра нашей игры
	MainGame game;
	Viewport viewport;

	public AirplaneGame (MainGame game){

		this.game = game;

		float height = Gdx.graphics.getHeight();
		float width = Gdx.graphics.getWidth();
		camera = new OrthographicCamera(width,height);// устанавливаем переменные высоты и ширины в качестве области просмотра нашей игры
		camera.setToOrtho(false);// этим методом мы центруем камеру на половину высоты и половину ширины экрана устройства
		//camera.position.set(width/2, height/2,0);
		viewport = new FitViewport(width,height, camera);
		batch = new SpriteBatch();
		fpsLogger = new FPSLogger();
		GameManager.initialize(width,height);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

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

	    viewport.update(width,height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose () {

		batch.dispose();
		GameManager.dispose();

	}
}
