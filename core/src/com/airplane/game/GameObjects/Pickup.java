package com.airplane.game.GameObjects;

import com.airplane.game.Airplane;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


public class Pickup {

    public static final int STAR = 1;
    public static final int SHIELD = 2;
    public static final int FUEL = 3;

    public TextureRegion pickUpTexture;
    public int pickUpType, pickUpValue;
    public Sound pickUpSound;
    public Vector2 pickUpPosition = new Vector2();

    public Pickup(int pickUpType, AssetManager manager){

        TextureAtlas atlas = manager.get("Airplane.pack", TextureAtlas.class);
        this.pickUpType = pickUpType;

        switch (pickUpType){
            case STAR:
                pickUpTexture = atlas.findRegion("star_pickup");
                pickUpValue = 5;
                pickUpSound = manager.get("star.ogg", Sound.class);
                break;
            case SHIELD:
                pickUpTexture = atlas.findRegion("shield_pickup");
                pickUpValue = 15;
                pickUpSound = manager.get("shield.ogg");
                break;
            case FUEL:
                pickUpTexture = atlas.findRegion("fuel_pickup");
                pickUpValue = 100;
                pickUpSound = manager.get("fuel.ogg");
                break;
        }
    }

}
