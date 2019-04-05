package com.airplane.game.GameObjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Pickup {

    private static final int STAR = 1;
    private static final int SHIELD = 2;
    private static final int FUEL = 3;
    private Vector3 pickUpTiming;
    TextureRegion pickUpTexture;
    Vector2 pickUpPosition;
    int pickUpType, pickUpValue;
    Sound pickUpSound;

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

    private void checkAndCreatePickUp(float delta){

        pickUpTiming.sub(delta);
        if(pickUpTiming.x <= 0){

        }
    }
}
