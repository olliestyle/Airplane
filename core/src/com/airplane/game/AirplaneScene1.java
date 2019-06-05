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


public class AirplaneScene1 extends BaseScene {

	//private static SpriteBatch batch; // область для отрисовки спрайтов нашей игры
	//OrthographicCamera camera; // область просмотра нашей игры + устанавливаем переменные высоты и ширины в качестве области просмотра нашей игры
	//private static Viewport viewport;

	private Stage stage;
	private TextButton.TextButtonStyle resumeButtonStyle;
	private TextButton resumeButton;
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

		stage = new Stage(airplane.getViewport());

		resumeButtonStyle = new TextButton.TextButtonStyle();
		resumeButtonStyle.font = game.manager.get("june.fnt");
		resumeButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resumeButtonUp.png"))));
		resumeButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resumeButtonDown.png"))));
		resumeButton = new TextButton("", resumeButtonStyle);
		resumeButton.setPosition(100, 100);
		resumeButton.setSize(200,200);

		stage.addActor(resumeButton);


		//float height = Gdx.graphics.getHeight();
		//float width = Gdx.graphics.getWidth();
		//camera = new OrthographicCamera();
		//camera.setToOrtho(false);// этим методом мы центруем камеру на половину высоты и половину ширины экрана устройства и устанавливаем переменные высоты и ширины устройства в качестве области просмотра нашей игры
		//viewport = new FillViewport(3000, 1200, camera);
		//batch = new SpriteBatch();

        plane.initialize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gameManager.initialize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(new InputManager(camera, plane));// доступ класса InputManager для получения касаний/нажатий
		AirplaneScene2.setIsAirplaneScene2Initialized(false);
		AirplaneScene3.setIsAirplaneScene3Initialized(false);
		MenuScene.setIsMenuSceneInitialised(false);
		isAirplaneScene1Initialized = true;

		resumeButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				System.out.println("resumeClick");
			}
		});
	}

	public static boolean isIsAirplaneScene1Initialized() {
		return isAirplaneScene1Initialized;
	}

	public static void setIsAirplaneScene1Initialized(boolean isAirplaneScene1Initialized) {
		AirplaneScene1.isAirplaneScene1Initialized = isAirplaneScene1Initialized;
	}

	@Override
	protected void handleBackPress() {System.out.println("back");
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

		super.render(delta);
		if(gamePaused){
			return;
		}
		//System.out.println("In AirplaneScene1 render method");
		/*System.out.println("HEIGHT HERE " + Airplane.camera.viewportHeight);
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
		stage.act();
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {

        System.out.println("In AirplaneScene1 resize method");
		/*
		System.out.println("RESIZE HERE");
		System.out.println("width = " + width);
		System.out.println("height = " + height);
		*/

	    //viewport.update(width,height);

	}

	@Override
	public void pause() {
        System.out.println("In AirplaneScene1 pause method");
		//System.out.println("PAUSE HERE");
		gamePaused = true;

	}

	@Override
	public void resume() {
        System.out.println("In AirplaneScene1 resume method");
		//System.out.println("RESUME HERE");
		gamePaused = false;
	}

	@Override
	public void hide() {
        System.out.println("In AirplaneScene1 hide method");
		//System.out.println("HIDE HERE");
		isAirplaneScene1Initialized = false;
		dispose();
	}

	@Override
	public void dispose () {
        System.out.println("In AirplaneScene1 dispose method");
		//System.out.println("DISPOSE HERE");
		gameManager.dispose();
		stage.dispose();

	}

}
