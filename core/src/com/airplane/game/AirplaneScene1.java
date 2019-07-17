package com.airplane.game;

import com.airplane.game.GameObjects.Plane;
import com.airplane.game.Managers.GameManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class AirplaneScene1 extends BaseScene{

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private GameManager gameManager;
	private Plane plane;
	private static boolean isAirplaneScene1Initialized;
	private int firstEnter = 0;

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

		if (gameManager.getGameState() == GameManager.GameState.PAUSE){
//			Gdx.input.setInputProcessor(inputManager);
			System.out.println("back in PAUSE");
			//Gdx.input.setInputProcessor(gameManager.getInputManager());
			resume();

		} else {
			System.out.println("back in RESUME");
			pause();

		}
		/*if(gamePaused){
			resume();
		}else{
			pause();
		}*/
	}

	@Override
	public void show() {

		firstEnter = 1;
		System.out.println("In AirplaneScene1 show method");
	}

	@Override
	public void render(float delta) {

		//System.out.println("In AirplaneScene1 render method");
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

		// При первом входе нам не нужно сюда заходить. А после того как кнопка HOME будет нажата, нужно чтобы при возврате в игру стояла пауза.
		if (firstEnter != 1) {
			gameManager.setGameState(GameManager.GameState.PAUSE);
		}
		firstEnter = 2;
        System.out.println("In AirplaneScene1 resize method");
	}

	@Override
	public void pause() {

        //gamePaused = true;
		gameManager.setGameState(GameManager.GameState.PAUSE);
		System.out.println("In AirplaneScene1 pause method");
	}

	@Override
	public void resume() {

		//gamePaused = false;
		Gdx.input.setInputProcessor(gameManager.getInputManager());
		gameManager.setGameState(GameManager.GameState.ACTION);
		System.out.println("In AirplaneScene1 resume method");
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
