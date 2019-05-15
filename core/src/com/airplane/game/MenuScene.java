package com.airplane.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScene extends ScreenAdapter {

    private Stage stage;
    private Image screenBg;
    private Skin skin;
    private Image title;
    private Label helpTip;
    private Table table;
    private Table options;
    private CheckBox muteCheckBox;
    private Slider volumeSlider;
    private TextButton backButton;
    private TextButton Level1PlayButton;
    private TextButton Level2PlayButton;
    private TextButton optionsButton;
    private TextButton exitButton;
    private boolean menuShown;
    private Airplane game;

    public MenuScene(final Airplane airplane) {

        //super(airplane);
        game = airplane;

        stage = new Stage(game.getViewport());
        Gdx.input.setInputProcessor(stage);
        //skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));

        screenBg = new Image(game.atlas.findRegion("background"));
        screenBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        title = new Image(game.manager.get("title.png", Texture.class));
        title.setSize(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/8);

        helpTip = new Label("Tap around the plane to move it!", skin);
        helpTip.setSize(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/8);
        helpTip.setColor(Color.NAVY);

        table = new Table();
        Level1PlayButton = new TextButton("Level 1", skin);
        table.add(Level1PlayButton).padBottom(15);
        table.row();
        Level2PlayButton = new TextButton("Level 2", skin);
        table.add(Level2PlayButton).padBottom(15);
        table.row();
        optionsButton=new TextButton("Sound Options", skin);
        table.add(optionsButton).padBottom(15);
        table.row();
        table.add(new TextButton("LeadetBoard", skin)).padBottom(15);
        table.row();
        exitButton = new TextButton("Exit Game", skin);
        table.add(exitButton);
        table.setSize(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/8);
        table.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6 , -Gdx.graphics.getHeight()/3);

        options = new Table();
        Label soundTitle = new Label("SOUND OPTIONS",skin);
        soundTitle.setColor(Color.NAVY);
        options.add(soundTitle).padBottom(25).colspan(2);
        options.row();
        muteCheckBox = new CheckBox(" MUTE ALL", skin);
        options.add(muteCheckBox).padBottom(10).colspan(2);
        //options.row();
        //options.add(new Label("VOLUME ",skin)).padBottom(10).padRight(10);
        //volumeSlider = new Slider(0, 2, 0.2f, false, skin);
        //options.add(volumeSlider).padTop(10).padBottom(20);
        options.row();
        backButton = new TextButton("BACK", skin);
        options.add(backButton).colspan(2).padTop(20);
        options.setSize(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/8);
        options.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, -Gdx.graphics.getHeight()/3);
        muteCheckBox.setChecked(!game.soundEnabled);
        //volumeSlider.setValue(game.soundVolume);

        stage.addActor(screenBg);
        stage.addActor(title);
        stage.addActor(helpTip);
        stage.addActor(table);
        stage.addActor(options);

        Level1PlayButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new AirplaneScene1(game));
            }
        });
        Level2PlayButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new AirplaneScene2(game));
            }
        });
        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showMenu(false);
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                // or System.exit(0);
            }
        });

        /*volumeSlider.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.soundVolume = volumeSlider.getValue();
            }
        });*/
        muteCheckBox.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.soundEnabled =! muteCheckBox.isChecked();
            }
        });
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showMenu(true);
            }
        });
    }

    @Override
    public void show() {

        title.setPosition( Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/6);
        helpTip.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/12, Gdx.graphics.getHeight()/20);

        MoveToAction actionMove = Actions.action(MoveToAction.class);
        actionMove.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, (float) (Gdx.graphics.getHeight()/1.5));
        actionMove.setDuration(2);
        actionMove.setInterpolation(Interpolation.elasticOut);
        title.addAction(actionMove);

        showMenu(true);
    }

    @Override
    public void resize(int width, int height) {

    }

    private void showMenu(boolean flag) {

        MoveToAction actionMove1 = Actions.action(MoveToAction.class);//out
        actionMove1.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, -Gdx.graphics.getHeight()/3);
        actionMove1.setDuration(1);
        actionMove1.setInterpolation(Interpolation.swingIn);

        MoveToAction actionMove2 = Actions.action(MoveToAction.class);//in
        actionMove2.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, (float) (Gdx.graphics.getHeight()/2.5));
        actionMove2.setDuration(1.5f);
        actionMove2.setInterpolation(Interpolation.swing);

        if(flag){
            table.addAction(actionMove2);
            options.addAction(actionMove1);
        }else{
            options.addAction(actionMove2);
            table.addAction(actionMove1);
        }
        menuShown = flag;
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Show the loading screen
        stage.act();
        stage.draw();

        System.out.println("stage.getViewport().getScreenHeight() = " + stage.getViewport().getScreenHeight());
        System.out.println("stage.getViewport().getScreenWidth() = " + stage.getViewport().getScreenWidth());

        //Table.drawDebug(stage);
        super.render(delta);
    }

    //@Override
    protected void handleBackPress() {
        if(!menuShown){
            showMenu(!menuShown);
        }
    }

    @Override
    public void dispose () {
        stage.dispose();
        skin.dispose();
    }


}
