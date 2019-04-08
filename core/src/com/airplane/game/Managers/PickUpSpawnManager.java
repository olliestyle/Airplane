package com.airplane.game.Managers;

import com.airplane.game.Airplane;
import com.airplane.game.AirplaneScene1;
import com.airplane.game.GameObjects.Pickup;
import com.airplane.game.GameObjects.RockPillar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.concurrent.ThreadLocalRandom;

public class PickUpSpawnManager {

    Airplane game;
    AirplaneScene1 airplaneScene1;
    private Vector3 pickUpTiming = new Vector3();
    Array<Pickup> pickupsInScene = new Array<Pickup>();
    Pickup tempPickUp;

    public PickUpSpawnManager(Airplane game) {

        this.game = game;
    }


    public void checkAndCreatePickUp(float delta){

        pickUpTiming.sub(delta);
        if(pickUpTiming.x <= 0){

            pickUpTiming.x = (float) (0.5 * Math.random()*0.5);
            if (addPickUp(Pickup.getSTAR())){
                pickUpTiming.x = (float) (1 + Math.random()*2);
            }
        }
        if(pickUpTiming.y <= 0){
            pickUpTiming.y = (float) (0.5 * Math.random()*0.5);
            if (addPickUp(Pickup.getFUEL())){
                pickUpTiming.y = (float) (3 + Math.random()*2);
            }
        }
        if (pickUpTiming.z <= 0){
            pickUpTiming.z = (float) (0.5 * Math.random()*0.5);
            if (addPickUp(Pickup.getSHIELD())){
                pickUpTiming.z = (float) (10 + Math.random()*0.5);
            }
        }
    }

    private boolean addPickUp(int pickUpType){

        Vector2 randomPosition = new Vector2();
        randomPosition.x = ThreadLocalRandom.current().nextInt(100, Gdx.graphics.getHeight()-100);
        randomPosition.y = ThreadLocalRandom.current().nextInt(Gdx.graphics.getWidth() - 50, Gdx.graphics.getWidth());
        /*System.out.println("rockPillar in gameManager = " + gameManager.getRockPillar());

        for (Vector2 vec: gameManager.getRockPillar().getPillars()){

            if (gameManager.getRockPillar().getPillarRect1().contains(randomPosition) || gameManager.getRockPillar().getPillarRect2().contains(randomPosition)){
                return false;
            }
        }*/
        tempPickUp = new Pickup(pickUpType, game.manager);
        tempPickUp.pickUpPosition.set(randomPosition);
        //must be an array of pickups
        return true;
    }

}
