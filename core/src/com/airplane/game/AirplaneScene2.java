package com.airplane.game;

import com.airplane.game.GameObjects.Plane;
import com.airplane.game.Managers.GameManager2;
import com.airplane.game.Managers.InputManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class AirplaneScene2 extends BaseScene {


    private SpriteBatch batch;
    private OrthographicCamera camera;
    private GameManager2 gameManager2;
    private Plane plane;
    private static boolean isAirplaneScene2Initialized;
    private int firstEnter = 0;
    private boolean doNotEnterInRender;

    public AirplaneScene2 (Airplane airplane){

        super(airplane);
        batch = airplane.batch;
        camera = airplane.camera;
        plane = new Plane(airplane);
        gameManager2 = new GameManager2(airplane, plane);
        plane.setGameManager2(gameManager2);

        plane.initialize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gameManager2.initialize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        AirplaneScene1.setIsAirplaneScene1Initialized(false);
        AirplaneScene3.setIsAirplaneScene3Initialized(false);
        MenuScene.setIsMenuSceneInitialised(false);
        isAirplaneScene2Initialized = true;
    }

    public static boolean isIsAirplaneScene2Initialized() {
        return isAirplaneScene2Initialized;
    }

    public static void setIsAirplaneScene2Initialized(boolean isAirplaneScene2Initialized) {
        AirplaneScene2.isAirplaneScene2Initialized = isAirplaneScene2Initialized;
    }

    @Override
    protected void handleBackPress() {

        if (gameManager2.getGameState() == GameManager2.GameState.PAUSE){
            resume();
        }
        else if (gameManager2.getGameState() == GameManager2.GameState.GAME_OVER || gameManager2.getGameState() == GameManager2.GameState.INIT){
            doNotEnterInRender = true;
            gameManager2.setMenuSceneScreen();
        }
        else {
            pause();
        }
    }

    @Override
    public void show() {

        firstEnter = 1;
        System.out.println("In AirplaneScene2 show method");
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
        gameManager2.renderGame(batch);
        gameManager2.updateScene(batch);
        plane.renderPlane(batch);
        plane.update();
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

        System.out.println("In AirplaneScene2 resize method");

        // При первом входе нам не нужно сюда заходить. А после того как кнопка HOME будет нажата, нужно чтобы при возврате в игру стояла пауза.
        if (firstEnter != 1) {
            gameManager2.setGameState(GameManager2.GameState.PAUSE);
        }
        firstEnter = 2;
        System.out.println("In AirplaneScene1 resize method");
    }

    @Override
    public void pause() {

        gameManager2.setGameState(GameManager2.GameState.PAUSE);
        System.out.println("In AirplaneScene2 pause method");

    }

    @Override
    public void resume() {

        Gdx.input.setInputProcessor(gameManager2.getInputManager());
        gameManager2.setGameState(GameManager2.GameState.ACTION);
        System.out.println("In AirplaneScene2 resume method");

    }

    @Override
    public void hide() {

        System.out.println("In AirplaneScene2 hide method");
        isAirplaneScene2Initialized = false;
        dispose();
    }

    @Override
    public void dispose () {

        System.out.println("In AirplaneScene2 dispose method");
        gameManager2.dispose();
    }

}
