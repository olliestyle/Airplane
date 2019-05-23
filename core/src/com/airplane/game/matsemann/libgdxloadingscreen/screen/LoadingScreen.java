package com.airplane.game.matsemann.libgdxloadingscreen.screen;

import com.airplane.game.Airplane;
import com.airplane.game.AirplaneScene1;
import com.airplane.game.MenuScene;
import com.airplane.game.matsemann.libgdxloadingscreen.LoadingBar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Scaling;
//import com.csharks.thrustcopter.MenuScene;
//import com.csharks.thrustcopter.ThrustCopter;

/**
 * @author Mats Svensson
 */
public class LoadingScreen extends ScreenAdapter {

	private Stage stage;

	private ParticleEffectLoader.ParticleEffectParameter pep;

	private Image logo;
	private Image loadingFrame;
	private Image loadingBarHidden;
	private Image screenBg;
	private Image loadingBg;

	private float startX, endX;
	private float percent;

	private Actor loadingBar;
	private Airplane game;
	private float waitTime=1;

	public LoadingScreen(Airplane airplane) {

		System.out.println("IN LoadingScreen constructor");
		game = airplane;
	}

	@Override
	public void show() {
		//stage.setViewport(game.viewport);

		System.out.println("IN LoadingScreen show method");

		// Tell the manager to load assets for the loading screen
		game.manager.load("loading.pack", TextureAtlas.class);
		// Wait until they are finished loading
		game.manager.finishLoading();

		// Initialize the stage where we will place everything
		stage = new Stage(game.getViewport());

		// Get our textureatlas from the manager
		TextureAtlas atlas = game.manager.get("loading.pack", TextureAtlas.class);

		// Grab the regions from the atlas and create some images
		logo = new Image(atlas.findRegion("libgdx-logo"));
		loadingFrame = new Image(atlas.findRegion("loading-frame"));
		loadingBarHidden = new Image(atlas.findRegion("loading-bar-hidden"));
		screenBg = new Image(atlas.findRegion("screen-bg"));
		loadingBg = new Image(atlas.findRegion("loading-frame-bg"));

		// Add the loading bar animation
		Animation anim = new Animation(0.05f, atlas.findRegions("loading-bar-anim"));
		anim.setPlayMode(PlayMode.LOOP_REVERSED);
		loadingBar = new LoadingBar(anim);

		// Or if you only need a static bar, you can do
		// loadingBar = new Image(atlas.findRegion("loading-bar1"));

		// Add all the actors to the stage
		stage.addActor(screenBg);
		stage.addActor(loadingBar);
		stage.addActor(loadingBg);
		stage.addActor(loadingBarHidden);
		stage.addActor(loadingFrame);
		stage.addActor(logo);

		// Add everything to be loaded, for instance:
		//game.manager.load("gameover.png", Texture.class);
		//game.manager.load("thrustcopterassets.txt", TextureAtlas.class);
		//game.manager.load("uiskin.atlas", TextureAtlas.class);
		//game.manager.load("default.fnt", BitmapFont.class);

		pep = new ParticleEffectLoader.ParticleEffectParameter();
		pep.atlasFile = "Airplane.pack";

		//game.manager.load("flat-earth-ui.json", Json.class);
		game.manager.load("title.png", Texture.class);
		game.manager.load("june.fnt", BitmapFont.class);
		game.manager.load("journey.mp3", Music.class);
		game.manager.load("pop.ogg", Sound.class);
		game.manager.load("crash.ogg", Sound.class);
		game.manager.load("star.ogg", Sound.class);
		game.manager.load("shield.ogg", Sound.class);
		game.manager.load("fuel.ogg", Sound.class);
		game.manager.load("Airplane.pack", TextureAtlas.class);
		game.manager.load("fuelBar.png", Texture.class);
		game.manager.load("Smoke", ParticleEffect.class, pep);
		game.manager.load("Explosion", ParticleEffect.class, pep);

	}

	@Override
	public void resize(int width, int height) {
		// Scale the viewport to fit the screen

		System.out.println("IN LoadingScreen resize method");

		//Vector2 scaledView = Scaling.fit.apply(800, 480, width, height);
		//stage.getViewport().update((int)scaledView.x, (int)scaledView.y, true);
		//stage.getViewport();

		// Make the background fill the screen
		screenBg.setSize(width, height);

		// Place the logo in the middle of the screen and 100 px up
		logo.setX((width - logo.getWidth()) / 2);
		logo.setY((height - logo.getHeight()) / 2 + 100);

		// Place the loading frame in the middle of the screen
		loadingFrame.setX((stage.getWidth() - loadingFrame.getWidth()) / 2);
		loadingFrame.setY((stage.getHeight() - loadingFrame.getHeight()) / 2);

		// Place the loading bar at the same spot as the frame, adjusted a few
		// px
		loadingBar.setX(loadingFrame.getX() + 15);
		loadingBar.setY(loadingFrame.getY() + 5);

		// Place the image that will hide the bar on top of the bar, adjusted a
		// few px
		loadingBarHidden.setX(loadingBar.getX() + 35);
		loadingBarHidden.setY(loadingBar.getY() - 3);
		// The start position and how far to move the hidden loading bar
		startX = loadingBarHidden.getX();
		endX = 440;

		// The rest of the hidden bar
		loadingBg.setSize(450, 50);
		loadingBg.setX(loadingBarHidden.getX() + 30);
		loadingBg.setY(loadingBarHidden.getY() + 3);
	}

	@Override
	public void render(float delta) {
		// Clear the screen

		System.out.println("IN LoadingScreen render method");

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		System.out.println("game.manager.update is " + game.manager.update());
		if (game.manager.update()) { // Load some, will return true if done
										// loading
			waitTime -= delta;
			if(waitTime <= 0){
			game.atlas = game.manager.get("Airplane.pack", TextureAtlas.class);
			//game.font = game.manager.get("impact-40.fnt", BitmapFont.class);
			//game.setScreen(new MenuScene(game));
			game.setScreen(new MenuScene(game));
			}
		}

		// Interpolate the percentage to make it more smooth
		percent = Interpolation.linear.apply(percent, game.manager.getProgress(), 0.1f);

		// Update positions (and size) to match the percentage
		loadingBarHidden.setX(startX + endX * percent);
		loadingBg.setX(loadingBarHidden.getX() + 30);
		loadingBg.setWidth(450 - 450 * percent);
		loadingBg.invalidate();

		// Show the loading screen
		stage.act();
		stage.draw();
	}

	@Override
	public void hide() {

		System.out.println("IN LoadingScreen hide method");

		// Dispose the loading assets as we no longer need them
		game.manager.unload("loading.pack");
	}
	
	@Override
	public void dispose () {
		stage.dispose();
	}
}
