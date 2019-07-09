package com.airplane.game;

import com.airplane.game.GameObjects.Plane;
import com.airplane.game.Managers.GameManager;

import com.airplane.game.Managers.InputManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class AirplaneScene1 extends BaseScene{

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private GameManager gameManager;
	private Plane plane;
	private static boolean isAirplaneScene1Initialized;

	private boolean gamePaused = false;

	public AirplaneScene1 (Airplane airplane){

		super(airplane);
		batch = airplane.batch;
		camera = airplane.camera;
		plane = new Plane(airplane);
		gameManager = new GameManager(airplane, plane);
		plane.setGameManager(gameManager);

        plane.initialize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gameManager.initialize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		AirplaneScene2.setIsAirplaneScene2Initialized(false);
		AirplaneScene3.setIsAirplaneScene3Initialized(false);
		MenuScene.setIsMenuSceneInitialised(false);
		isAirplaneScene1Initialized = true;

	}

	public static boolean isIsAirplaneScene1Initialized() {
		return isAirplaneScene1Initialized;
	}

	public static void setIsAirplaneScene1Initialized(boolean isAirplaneScene1Initialized) {
		AirplaneScene1.isAirplaneScene1Initialized = isAirplaneScene1Initialized;
	}

	@Override
	protected void handleBackPress() {
		System.out.println("back");
		if(gamePaused){
			resume();
		}else{
			pause();
		}
	}

	@Override
	public void show() {

		System.out.println("In AirplaneScene1 show method");
	}

	@Override
	public void render(float delta) {

		System.out.println("In AirplaneScene1 render method");
		super.render(delta);
		/*if(gamePaused){
			return;
		}*/

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

        System.out.println("In AirplaneScene1 resize method");
	}

	@Override
	public void pause() {

        System.out.println("In AirplaneScene1 pause method");
        //gamePaused = true;
        gameManager.setGameState(GameManager.GameState.PAUSE);
	}

	@Override
	public void resume() {

		System.out.println("In AirplaneScene1 resume method");
		gamePaused = false;
	}

	@Override
	public void hide() {

        System.out.println("In AirplaneScene1 hide method");
		isAirplaneScene1Initialized = false;
		dispose();
	}

	@Override
	public void dispose () {

        System.out.println("In AirplaneScene1 dispose method");
		gameManager.dispose();
	}
}
