package com.airplane.game;

import com.airplane.game.GameObjects.Plane;

import com.airplane.game.Managers.GameManager;
import com.airplane.game.Managers.GameManager3;
import com.airplane.game.Managers.InputManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class AirplaneScene3 extends BaseScene {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private GameManager3 gameManager3;
    private Plane plane;
    private static boolean isAirplaneScene3Initialized;
    private int firstEnter = 0;
    private boolean doNotEnterInRender;

    public AirplaneScene3 (Airplane airplane){

        super(airplane);
        batch = airplane.batch;
        camera = airplane.camera;
        plane = new Plane(airplane);
        gameManager3 = new GameManager3(airplane, plane);
        plane.setGameManager3(gameManager3);

        plane.initialize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gameManager3.initialize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        AirplaneScene1.setIsAirplaneScene1Initialized(false);
        AirplaneScene2.setIsAirplaneScene2Initialized(false);
        MenuScene.setIsMenuSceneInitialised(false);
        isAirplaneScene3Initialized = true;
    }

    public static boolean isIsAirplaneScene3Initialized() {
        return isAirplaneScene3Initialized;
    }

    public static void setIsAirplaneScene3Initialized(boolean isAirplaneScene3Initialized) {
        AirplaneScene3.isAirplaneScene3Initialized = isAirplaneScene3Initialized;
    }

    @Override
    protected void handleBackPress() {

        if (gameManager3.getGameState() == GameManager3.GameState.PAUSE){
            resume();
        }
        else if (gameManager3.getGameState() == GameManager3.GameState.GAME_OVER || gameManager3.getGameState() == GameManager3.GameState.INIT){
            doNotEnterInRender = true;
            gameManager3.setMenuSceneScreen();
        }
        else {
            pause();
        }
    }

    @Override
    public void show() {

        firstEnter = 1;
        System.out.println("In AirplaneScene3 show method");
    }

    @Override
    public void render(float delta) {

        super.render(delta);
        if(doNotEnterInRender){
            return; // без этого после касания BACK в GAME_OVER выдает glUseProgram:1573 GL error 0x501 после строки gameManager.updateScene()
        }

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined); // устанавливаем в экземпляр spritebatch вид с камеры (области просмотра)
        batch.begin();
        gameManager3.renderGame(batch);
        gameManager3.updateScene(batch);
        plane.renderPlane(batch);
        plane.update();
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

        // При первом входе нам не нужно сюда заходить. А после того как кнопка HOME будет нажата, нужно чтобы при возврате в игру стояла пауза.
        if (firstEnter != 1) {
            gameManager3.setGameState(GameManager3.GameState.PAUSE);
        }
        firstEnter = 2;
        System.out.println("In AirplaneScene3 resize method");

    }

    @Override
    public void pause() {

        gameManager3.setGameState(GameManager3.GameState.PAUSE);
        System.out.println("In AirplaneScene3 pause method");
    }

    @Override
    public void resume() {

        Gdx.input.setInputProcessor(gameManager3.getInputManager());
        gameManager3.setGameState(GameManager3.GameState.ACTION);
        System.out.println("In AirplaneScene3 resume method");
    }

    @Override
    public void hide() {

        System.out.println("In AirplaneScene3 hide method");
        isAirplaneScene3Initialized = false;
        dispose();
    }

    @Override
    public void dispose () {

        System.out.println("In AirplaneScene3 dispose method");
        gameManager3.dispose();

    }

}
