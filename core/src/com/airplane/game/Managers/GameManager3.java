package com.airplane.game.Managers;

import com.airplane.game.Airplane;
import com.airplane.game.GameObjects.Meteor;
import com.airplane.game.GameObjects.Plane;
import com.airplane.game.GameObjects.RockPillar;
import com.airplane.game.GameObjects.Terrain;
import com.airplane.game.MenuScene;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class GameManager3 {

    private TextureRegion backGroundRegion;
    private GameState gameState;
    private Music mainMusic;
    private Terrain terrain;
    private TextManager textManager;
    private Meteor meteor;
    private Meteor meteor2;
    private Meteor meteor3;
    private RockPillar rockPillar;
    private RockPillar rockPillar2;
    private RockPillar rockPillar3;
    private TextureAtlas atlas;
    private PickUpSpawnManager pickUpSpawnManager;
    private Plane plane;
    private Airplane airplane;
    private InputManager inputManager;

    private Stage stageTryAgain;
    private Stage stageResume;
    private TextButton.TextButtonStyle resumeButtonStyle;
    private TextButton resumeButton;
    private TextButton.TextButtonStyle tryAgainButtonStyle;
    private TextButton tryAgainButton;
    private TextButton.TextButtonStyle menuButtonStyle;
    private TextButton menuButton;
    private TextButton.TextButtonStyle menuPauseButtonStyle;
    private TextButton menuPauseButton;

    public GameManager3(final Airplane airplane, Plane plane) {

        this.airplane = airplane;
        atlas = airplane.atlas;
        this.plane = plane;
        terrain = new Terrain(airplane, plane, this);
        meteor = new Meteor(airplane, this);
        meteor2 = new Meteor(airplane, this);
        meteor3 = new Meteor(airplane, this);
        rockPillar = new RockPillar(airplane, plane, this);
        rockPillar2 = new RockPillar(airplane, plane, this);
        rockPillar3 = new RockPillar(airplane, plane, this);
        pickUpSpawnManager = new PickUpSpawnManager(airplane, rockPillar, plane, this); // нам нужно получить именно ту скалу, которая отрисована на данный момент
        textManager = new TextManager(airplane, pickUpSpawnManager, this);
        inputManager = new InputManager(airplane.camera, plane);
        Gdx.input.setInputProcessor(inputManager);// доступ класса InputManager для получения касаний/нажатий

        stageTryAgain = new Stage(airplane.getViewport());
        stageResume = new Stage(airplane.getViewport());

        resumeButtonStyle = new TextButton.TextButtonStyle();
        resumeButtonStyle.font = airplane.manager.get("june.fnt");
        resumeButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resumeButtonUp.png"))));
        resumeButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resumeButtonDown.png"))));
        resumeButton = new TextButton("", resumeButtonStyle);
        resumeButton.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        resumeButton.setPosition(Gdx.graphics.getWidth()/2 - resumeButton.getWidth()*1.3f , Gdx.graphics.getHeight()/2 - resumeButton.getHeight()/2);


        tryAgainButtonStyle = new TextButton.TextButtonStyle();
        tryAgainButtonStyle.font = airplane.manager.get("june.fnt");
        tryAgainButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tryAgainButtonUp.png"))));
        tryAgainButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tryAgainButtonDown.png"))));
        tryAgainButton = new TextButton("", tryAgainButtonStyle);
        tryAgainButton.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        tryAgainButton.setPosition(Gdx.graphics.getWidth()/2 - tryAgainButton.getWidth()*1.3f , Gdx.graphics.getHeight()/2 - tryAgainButton.getHeight()/2);


        menuButtonStyle = new TextButton.TextButtonStyle();
        menuButtonStyle.font = airplane.manager.get("june.fnt");
        menuButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("menuButtonUp.png"))));
        menuButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("menuButtonDown.png"))));
        menuButton = new TextButton("", menuButtonStyle);
        menuButton.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        menuButton.setPosition(Gdx.graphics.getWidth()/2 + menuButton.getWidth()*0.3f , Gdx.graphics.getHeight()/2 - menuButton.getHeight()/2);


        menuPauseButtonStyle = new TextButton.TextButtonStyle();
        menuPauseButtonStyle.font = airplane.manager.get("june.fnt");
        menuPauseButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("menuButtonUp.png"))));
        menuPauseButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("menuButtonDown.png"))));
        menuPauseButton = new TextButton("", menuButtonStyle);
        menuPauseButton.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        menuPauseButton.setPosition(Gdx.graphics.getWidth()/2 + menuPauseButton.getWidth()*0.3f , Gdx.graphics.getHeight()/2 - menuButton.getHeight()/2);


        stageTryAgain.addActor(tryAgainButton);
        stageTryAgain.addActor(menuButton);

        stageResume.addActor(resumeButton);
        stageResume.addActor(menuPauseButton);

        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("resume Clicked");
                Gdx.input.setInputProcessor(inputManager);
                gameState = GameManager3.GameState.ACTION;
            }
        });

        tryAgainButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                resetScene();
                gameState = GameManager3.GameState.INIT;
            }
        });

        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                airplane.setScreen(new MenuScene(airplane));
            }
        });

        menuPauseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                airplane.setScreen(new MenuScene(airplane));
            }
        });
    }

    public void setMenuSceneScreen(){
        airplane.setScreen(new MenuScene(airplane));
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public enum GameState{
        INIT, ACTION, PAUSE, GAME_OVER
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void initialize(float width, float height){

        System.out.println("atlas = " + atlas);
        backGroundRegion = atlas.findRegion("background");

        if (airplane.soundEnabled) {
            mainMusic = airplane.manager.get("journey.mp3");
            mainMusic.setLooping(true);
            mainMusic.play();
        }
        gameState = GameState.INIT;

        terrain.initializeTerrain();
        textManager.initialize(width,height);
        rockPillar.initializePillar();
        rockPillar2.initializePillar();
        rockPillar3.initializePillar();
        meteor.initializeMeteor();
        meteor2.initializeMeteor();
        meteor3.initializeMeteor();

    }

    public void renderGame(SpriteBatch batch) {

        batch.disableBlending(); // blending - смешивание
        batch.draw(backGroundRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // отрисовываем задний фон
        batch.enableBlending(); // blending - смешивание
        rockPillar.renderPillar(batch);
        rockPillar2.renderPillar(batch);
        rockPillar3.renderPillar(batch);
        meteor.renderMeteor(batch);
        meteor2.renderMeteor(batch);
        meteor3.renderMeteor(batch);
        terrain.renderTerrain(batch);
        textManager.displayMessage(batch);
        pickUpSpawnManager.drawPickUp(batch);
    }

    public void updateScene(SpriteBatch batch){

        switch (gameState){

            case INIT:

                if(Gdx.input.justTouched()) {
                    gameState = GameState.ACTION;
                }
                break;

            case ACTION:

                // объекты, движущиеся относительно самолета должны обновляться перед присваиванием дефолтной позиции самолета по x
                rockPillar.updatePillar(pickUpSpawnManager);
                rockPillar2.updatePillar(pickUpSpawnManager);
                rockPillar3.updatePillar(pickUpSpawnManager);
                terrain.updateTerrain();
                pickUpSpawnManager.updatePickUp();
                plane.getPlanePosition().x = plane.getPlaneDefaultPosition().x; // Имитация нахождения самолета на одном месте по x. Самолет стоит на месте. Все остальные объекты перемещаются относительно него
                meteor.updateMeteor(pickUpSpawnManager);
                meteor2.updateMeteor(pickUpSpawnManager);
                meteor3.updateMeteor(pickUpSpawnManager);
                pickUpSpawnManager.checkAndCreatePickUp(Gdx.graphics.getDeltaTime());

                break;

            case PAUSE:

                stageResume.act();
                stageResume.draw();
                Gdx.input.setInputProcessor(stageResume);
                break;


            case GAME_OVER:

                plane.renderPlane(batch);
                stageTryAgain.act();
                stageTryAgain.draw();
                Gdx.input.setInputProcessor(stageTryAgain);

                break;

            default:

                break;
        }
    }

    public void resetScene(){

        meteor.resetMeteor();
        meteor2.resetMeteor();
        meteor3.resetMeteor();
        plane.resetPlane();
        pickUpSpawnManager.resetPickup();
        rockPillar.resetPillar();
        rockPillar2.resetPillar();
        rockPillar3.resetPillar();
        Gdx.input.setInputProcessor(inputManager);
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void dispose(){

        //atlas.dispose();
        mainMusic.dispose();
    }

}
