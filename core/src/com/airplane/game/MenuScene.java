package com.airplane.game;

import com.airplane.game.Managers.TextManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

public class MenuScene extends BaseScene {

    private TextureAtlas menuAtlas;
    private Stage stage;
    private Image screenBg;
    private Image title;
    private Label.LabelStyle bestScoreLableStyle;
    private Label bestScoreLevel1;
    private Label bestScoreLevel2;
    private Label bestScoreLevel3;
    private Table tableLevel1BestScore;
    private Table tableLevel2BestScore;
    private Table tableLevel3BestScore;
    private CheckBox.CheckBoxStyle muteCheckBoxStyle;
    private CheckBox muteCheckBox;
    private TextButton.TextButtonStyle backChooseLevelTextButtonStyle;
    private TextButton backChooseLevelTextButton;
    private TextButton backOptionsTextButton;
    private TextButton.TextButtonStyle chooseLevelTextButtonStyle;
    private TextButton chooseLevelTextButton;
    private TextButton.TextButtonStyle soundOptionsTextButtonStyle;
    private TextButton soundOptionsTextButton;
    private TextButton.TextButtonStyle leaderBoardTextButtonStyle;
    private TextButton leaderBoardTextButton;
    private TextButton.TextButtonStyle exitTextButtonStyle;
    private TextButton exitTextButton;
    private TextButton.TextButtonStyle level1TextButtonStyle;
    private TextButton level1TextButton;
    private TextButton.TextButtonStyle level2TextButtonStyle;
    private TextButton level2TextButton;
    private TextButton.TextButtonStyle level3TextButtonStyle;
    private TextButton level3TextButton;
    private boolean menuShown;
    private static boolean isMenuSceneInitialised;
    private Airplane airplane;
    private TextManager textManager;
    private SpriteBatch batch;

    public MenuScene(final Airplane airplane) {

        super(airplane);
        this.airplane = airplane;
        menuAtlas = this.airplane.manager.get("menuAtlas.txt", TextureAtlas.class);
        stage = new Stage(this.airplane.getViewport());
        Gdx.input.setInputProcessor(stage);
        batch = this.airplane.batch;
        textManager = new TextManager(this.airplane);

        screenBg = new Image(this.airplane.atlas.findRegion("background"));
        screenBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        title = new Image(this.airplane.manager.get("title.png", Texture.class));
        title.setSize(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/8);
        title.setScaling(Scaling.stretch);

        /*
        helpTip = new Label("Tap around the plane to move it!\nCollect the Stars to get the HighScore!\n" +
                "Collect Fuel to keep fly!\nCollect Shield to be invincible to Rocks and Meteors!", skin);
        helpTip.debug();
        helpTip.setSize(Gdx.graphics.getWidth()/1.3f, Gdx.graphics.getHeight()/4);
        helpTip.setColor(Color.NAVY);
        helpTip.setAlignment(Align.center);
        */

        bestScoreLableStyle = new Label.LabelStyle();
        bestScoreLableStyle.font = this.airplane.manager.get("june.fnt");
        bestScoreLableStyle.fontColor = Color.BLACK;

        tableLevel1BestScore = new Table().debug();
        tableLevel1BestScore.setPosition(100,500);
        for (int i = 1; i <= 10; i++){

            bestScoreLevel1 = new Label(" " + airplane.getSaveManager().loadDataValue("Score" + i, int.class), bestScoreLableStyle);
            tableLevel1BestScore.add(bestScoreLevel1).padBottom(2).align(Align.left);
            tableLevel1BestScore.row();
        }

        tableLevel2BestScore = new Table().debug();
        tableLevel2BestScore.setPosition(200, 500);
        for(int i = 11; i <= 20; i++){

            bestScoreLevel2 = new Label( " " +airplane.getSaveManager().loadDataValue("Score" + i, int.class), bestScoreLableStyle );
            tableLevel2BestScore.add(bestScoreLevel2).padBottom(2).align(Align.center);
            tableLevel2BestScore.row();
        }



        chooseLevelTextButtonStyle = new TextButton.TextButtonStyle();
        chooseLevelTextButtonStyle.font = this.airplane.manager.get("june.fnt"); // без этого выскакивает IllegalArgumentException: Missing LabelStyle font
        chooseLevelTextButtonStyle.up = new TextureRegionDrawable(menuAtlas.findRegion("playButtonUp")) ;
        chooseLevelTextButtonStyle.down = new TextureRegionDrawable(menuAtlas.findRegion("playButtonDown"));
        chooseLevelTextButton = new TextButton("", chooseLevelTextButtonStyle);
        chooseLevelTextButton.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/8);
        chooseLevelTextButton.setPosition(-Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/1.45f);

        soundOptionsTextButtonStyle = new TextButton.TextButtonStyle();
        soundOptionsTextButtonStyle.font = this.airplane.manager.get("june.fnt");
        soundOptionsTextButtonStyle.up = new TextureRegionDrawable(menuAtlas.findRegion("soundButtonUp"));
        soundOptionsTextButtonStyle.down = new TextureRegionDrawable(menuAtlas.findRegion("soundButtonDown"));
        soundOptionsTextButton = new TextButton("", soundOptionsTextButtonStyle);
        soundOptionsTextButton.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/8);
        soundOptionsTextButton.setPosition(Gdx.graphics.getWidth() + Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/1.8f);

        leaderBoardTextButtonStyle = new TextButton.TextButtonStyle();
        leaderBoardTextButtonStyle.font = this.airplane.manager.get("june.fnt");
        leaderBoardTextButtonStyle.up = new TextureRegionDrawable(menuAtlas.findRegion("leaderBoardButtonUp"));
        leaderBoardTextButtonStyle.down = new TextureRegionDrawable(menuAtlas.findRegion("leaderBoardButtonDown"));
        leaderBoardTextButton = new TextButton("", leaderBoardTextButtonStyle);
        leaderBoardTextButton.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/8);
        leaderBoardTextButton.setPosition(-Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2.36f);

        exitTextButtonStyle = new TextButton.TextButtonStyle();
        exitTextButtonStyle.font = this.airplane.manager.get("june.fnt");
        exitTextButtonStyle.up = new TextureRegionDrawable(menuAtlas.findRegion("exitButtonUp"));
        exitTextButtonStyle.down = new TextureRegionDrawable(menuAtlas.findRegion("exitButtonDown"));
        exitTextButton = new TextButton("", exitTextButtonStyle);
        exitTextButton.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/8);
        exitTextButton.setPosition(Gdx.graphics.getWidth() + Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3.45f);

        level1TextButtonStyle = new TextButton.TextButtonStyle();
        level1TextButtonStyle.font = this.airplane.manager.get("june.fnt");
        level1TextButtonStyle.up = new TextureRegionDrawable(menuAtlas.findRegion("level1ButtonUp"));
        level1TextButtonStyle.down = new TextureRegionDrawable(menuAtlas.findRegion("level1ButtonDown"));
        level1TextButton = new TextButton("", level1TextButtonStyle);
        level1TextButton.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/8);
        level1TextButton.setPosition(-Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/1.45f);

        level2TextButtonStyle = new TextButton.TextButtonStyle();
        level2TextButtonStyle.font = this.airplane.manager.get("june.fnt");
        level2TextButtonStyle.up = new TextureRegionDrawable(menuAtlas.findRegion("level2ButtonUp"));
        level2TextButtonStyle.down = new TextureRegionDrawable(menuAtlas.findRegion("level2ButtonDown"));
        level2TextButton = new TextButton("", level2TextButtonStyle);
        level2TextButton.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/8);
        level2TextButton.setPosition(Gdx.graphics.getWidth() + Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/1.8f);

        level3TextButtonStyle = new TextButton.TextButtonStyle();
        level3TextButtonStyle.font = this.airplane.manager.get("june.fnt");
        level3TextButtonStyle.up = new TextureRegionDrawable(menuAtlas.findRegion("level3ButtonUp"));
        level3TextButtonStyle.down = new TextureRegionDrawable(menuAtlas.findRegion("level3ButtonDown"));
        level3TextButton = new TextButton("", level3TextButtonStyle);
        level3TextButton.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/8);
        level3TextButton.setPosition(-Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2.36f);

        backChooseLevelTextButtonStyle = new TextButton.TextButtonStyle();
        backChooseLevelTextButtonStyle.font = this.airplane.manager.get("june.fnt");
        backChooseLevelTextButtonStyle.up = new TextureRegionDrawable(menuAtlas.findRegion("backButtonUp"));
        backChooseLevelTextButtonStyle.down = new TextureRegionDrawable(menuAtlas.findRegion("backButtonDown"));
        backChooseLevelTextButton = new TextButton("", backChooseLevelTextButtonStyle);
        backChooseLevelTextButton.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/8);
        backChooseLevelTextButton.setPosition(Gdx.graphics.getWidth() + Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3.45f);

        muteCheckBoxStyle = new CheckBox.CheckBoxStyle();
        muteCheckBoxStyle.font = this.airplane.manager.get("june.fnt");
        muteCheckBoxStyle.checkboxOff = new TextureRegionDrawable(menuAtlas.findRegion("soundOnCheckBox"));
        muteCheckBoxStyle.checkboxOn = new TextureRegionDrawable(menuAtlas.findRegion("soundOffCheckBox"));
        muteCheckBox = new CheckBox("", muteCheckBoxStyle);
        muteCheckBox.getImageCell().size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/8);
        muteCheckBox.getImage().setScaling(Scaling.stretch);
        muteCheckBox.add(muteCheckBox.getLabel()).expand();
        muteCheckBox.setBounds(0,0, 0,300);//без этой строки происходит срабатывание кнопки, при нажатии рядом с ней.

        //muteCheckBox.getLabel().setAlignment(Align.center);
        //muteCheckBox.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/8);
        muteCheckBox.setPosition(-Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2.36f);
        muteCheckBox.setChecked(airplane.isSoundEnabled());

        backOptionsTextButton = new TextButton("", backChooseLevelTextButtonStyle);
        backOptionsTextButton.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/8);
        backOptionsTextButton.setPosition(Gdx.graphics.getWidth() + Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3.45f);

        stage.addActor(screenBg);
        stage.addActor(title);
        //stage.addActor(helpTip);
        stage.addActor(chooseLevelTextButton);
        stage.addActor(soundOptionsTextButton);
        stage.addActor(leaderBoardTextButton);
        stage.addActor(exitTextButton);
        stage.addActor(level1TextButton);
        stage.addActor(level2TextButton);
        stage.addActor(level3TextButton);
        stage.addActor(backChooseLevelTextButton);
        stage.addActor(backOptionsTextButton);
        stage.addActor(muteCheckBox);
        stage.addActor(tableLevel1BestScore);
        stage.addActor(tableLevel2BestScore);
        stage.addActor(bestScoreLevel1);
        stage.addActor(bestScoreLevel2);

        chooseLevelTextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                showMenu(false, true, false);
            }
        });
        level1TextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuScene.this.airplane.setScreen(new AirplaneScene1(MenuScene.this.airplane));
            }
        });
        level2TextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuScene.this.airplane.setScreen(new AirplaneScene2(MenuScene.this.airplane));
            }
        });
        level3TextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuScene.this.airplane.setScreen(new AirplaneScene3(MenuScene.this.airplane));
            }
        });
        soundOptionsTextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showMenu(false, false, true);
            }
        });
        exitTextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                // or System.exit(0);
            }
        });
        muteCheckBox.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                //MenuScene.this.airplane.soundEnabled =! muteCheckBox.isChecked();
                if (airplane.isSoundEnabled() == true) {
                    airplane.setSoundEnabled(false);
                }
                else {
                    airplane.setSoundEnabled(true);
                }
            }
        });
        backOptionsTextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                airplane.saveAll();
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
                airplane.soundVolume = volumeSlider.getValue();
            }
        });*/

    }

    public static boolean isIsMenuSceneInitialised() {
        return isMenuSceneInitialised;
    }

    public static void setIsMenuSceneInitialised(boolean isMenuSceneInitialised) {
        MenuScene.isMenuSceneInitialised = isMenuSceneInitialised;
    }

    @Override
    public void show() {

        isMenuSceneInitialised = true;

        textManager.initialize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        title.setPosition( Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/10);
        //helpTip.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/16);

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

        MoveToAction actionMove1 = Actions.action(MoveToAction.class);//in
        actionMove1.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/10 , Gdx.graphics.getHeight()/1.45f);
        actionMove1.setDuration(1.3f);
        actionMove1.setInterpolation(Interpolation.elasticOut);

        MoveToAction actionMove2 = Actions.action(MoveToAction.class);//in
        actionMove2.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/1.8f);
        actionMove2.setDuration(1.3f);
        actionMove2.setInterpolation(Interpolation.elasticOut);

        MoveToAction actionMove3 = Actions.action(MoveToAction.class);//in
        actionMove3.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/2.36f);
        actionMove3.setDuration(1.3f);
        actionMove3.setInterpolation(Interpolation.elasticOut);

        MoveToAction actionMove4 = Actions.action(MoveToAction.class);//in
        actionMove4.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/3.45f);
        actionMove4.setDuration(1.3f);
        actionMove4.setInterpolation(Interpolation.elasticOut);

        MoveToAction actionMove5 = Actions.action(MoveToAction.class);//out
        actionMove5.setPosition(-Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/1.45f);
        actionMove5.setDuration(0.5f);
        actionMove5.setInterpolation(Interpolation.pow2In);

        MoveToAction actionMove6 = Actions.action(MoveToAction.class);//out
        actionMove6.setPosition(Gdx.graphics.getWidth() + Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/1.8f);
        actionMove6.setDuration(0.5f);
        actionMove6.setInterpolation(Interpolation.pow2In);

        MoveToAction actionMove7 = Actions.action(MoveToAction.class);//out
        actionMove7.setPosition(-Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2.36f);
        actionMove7.setDuration(0.5f);
        actionMove7.setInterpolation(Interpolation.pow2In);

        MoveToAction actionMove8 = Actions.action(MoveToAction.class);//out
        actionMove8.setPosition(Gdx.graphics.getWidth() + Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3.45f);
        actionMove8.setDuration(0.5f);
        actionMove8.setInterpolation(Interpolation.pow2In);

        MoveToAction actionMove9 = Actions.action(MoveToAction.class);//out
        actionMove9.setPosition(-Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2.36f);
        actionMove9.setDuration(0.5f);
        actionMove9.setInterpolation(Interpolation.pow2In);

        MoveToAction actionMove10 = Actions.action(MoveToAction.class);//out
        actionMove10.setPosition(Gdx.graphics.getWidth() + Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3.45f);
        actionMove10.setDuration(0.5f);
        actionMove10.setInterpolation(Interpolation.pow2In);

        if (showMenuFlag){
            chooseLevelTextButton.setVisible(true);
            soundOptionsTextButton.setVisible(true);
            leaderBoardTextButton.setVisible(true);
            exitTextButton.setVisible(true);
            chooseLevelTextButton.addAction(actionMove1);
            soundOptionsTextButton.addAction(actionMove2);
            leaderBoardTextButton.addAction(actionMove3);
            exitTextButton.addAction(actionMove4);
            level1TextButton.setVisible(false);
            level2TextButton.setVisible(false);
            level3TextButton.setVisible(false);
            backChooseLevelTextButton.setVisible(false);
            muteCheckBox.setVisible(false);
            backOptionsTextButton.setVisible(false);
            level1TextButton.addAction(actionMove5);
            level2TextButton.addAction(actionMove6);
            level3TextButton.addAction(actionMove7);
            backChooseLevelTextButton.addAction(actionMove8);
            muteCheckBox.addAction(actionMove9);
            backOptionsTextButton.addAction(actionMove10);
        } else if(showChooseLevel){
            level1TextButton.setVisible(true);
            level2TextButton.setVisible(true);
            level3TextButton.setVisible(true);
            backChooseLevelTextButton.setVisible(true);
            level1TextButton.addAction(actionMove1);
            level2TextButton.addAction(actionMove2);
            level3TextButton.addAction(actionMove3);
            backChooseLevelTextButton.addAction(actionMove4);
            chooseLevelTextButton.setVisible(false);
            soundOptionsTextButton.setVisible(false);
            leaderBoardTextButton.setVisible(false);
            exitTextButton.setVisible(false);
            chooseLevelTextButton.addAction(actionMove5);
            soundOptionsTextButton.addAction(actionMove6);
            leaderBoardTextButton.addAction(actionMove7);
            exitTextButton.addAction(actionMove8);
        } else if(showSoundOptions){
            muteCheckBox.setVisible(true);
            backOptionsTextButton.setVisible(true);
            muteCheckBox.addAction(actionMove3);
            backOptionsTextButton.addAction(actionMove4);
            chooseLevelTextButton.setVisible(false);
            soundOptionsTextButton.setVisible(false);
            leaderBoardTextButton.setVisible(false);
            exitTextButton.setVisible(false);
            chooseLevelTextButton.addAction(actionMove5);
            soundOptionsTextButton.addAction(actionMove6);
            leaderBoardTextButton.addAction(actionMove7);
            exitTextButton.addAction(actionMove8);
        }

        menuShown = showMenuFlag;
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Show the loading screen
        stage.act();
        stage.draw();
        batch.begin();
        textManager.displayMenuMessage(batch);
        batch.end();
        //System.out.println("stage.getViewport().getScreenHeight() = " + stage.getViewport().getScreenHeight());
        //System.out.println("stage.getViewport().getScreenWidth() = " + stage.getViewport().getScreenWidth());

        //Table.drawDebug(stage);
        super.render(delta);

    }

   @Override
    protected void handleBackPress() {
       Gdx.app.exit();
    }

    @Override
    public void dispose () {
        stage.dispose();
    }

}
