package com.airplane.game.GameObjects;

import com.airplane.game.Airplane;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


public class Pickup {

    public static final int STAR = 1;
    public static final int SHIELD = 2;
    public static final int FUEL = 3;

    private TextureRegion pickUpTexture;
    private Sprite pickUpSprite;
    private int pickUpType, pickUpValue;
    private Sound pickUpSound;
    private Vector2 pickUpPosition = new Vector2();
    private int pickUpDown;
    private boolean isPickUpMoveUp;
    private boolean isPickUpMoveDown;

    public Pickup(int pickUpType, AssetManager manager){

        TextureAtlas atlas = manager.get("Airplane.pack", TextureAtlas.class);
        this.pickUpType = pickUpType;

        if(MathUtils.randomBoolean()){
            isPickUpMoveDown = true;
        }
        else {
            isPickUpMoveUp = true;
        }

        switch (pickUpType){
            case STAR:
                pickUpTexture = atlas.findRegion("star_pickup");
                pickUpSprite = new Sprite(pickUpTexture);
                pickUpValue = 1;
                pickUpSound = manager.get("star.ogg", Sound.class);
                break;
            case SHIELD:
                pickUpTexture = atlas.findRegion("shield_pickup");
                pickUpSprite = new Sprite(pickUpTexture);
                pickUpValue = 10;
                pickUpSound = manager.get("shield.ogg");
                break;
            case FUEL:
                pickUpTexture = atlas.findRegion("fuel_pickup");
                pickUpSprite = new Sprite(pickUpTexture);
                pickUpValue = 100;
                pickUpSound = manager.get("fuel.ogg");
                break;
        }
    }

    public void updatePickUpDown(){
        if (isPickUpMoveUp) {
            pickUpDown++;
            if (pickUpDown == Gdx.graphics.getHeight()/50){
                isPickUpMoveUp = false;
                isPickUpMoveDown = true;
            }
        }

        if (isPickUpMoveDown){
            pickUpDown--;
            if (pickUpDown == -Gdx.graphics.getHeight()/50){
                isPickUpMoveDown = false;
                isPickUpMoveUp = true;
            }
        }
    }

    public int getPickUpDown() {
        return pickUpDown;
    }

    public Sprite getPickUpSprite() {
        return pickUpSprite;
    }

    public int getPickUpType() {
        return pickUpType;
    }

    public int getPickUpValue() {
        return pickUpValue;
    }

    public Sound getPickUpSound() {
        return pickUpSound;
    }

    public Vector2 getPickUpPosition() {
        return pickUpPosition;
    }

    public TextureRegion getPickUpTexture() {
        return pickUpTexture;
    }

}
