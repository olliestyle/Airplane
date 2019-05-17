package com.airplane.game.Managers;

import com.airplane.game.Airplane;
import com.airplane.game.AirplaneScene1;
import com.airplane.game.AirplaneScene2;
import com.airplane.game.AirplaneScene3;
import com.airplane.game.GameObjects.Pickup;
import com.airplane.game.GameObjects.Plane;
import com.airplane.game.GameObjects.RockPillar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import java.util.concurrent.ThreadLocalRandom;

import static com.airplane.game.GameObjects.Plane.planeRect;

public class PickUpSpawnManager{

    private Vector3 pickUpTiming = new Vector3();
    private Array<Pickup> pickupsInScene = new Array<Pickup>();
    private Pickup tempPickUp;
    private AssetManager manager;
    private Rectangle pickUpRect = new Rectangle();
    private int starCount = 0, fuelPercentage;
    private float shieldCount = 0;
    private float fuelCount = 100;
    private RockPillar rockPillar;
    public Texture testOverlapsPickup;
    private TextureAtlas atlas;
    private Texture fuelIndicator;
    private Plane plane;
    private Animation shield;
    private float shieldAnimTime;
    private float SHIELD_RESIZE_WIDTH_FACTOR;
    private float SHIELD_RESIZE_HEIGHT_FACTOR;
    private GameManager gameManager;
    private GameManager2 gameManager2;
    private GameManager3 gameManager3;
    private Airplane airplane;

    public void setPlaneResizeWidthFactor(){

        if (Gdx.graphics.getWidth() <= 800){
            SHIELD_RESIZE_WIDTH_FACTOR = 0.6f;
        }
        else if (Gdx.graphics.getWidth() > 1280){
            SHIELD_RESIZE_WIDTH_FACTOR = 1.5f;
        }
        else{
            SHIELD_RESIZE_WIDTH_FACTOR = 0.8f;
        }
    }

    public void setPlaneResizeHeightFactor(){
        if (Gdx.graphics.getHeight() <= 480){
            SHIELD_RESIZE_HEIGHT_FACTOR = 0.6f;
        }
        else if (Gdx.graphics.getHeight() > 768){
            SHIELD_RESIZE_HEIGHT_FACTOR = 1.5f;
        }
        else{
            SHIELD_RESIZE_HEIGHT_FACTOR = 0.8f;
        }
    }

    // экземпляр rockPillar нужен для того, чтобы пикапы не создавались внутри скалы
    PickUpSpawnManager (Airplane airplane, RockPillar rockPillar, Plane plane, GameManager gameManager){

        this.airplane = airplane;
        atlas = airplane.atlas;
        manager = airplane.manager;
        this.plane = plane;
        this.rockPillar = rockPillar;
        this.gameManager = gameManager;
        fuelIndicator = manager.get("fuelBar.png");
        shield = new Animation(0.05f, atlas.findRegion("shield1"), atlas.findRegion("shield2"), atlas.findRegion("shield3")
                , atlas.findRegion("shield4"), atlas.findRegion("shield5"), atlas.findRegion("shield6"), atlas.findRegion("shield7")
                , atlas.findRegion("shield8"), atlas.findRegion("shield9"), atlas.findRegion("shield10"), atlas.findRegion("shield11")); // инициализация анимации объекта plane
        shield.setPlayMode(Animation.PlayMode.LOOP); // "запуск" анимации
    }

    PickUpSpawnManager (Airplane airplane, RockPillar rockPillar, Plane plane, GameManager2 gameManager2){

        this.airplane = airplane;
        atlas = airplane.atlas;
        manager = airplane.manager;
        this.plane = plane;
        this.rockPillar = rockPillar;
        this.gameManager2 = gameManager2;
        fuelIndicator = manager.get("fuelBar.png");
        shield = new Animation(0.05f, atlas.findRegion("shield1"), atlas.findRegion("shield2"), atlas.findRegion("shield3")
                , atlas.findRegion("shield4"), atlas.findRegion("shield5"), atlas.findRegion("shield6"), atlas.findRegion("shield7")
                , atlas.findRegion("shield8"), atlas.findRegion("shield9"), atlas.findRegion("shield10"), atlas.findRegion("shield11")); // инициализация анимации объекта plane
        shield.setPlayMode(Animation.PlayMode.LOOP); // "запуск" анимации
    }

    PickUpSpawnManager (Airplane airplane, RockPillar rockPillar, Plane plane, GameManager3 gameManager3){

        this.airplane = airplane;
        atlas = airplane.atlas;
        manager = airplane.manager;
        this.plane = plane;
        this.rockPillar = rockPillar;
        this.gameManager3 = gameManager3;
        fuelIndicator = manager.get("fuelBar.png");
        shield = new Animation(0.05f, atlas.findRegion("shield1"), atlas.findRegion("shield2"), atlas.findRegion("shield3")
                , atlas.findRegion("shield4"), atlas.findRegion("shield5"), atlas.findRegion("shield6"), atlas.findRegion("shield7")
                , atlas.findRegion("shield8"), atlas.findRegion("shield9"), atlas.findRegion("shield10"), atlas.findRegion("shield11")); // инициализация анимации объекта plane
        shield.setPlayMode(Animation.PlayMode.LOOP); // "запуск" анимации
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
        randomPosition.y = ThreadLocalRandom.current().nextInt(Gdx.graphics.getHeight()/6 ,Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/6);
        /*System.out.println("rockPillar = " + rockPillar);
        System.out.println("rockPillar.getPillarRect1 = " + rockPillar.getPillarRect1());
        System.out.println("rockPillar.getPillarRect2 = " + rockPillar.getPillarRect2());
        System.out.println("randomPosition = " + randomPosition);*/

        if (rockPillar.getPillarRect3().contains(randomPosition)){
            //System.out.println("I'm here ");
            return false;
        }

        tempPickUp = new Pickup(pickUpType, manager);
        tempPickUp.getPickUpPosition().set(randomPosition);
        pickupsInScene.add(tempPickUp);
        return true;
    }

    public void drawPickUp(SpriteBatch batch){

        for(Pickup pickup: pickupsInScene) {
            //System.out.println("array pickup size = " + pickupsInScene.size);

            if (Gdx.graphics.getWidth() <= 800){
                //batch.draw(pickup.pickUpTexture, pickup.pickUpPosition.x, pickup.pickUpPosition.y, Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/25);
                //batch.draw(testOverlapsPickup, pickup.pickUpPosition.x, pickup.pickUpPosition.y, Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/25);
                pickup.getPickUpSprite().setPosition(pickup.getPickUpPosition().x, pickup.getPickUpPosition().y + pickup.getPickUpDown());
                pickup.getPickUpSprite().setSize(Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/25);
                pickup.getPickUpSprite().draw(batch);
            }
            else if (Gdx.graphics.getWidth() > 1280){
                //batch.draw(pickup.pickUpTexture, pickup.pickUpPosition.x, pickup.pickUpPosition.y, Gdx.graphics.getWidth()/32, Gdx.graphics.getHeight()/25);
                pickup.getPickUpSprite().setPosition(pickup.getPickUpPosition().x, pickup.getPickUpPosition().y + pickup.getPickUpDown());
                pickup.getPickUpSprite().setSize(Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/25);
                pickup.getPickUpSprite().draw(batch);
            }
            else{
                //batch.draw(pickup.pickUpTexture, pickup.pickUpPosition.x, pickup.pickUpPosition.y, Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/25);
                //batch.draw(testOverlapsPickup, pickup.pickUpPosition.x, pickup.pickUpPosition.y + upDown, Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/25);
                pickup.getPickUpSprite().setPosition(pickup.getPickUpPosition().x, pickup.getPickUpPosition().y + pickup.getPickUpDown());
                pickup.getPickUpSprite().setSize(Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/25);
                pickup.getPickUpSprite().draw(batch);
            }
        }
        if (Gdx.graphics.getWidth() <= 800){
            batch.draw(fuelIndicator, Gdx.graphics.getWidth()/100, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/10, fuelPercentage, Gdx.graphics.getHeight()/40);
            if (getShieldCount() > 0) {
                batch.draw((TextureRegion) shield.getKeyFrame(shieldAnimTime), plane.getPlaneDefaultPosition().x - Gdx.graphics.getWidth() / 65, plane.getPlanePosition().y - Gdx.graphics.getHeight() / 38,
                        Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7);
            }
        }
        else if (Gdx.graphics.getWidth() > 1280){
            batch.draw(fuelIndicator, Gdx.graphics.getWidth()/100, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/10, fuelPercentage, Gdx.graphics.getHeight()/40);
            if (getShieldCount() > 0) {
                batch.draw((TextureRegion) shield.getKeyFrame(shieldAnimTime), plane.getPlaneDefaultPosition().x - Gdx.graphics.getWidth() / 55, plane.getPlanePosition().y - Gdx.graphics.getHeight() / 30,
                        Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7);
            }
        }
        else{
            batch.draw(fuelIndicator, Gdx.graphics.getWidth()/100, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/10, fuelPercentage, Gdx.graphics.getHeight()/40);
            if (getShieldCount() > 0){
                batch.draw((TextureRegion) shield.getKeyFrame(shieldAnimTime), plane.getPlaneDefaultPosition().x - Gdx.graphics.getWidth()/45, plane.getPlanePosition().y - Gdx.graphics.getHeight()/30,
                    Gdx.graphics.getWidth()/10 , Gdx.graphics.  getHeight()/7 );
            }
        }
    }

    public void updatePickUp(){

        fuelCount -= 6*Gdx.graphics.getDeltaTime();
        fuelPercentage = (int) (114*fuelCount/100);
        shieldCount -= Gdx.graphics.getDeltaTime();
        shieldAnimTime += Gdx.graphics.getDeltaTime();

        if(fuelCount < 0){
            System.out.println("Fuel is over");
            if(AirplaneScene1.isIsAirplaneScene1Initialized()){
                if (gameManager.getGameState() != GameManager.GameState.GAME_OVER){
                    gameManager.setGameState(GameManager.GameState.GAME_OVER);
                }
            }
            if(AirplaneScene2.isIsAirplaneScene2Initialized()){
                if (gameManager2.getGameState() != GameManager2.GameState.GAME_OVER){
                    gameManager2.setGameState(GameManager2.GameState.GAME_OVER);
                }
            }
            if(AirplaneScene3.isIsAirplaneScene3Initialized()){
                if (gameManager3.getGameState() != GameManager3.GameState.GAME_OVER){
                    gameManager3.setGameState(GameManager3.GameState.GAME_OVER);
                }
            }
        }

        for(Pickup pickup: pickupsInScene) {

            pickup.getPickUpPosition().x -= plane.getPlanePosition().x - plane.getPlaneDefaultPosition().x;
            pickup.updatePickUpDown();

            if(pickup.getPickUpPosition().x + pickup.getPickUpTexture().getRegionWidth() < -10){
                pickupsInScene.removeValue(pickup, false);
            }
            if (Gdx.graphics.getWidth() <= 800){
                pickUpRect.set(pickup.getPickUpPosition().x, pickup.getPickUpPosition().y + pickup.getPickUpDown(), Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/25);

            }
            else if (Gdx.graphics.getWidth() > 1280){
                pickUpRect.set(pickup.getPickUpPosition().x, pickup.getPickUpPosition().y + pickup.getPickUpDown(), Gdx.graphics.getWidth()/32, Gdx.graphics.getHeight()/25);

            }
            else{
                pickUpRect.set(pickup.getPickUpPosition().x, pickup.getPickUpPosition().y + pickup.getPickUpDown(), Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/25);
            }
            if(planeRect.overlaps(pickUpRect)) {
                pickIt(pickup);
            }
        }
    }

    private void pickIt(Pickup pickup) {

        if(airplane.soundEnabled) {
            pickup.getPickUpSound().play();
        }
        switch(pickup.getPickUpType()){
            case Pickup.STAR:
                starCount += pickup.getPickUpValue();
                break;
            case Pickup.SHIELD:
                shieldCount = pickup.getPickUpValue();
                break;
            case Pickup.FUEL:
                fuelCount = pickup.getPickUpValue();
                break;
        }
        pickupsInScene.removeValue(pickup, false);
    }

    public float getShieldCount() {
        return shieldCount;
    }

    public int getStarCount() {
        return starCount;
    }
}
