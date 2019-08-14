package com.airplane.game;

import com.airplane.game.GameObjects.Plane;
import com.airplane.game.Managers.GameManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.badlogic.gdx.Gdx.gl;

public class AirplaneScene1 extends BaseScene{

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private GameManager gameManager;
	private Plane plane;
	private static boolean isAirplaneScene1Initialized;
	private int firstEnter = 0;
	private boolean doNotEnterInRender;

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

		if (gameManager.getGameState() == GameManager.GameState.PAUSE){
			resume();
		}
		else if (gameManager.getGameState() == GameManager.GameState.GAME_OVER || gameManager.getGameState() == GameManager.GameState.INIT){
			doNotEnterInRender = true;
			gameManager.setMenuSceneScreen();
		}
		else {
			pause();
		}
	}

	@Override
	public void show() {

		firstEnter = 1;
		System.out.println("In AirplaneScene1 show method");
	}

	@Override
	public void render(float delta) {

		super.render(delta);
		if(doNotEnterInRender){
			return; // без этого после касания BACK в GAME_OVER выдает glUseProgram:1573 GL error 0x501 после строки gameManager.updateScene()
		}

        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //camera.position.x = plane.getPlanePosition().x;
        //camera.position.y = plane.getPlanePosition().y;
        camera.update();
		batch.setProjectionMatrix(camera.combined); // устанавливаем в экземпляр spritebatch вид с камеры (области просмотра)
        batch.begin();
        gameManager.renderGame(batch);
        gameManager.updateScene(batch);
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

		gameManager.setGameState(GameManager.GameState.PAUSE);
		System.out.println("In AirplaneScene1 pause method");
	}

	@Override
	public void resume() {

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
