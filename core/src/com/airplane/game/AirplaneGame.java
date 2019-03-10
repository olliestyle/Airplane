package com.airplane.game;

import com.airplane.game.GameObjects.Meteor;
import com.airplane.game.GameObjects.Plane;
import com.airplane.game.Managers.GameManager;

import com.airplane.game.Managers.InputManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.airplane.game.Managers.GameManager.gameState;
import static com.airplane.game.Managers.GameManager.startPointTerrainAboveY;
import static com.airplane.game.Managers.GameManager.terrainAbove;


public class AirplaneGame implements Screen {

	private static SpriteBatch batch; // область для отрисовки спрайтов нашей игры
	private static OrthographicCamera camera = new OrthographicCamera(); // область просмотра нашей игры + устанавливаем переменные высоты и ширины в качестве области просмотра нашей игры
	MainGame game;

	private static Viewport viewport;


	public AirplaneGame (MainGame game){

		System.out.println("I'm in the GAME");
		this.game = game;

		float height = Gdx.graphics.getHeight();
		float width = Gdx.graphics.getWidth();
		//camera = new OrthographicCamera();
		camera.setToOrtho(false);// этим методом мы центруем камеру на половину высоты и половину ширины экрана устройства и устанавливаем переменные высоты и ширины устройства в качестве области просмотра нашей игры

		viewport = new FillViewport(5000,1800,camera);
		batch = new SpriteBatch();

		GameManager.initialize(width,height);
		Gdx.input.setInputProcessor(new InputManager(camera));// доступ класса InputManager для получения касаний/нажатий
	}


	@Override
	public void show() {

		System.out.println("SHOW HERE");
	}

	@Override
	public void render(float delta) {

		System.out.println("RENDER HERE");
		System.out.println("HEIGHT HERE " + camera.viewportHeight);
		System.out.println("WIDTH HERE " + camera.viewportWidth);
		System.out.println("Current State = " + gameState);
		System.out.println("MeteorPosition.x, MeteorPosition.y = " + Meteor.meteorPosition.x + " " + Meteor.meteorPosition.y);
		System.out.println("nextMeteorIn = " + Meteor.nextMeteorIn);


        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined); // устанавливаем в экземпляр spritebatch вид с камеры (области просмотра)
        batch.begin();
        GameManager.renderGame(batch);
        GameManager.updateScene();
        Plane.renderPlane(batch);
        Plane.update();
		Meteor.renderMeteor(batch);
		Meteor.updateMeteor();
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
