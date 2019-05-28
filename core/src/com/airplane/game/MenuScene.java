package com.airplane.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class MenuScene extends ScreenAdapter {


    private TextureAtlas menuAtlas;
    private Stage stage;
    private Image screenBg;
    private Skin skin;
    private Image title;
    private Label helpTip;
    private Table mainTable;
    private Table chooseLevelTable;
    private Table optionsTable;
    private CheckBox muteCheckBox;
    private TextButton backChooseLevelTextButton;
    private TextButton.TextButtonStyle backChooseLevelTextButtonStyle;
    private TextButton.TextButtonStyle chooseLevelTextButtonStyle;
    private TextButton chooseLevelTextButton;
    private TextButton.TextButtonStyle backOptionsTextButtonStyle;
    private TextButton backOptionsTextButton;
    private TextButton level1PlayButton;
    private TextButton level2PlayButton;
    private TextButton level3PlayButton;
    private TextButton optionsButton;
    private TextButton.TextButtonStyle optionsTextButtonStyle;
    private TextButton optionsTextButton;
    private TextButton exitButton;


    private boolean menuShown;
    private Airplane game;

    public MenuScene(final Airplane airplane) {

        //super(airplane);
        game = airplane;
        menuAtlas = game.manager.get("menuAtlas.txt", TextureAtlas.class);
        stage = new Stage(game.getViewport());
        Gdx.input.setInputProcessor(stage);
        //skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));
        //skin = game.manager.get("flat-earth-ui.json");

        screenBg = new Image(game.atlas.findRegion("background"));
        screenBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        title = new Image(game.manager.get("title.png", Texture.class));
        title.setSize(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/8);

        helpTip = new Label("Tap around the plane to move it!\nCollect the Stars to get the HighScore!\n" +
                "Collect Fuel to keep fly!\nCollect Shield to be invincible to Rocks and Meteors ", skin);
        helpTip.setSize(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/8);
        helpTip.setColor(Color.NAVY);
        helpTip.setAlignment(Align.center);

        chooseLevelTextButtonStyle = new TextButton.TextButtonStyle();
        chooseLevelTextButtonStyle.font = game.manager.get("june.fnt"); // без этого выскакивает IllegalArgumentException: Missing LabelStyle font
        chooseLevelTextButtonStyle.up = new TextureRegionDrawable(menuAtlas.findRegion("playButtonUp")) ;
        chooseLevelTextButtonStyle.down = new TextureRegionDrawable(menuAtlas.findRegion("playButtonDown"));
        chooseLevelTextButton = new TextButton("", chooseLevelTextButtonStyle);
        chooseLevelTextButton.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/8);


        backChooseLevelTextButtonStyle = new TextButton.TextButtonStyle();
        backChooseLevelTextButtonStyle.font = game.manager.get("june.fnt");
        backChooseLevelTextButtonStyle.up = new TextureRegionDrawable(menuAtlas.findRegion("backButtonUp"));
        backChooseLevelTextButtonStyle.down = new TextureRegionDrawable(menuAtlas.findRegion("backButtonDown"));
        backChooseLevelTextButton = new TextButton("", backChooseLevelTextButtonStyle);
        backChooseLevelTextButton.setSize(Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/9);

        backOptionsTextButton = new TextButton("", backChooseLevelTextButtonStyle);
        backOptionsTextButton.setSize(Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/15);


        mainTable = new Table();
        //mainTable.add(chooseLevelTextButton).padBottom(20);
        //mainTable.add().row();
        optionsButton = new TextButton("Sound Options", skin);
        mainTable.add(optionsButton).padBottom(15);
        mainTable.row();
        mainTable.add(new TextButton("LeaderBoard", skin)).padBottom(15);
        mainTable.row();
        exitButton = new TextButton("Exit Game", skin);
        mainTable.add(exitButton);
        mainTable.setSize(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/8);
        mainTable.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6 , -Gdx.graphics.getHeight()/3);

        chooseLevelTable = new Table();
        Label chooseLevelTitle = new Label("Choose Level", skin);
        chooseLevelTitle.setColor(Color.NAVY);
        chooseLevelTable.add(chooseLevelTitle).padBottom(25).colspan(2);
        chooseLevelTable.row();
        level1PlayButton = new TextButton("Level 1", skin);
        chooseLevelTable.add(level1PlayButton).padBottom(15);
        chooseLevelTable.row();
        level2PlayButton = new TextButton("Level 2", skin);
        chooseLevelTable.add(level2PlayButton).padBottom(15);
        chooseLevelTable.row();
        level3PlayButton = new TextButton("Level 3", skin);
        chooseLevelTable.add(level3PlayButton).padBottom(15);
        chooseLevelTable.row();
        chooseLevelTable.add(backChooseLevelTextButton).padTop(25);
        chooseLevelTable.setSize(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/8);
        chooseLevelTable.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6 , -Gdx.graphics.getHeight()/3);

        optionsTable = new Table();
        Label soundTitle = new Label("SOUND OPTIONS",skin);
        soundTitle.setColor(Color.NAVY);
        optionsTable.add(soundTitle).padBottom(25).colspan(2);
        optionsTable.row();
        muteCheckBox = new CheckBox(" MUTE ALL", skin);
        optionsTable.add(muteCheckBox).padBottom(10).colspan(2);
        //options.row();
        //options.add(new Label("VOLUME ",skin)).padBottom(10).padRight(10);
        //volumeSlider = new Slider(0, 2, 0.2f, false, skin);
        //options.add(volumeSlider).padTop(10).padBottom(20);
        optionsTable.row();
        optionsTable.add(backOptionsTextButton).colspan(2).padTop(25);
        optionsTable.setSize(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/8);
        optionsTable.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, -Gdx.graphics.getHeight()/3);
        muteCheckBox.setChecked(!game.soundEnabled);
        //volumeSlider.setValue(game.soundVolume);

        stage.addActor(screenBg);
        stage.addActor(title);
        stage.addActor(helpTip);
        stage.addActor(mainTable);
        stage.addActor(chooseLevelTable);
        stage.addActor(optionsTable);
        stage.addActor(chooseLevelTextButton);

        chooseLevelTextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                showMenu(false, true, false);
            }
        });
        level1PlayButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new AirplaneScene1(game));
            }
        });
        level2PlayButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new AirplaneScene2(game));
            }
        });
        level3PlayButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new AirplaneScene3(game));
            }
        });
        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showMenu(false, false, true);
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                // or System.exit(0);
            }
        });
        muteCheckBox.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.soundEnabled =! muteCheckBox.isChecked();
                System.out.println("game.soundEnabled is " + game.soundEnabled);
            }
        });
        backOptionsTextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showMenu(true, false, false);
            }
        });
        backChooseLevelTextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showMenu(true, false, false);
            }
        });

        /*volumeSlider.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.soundVolume = volumeSlider.getValue();
            }
        });*/
    }

    @Override
    public void show() {

        title.setPosition( Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/10);
        helpTip.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/16);
        chooseLevelTextButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/10 , Gdx.graphics.getHeight()/1.4f);

        MoveToAction actionMove = Actions.action(MoveToAction.class);
        actionMove.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, (float) (Gdx.graphics.getHeight()/1.2));
        actionMove.setDuration(2);
        actionMove.setInterpolation(Interpolation.elasticOut);
        title.addAction(actionMove);

        showMenu(true, false, false);
    }

    @Override
    public void resize(int width, int height) {

    }

    private void showMenu(boolean showMenuFlag, boolean showChooseLevel, boolean showSoundOptions) {

        MoveToAction actionMove1 = Actions.action(MoveToAction.class);//out
        actionMove1.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, -Gdx.graphics.getHeight()/3);
        actionMove1.setDuration(1);
        actionMove1.setInterpolation(Interpolation.swingIn);

        MoveToAction actionMove2 = Actions.action(MoveToAction.class);//in
        actionMove2.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, (float) (Gdx.graphics.getHeight()/2.5));
        actionMove2.setDuration(1.5f);
        actionMove2.setInterpolation(Interpolation.swing);

        MoveToAction actionMove3 = Actions.action(MoveToAction.class);//in
        actionMove3.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, (float) (Gdx.graphics.getHeight()/2.5));
        actionMove3.setDuration(1.5f);
        actionMove3.setInterpolation(Interpolation.swing);

        MoveToAction actionMove4 = Actions.action(MoveToAction.class);//out
        actionMove4.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, -Gdx.graphics.getHeight()/3);
        actionMove4.setDuration(1);
        actionMove4.setInterpolation(Interpolation.swingIn);

        if (showMenuFlag){
            mainTable.addAction(actionMove2);
            optionsTable.addAction(actionMove1);
            chooseLevelTable.addAction(actionMove4);
        } else if(showChooseLevel){
            chooseLevelTable.addAction(actionMove2);
            mainTable.addAction(actionMove1);
            optionsTable.addAction(actionMove4);
        } else if(showSoundOptions){
            optionsTable.addAction(actionMove2);
            mainTable.addAction(actionMove1);
            chooseLevelTable.addAction(actionMove4);
        }

        /*if(showMenuFlag){
            mainTable.addAction(actionMove2);
            optionsTable.addAction(actionMove1);
            chooseLevelTable.addAction(actionMove3);
        }else{
            optionsTable.addAction(actionMove2);
            chooseLevelTable.addAction(actionMove1);
            mainTable.addAction(actionMove1);
        }*/

        menuShown = showMenuFlag;
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Show the loading screen
        stage.act();
        stage.draw();

        //System.out.println("stage.getViewport().getScreenHeight() = " + stage.getViewport().getScreenHeight());
        //System.out.println("stage.getViewport().getScreenWidth() = " + stage.getViewport().getScreenWidth());

        //Table.drawDebug(stage);
        super.render(delta);
    }

   /* //@Override
    protected void handleBackPress() {
        if(!menuShown){
            showMenu(!menuShown);
        }
    }*/

    @Override
    public void dispose () {
        stage.dispose();
        skin.dispose();
    }


}
