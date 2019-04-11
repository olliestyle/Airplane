package com.airplane.game.Managers;

import com.airplane.game.Airplane;
import com.airplane.game.AirplaneScene1;
import com.airplane.game.GameObjects.Pickup;
import com.airplane.game.GameObjects.Plane;
import com.airplane.game.GameObjects.RockPillar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.concurrent.ThreadLocalRandom;

import static com.airplane.game.GameObjects.Plane.planeRect;

public class PickUpSpawnManager{

    private Vector3 pickUpTiming = new Vector3();
    private Array<Pickup> pickupsInScene = new Array<Pickup>();
    private Pickup tempPickUp;
    private AssetManager manager;
    private Rectangle pickUpRect = new Rectangle();
    private int starCount, shieldCount, fuelCount, fuelPercentage;
    private RockPillar rockPillar;
    public Texture testOverlapsPickup;

    // экземпляр rockPillar нужен для того, чтобы пикапы не создавались внутри скалы
    PickUpSpawnManager (Airplane airplane, RockPillar rockPillar){

        manager = airplane.manager;
        this.rockPillar = rockPillar;
    }

    public void checkAndCreatePickUp(float delta){

        testOverlapsPickup = new Texture(Gdx.files.internal("testoverlaps.png"));

        pickUpTiming.sub(delta);
        if(pickUpTiming.x <= 0){

            pickUpTiming.x = (float) (0.5 * Math.random()*0.5);
            if (addPickUp(Pickup.STAR)){
                pickUpTiming.x = (float) (1 + Math.random()*2);
            }
        }
        if(pickUpTiming.y <= 0){
            pickUpTiming.y = (float) (0.5 * Math.random()*0.5);
            if (addPickUp(Pickup.FUEL)){
                pickUpTiming.y = (float) (3 + Math.random()*2);
            }
        }
        if (pickUpTiming.z <= 0){
            pickUpTiming.z = (float) (0.5 * Math.random()*0.5);
            if (addPickUp(Pickup.SHIELD)){
                pickUpTiming.z = (float) (10 + Math.random()*0.5);
            }
        }
    }

    private boolean addPickUp(int pickUpType){

        Vector2 randomPosition = new Vector2();
        randomPosition.x = ThreadLocalRandom.current().nextInt(Gdx.graphics.getWidth() - 100, Gdx.graphics.getWidth());
        randomPosition.y = ThreadLocalRandom.current().nextInt(Gdx.graphics.getHeight()/8 ,Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/8);
        /*System.out.println("rockPillar = " + rockPillar);
        System.out.println("rockPillar.getPillarRect1 = " + rockPillar.getPillarRect1());
        System.out.println("rockPillar.getPillarRect2 = " + rockPillar.getPillarRect2());
        System.out.println("randomPosition = " + randomPosition);*/


        if (rockPillar.getPillarRect3().contains(randomPosition)){
            //System.out.println("I'm here ");
            return false;
        }

        tempPickUp = new Pickup(pickUpType, manager);
        tempPickUp.pickUpPosition.set(randomPosition);
        pickupsInScene.add(tempPickUp);
        return true;
    }

    public void drawPickUp(SpriteBatch batch){

        for(Pickup pickup: pickupsInScene) {
            //System.out.println("array pickup size = " + pickupsInScene.size);

            if (Gdx.graphics.getWidth() <= 800){
                //batch.draw(pickup.pickUpTexture, pickup.pickUpPosition.x, pickup.pickUpPosition.y, Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/25);
                //batch.draw(testOverlapsPickup, pickup.pickUpPosition.x, pickup.pickUpPosition.y, Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/25);
                pickup.pickUpSprite.setPosition(pickup.pickUpPosition.x, pickup.pickUpPosition.y);
                pickup.pickUpSprite.setSize(Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/25);
                pickup.pickUpSprite.draw(batch);
            }
            else if (Gdx.graphics.getWidth() > 1280){
                //batch.draw(pickup.pickUpTexture, pickup.pickUpPosition.x, pickup.pickUpPosition.y, Gdx.graphics.getWidth()/32, Gdx.graphics.getHeight()/25);
                pickup.pickUpSprite.setPosition(pickup.pickUpPosition.x, pickup.pickUpPosition.y);
                pickup.pickUpSprite.setSize(Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/25);
                pickup.pickUpSprite.draw(batch);
            }
            else{
                //batch.draw(pickup.pickUpTexture, pickup.pickUpPosition.x, pickup.pickUpPosition.y, Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/25);
                //batch.draw(testOverlapsPickup, pickup.pickUpPosition.x, pickup.pickUpPosition.y, Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/25);
                pickup.pickUpSprite.setPosition(pickup.pickUpPosition.x, pickup.pickUpPosition.y);
                pickup.pickUpSprite.setSize(Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/25);
                pickup.pickUpSprite.draw(batch);
            }
        }
    }

    public void updatePickUp(){

        for(Pickup pickup: pickupsInScene) {
            pickup.pickUpPosition.x -= Plane.planePosition.x - Plane.planeDefaultPosition.x;
            if(pickup.pickUpPosition.x + pickup.pickUpTexture.getRegionWidth() < -10){
                pickupsInScene.removeValue(pickup, false);
            }
            if (Gdx.graphics.getWidth() <= 800){
                pickUpRect.set(pickup.pickUpPosition.x, pickup.pickUpPosition.y, Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/25);
                pickup.pickUpSprite.rotate((float) (Math.random()*15));
            }
            else if (Gdx.graphics.getWidth() > 1280){
                pickUpRect.set(pickup.pickUpPosition.x, pickup.pickUpPosition.y, Gdx.graphics.getWidth()/32, Gdx.graphics.getHeight()/25);
                pickup.pickUpSprite.rotate((float) (Math.random()*15));
            }
            else{
                pickUpRect.set(pickup.pickUpPosition.x, pickup.pickUpPosition.y, Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/25);
                pickup.pickUpSprite.rotate((float) (Math.random()*15));

            }

            if(planeRect.overlaps(pickUpRect)) {
                pickIt(pickup);
            }
        }
    }

    private void pickIt(Pickup pickup) {
        pickup.pickUpSound.play();
        switch(pickup.pickUpType){
            case Pickup.STAR:
                starCount += pickup.pickUpValue;
                break;
            case Pickup.SHIELD:
                shieldCount = pickup.pickUpValue;
                break;
            case Pickup.FUEL:
                fuelCount = pickup.pickUpValue;
                break;
        }
        pickupsInScene.removeValue(pickup, false);
    }
}
