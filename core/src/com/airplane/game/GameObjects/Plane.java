package com.airplane.game.GameObjects;

import com.airplane.game.Airplane;
import com.airplane.game.AirplaneScene1;
import com.airplane.game.AirplaneScene2;
import com.airplane.game.AirplaneScene3;
import com.airplane.game.Managers.GameManager;
import com.airplane.game.Managers.GameManager2;
import com.airplane.game.Managers.GameManager3;
import com.airplane.game.Managers.InputManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Plane{

    /* объявление объектов классов*/
    private Animation plane;
    private TextureRegion planeTexture;
    private Vector2 planeDefaultPosition;
    private Vector2 planePosition;
    private Vector2 planeVelocity;
    private final Vector2 scrollVelocity = new Vector2(4, 0);
    public Vector2 tempVector = new Vector2(); // временный вектор для хранения координат касания
    private final int TOUCH_IMPULSE = 500;
    public float tapDrawTime;
    private final float TAP_DRAW_TIME_MAX = 1.0f;
    public float planeAnimTime;
    private final Vector2 damping = new Vector2(0.99f,0.99f);
    private final Vector2 gravity = new Vector2(0, -2f);
    public float deltaTime;
    private TextureRegion tapIndicator;
    public static Rectangle planeRect = new Rectangle();
    private float PLANE_RESIZE_WIDTH_FACTOR;
    private float PLANE_RESIZE_HEIGHT_FACTOR;
    private float TAP_INDICATOR_RESIZE_WIDTH_FACTOR;
    private float TAP_INDICATOR_RESIZE_HEIGHT_FACTOR;
    private Texture testOverlapsPlane; // для проверки коллизий
    private TextureAtlas atlas;
    private Sound tapSound;
    private AssetManager manager;
    private ParticleEffect smoke;
    private ParticleEffect explosion;
    private GameManager gameManager;
    private GameManager2 gameManager2;
    private GameManager3 gameManager3;
    private Airplane airplane;

    public Plane(Airplane airplane) {

        System.out.println("game in plane = " + airplane);
        this.airplane = airplane;
        atlas = airplane.atlas;
        manager = airplane.manager;
    }

    /*метод initialize служит для инициализации ранее созданных объектов перед их применением (отрисовка и т.д.)*/
    public void initialize(float width, float height){

        //System.out.println("PLANE INITIALIZE");
        planeTexture = atlas.findRegion("planeGreen1");
        plane = new Animation(0.05f, atlas.findRegion("planeGreen1"), atlas.findRegion("planeGreen2"), atlas.findRegion("planeGreen3")); // инициализация анимации объекта plane
        plane.setPlayMode(Animation.PlayMode.LOOP); // "запуск" анимации
        planeDefaultPosition = new Vector2(); // инициализация начальной позиции самолета
        planeDefaultPosition.set(width/10, height/1.8f); // установка начальной позиции самолета
        planeVelocity = new Vector2(); // инициализация вектора скорости самолета
        planeVelocity.set(100, 0); // установка скорости самолета
        planePosition = new Vector2(); // инициализация вектора позиции самолета
        planePosition.set(planeDefaultPosition); // установка позиции самолета начальной позицией
        testOverlapsPlane = new Texture(Gdx.files.internal("testoverlaps.png")); // Инициализация текстуры для тестовой отработки коллизий
        tapIndicator = atlas.findRegion("tap2");
        tapSound = manager.get("pop.ogg");
        smoke = manager.get("Smoke", ParticleEffect.class);
        explosion = manager.get("Explosion", ParticleEffect.class);
        setPlaneResizeWidthFactor();
        setPlaneResizeHeightFactor();
    }

    // Устанавливаем значения ресайз факторов для разных экранов устройств
    public void setPlaneResizeWidthFactor(){

        if (Gdx.graphics.getWidth() <= 800){
            PLANE_RESIZE_WIDTH_FACTOR = 0.6f;
            TAP_INDICATOR_RESIZE_WIDTH_FACTOR = 0.6f;
        }
        else if (Gdx.graphics.getWidth() > 1280){
            PLANE_RESIZE_WIDTH_FACTOR = 1.5f;
            TAP_INDICATOR_RESIZE_WIDTH_FACTOR = 1.5f;
        }
        else{
            PLANE_RESIZE_WIDTH_FACTOR = 0.8f;
            TAP_INDICATOR_RESIZE_WIDTH_FACTOR = 0.9f;
        }
    }

    public void setPlaneResizeHeightFactor(){
        if (Gdx.graphics.getHeight() <= 480){
            PLANE_RESIZE_HEIGHT_FACTOR = 0.6f;
            TAP_INDICATOR_RESIZE_HEIGHT_FACTOR = 0.6f;
        }
        else if (Gdx.graphics.getHeight() > 768){
            PLANE_RESIZE_HEIGHT_FACTOR = 1.5f;
            TAP_INDICATOR_RESIZE_HEIGHT_FACTOR = 1.5f;
        }
        else{
            PLANE_RESIZE_HEIGHT_FACTOR = 0.8f;
            TAP_INDICATOR_RESIZE_HEIGHT_FACTOR = 0.9f;
        }
    }

    /*метод render служит для отрисовки объектов и анимаций*/
    public void renderPlane(SpriteBatch batch){

        if(AirplaneScene1.isIsAirplaneScene1Initialized()) {
            if (gameManager.getGameState() == GameManager.GameState.INIT) {
                batch.draw(tapIndicator, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, tapIndicator.getRegionWidth() * TAP_INDICATOR_RESIZE_WIDTH_FACTOR, tapIndicator.getRegionHeight() * TAP_INDICATOR_RESIZE_HEIGHT_FACTOR);
                batch.draw((TextureRegion) plane.getKeyFrame(planeAnimTime), planePosition.x, planePosition.y, planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR, planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR); // отрисовка самолета с параметрами (текстура с отрисвокой кадра в зависимости от planeAnimTime, координата по x, координата по y
            }
            if (gameManager.getGameState() == GameManager.GameState.ACTION) {
                smoke.draw(batch);
                batch.draw((TextureRegion) plane.getKeyFrame(planeAnimTime), planePosition.x, planePosition.y, planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR, planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR); // отрисовка самолета с параметрами (текстура с отрисвокой кадра в зависимости от planeAnimTime, координата по x, координата по y
            }
            if (gameManager.getGameState() == GameManager.GameState.GAME_OVER){
                explosion.draw(batch);
            }
        }

        if(AirplaneScene2.isIsAirplaneScene2Initialized()) {
            if (gameManager2.getGameState() == GameManager2.GameState.INIT) {
                batch.draw(tapIndicator, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, tapIndicator.getRegionWidth() * TAP_INDICATOR_RESIZE_WIDTH_FACTOR, tapIndicator.getRegionHeight() * TAP_INDICATOR_RESIZE_HEIGHT_FACTOR);
                batch.draw((TextureRegion) plane.getKeyFrame(planeAnimTime), planePosition.x, planePosition.y, planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR, planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR); // отрисовка самолета с параметрами (текстура с отрисвокой кадра в зависимости от planeAnimTime, координата по x, координата по y
            }
            if (gameManager2.getGameState() == GameManager2.GameState.ACTION) {
                smoke.draw(batch);
                batch.draw((TextureRegion) plane.getKeyFrame(planeAnimTime), planePosition.x, planePosition.y, planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR, planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR); // отрисовка самолета с параметрами (текстура с отрисвокой кадра в зависимости от planeAnimTime, координата по x, координата по y
                //batch.draw(testOverlapsPlane, planePosition.x + planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 4, planePosition.y + planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 4, planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 2, planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 2);
            }
            if (gameManager2.getGameState() == GameManager2.GameState.GAME_OVER){
                explosion.draw(batch);
            }
        }

        if(AirplaneScene3.isIsAirplaneScene3Initialized()) {
            if (gameManager3.getGameState() == GameManager3.GameState.INIT) {
                batch.draw(tapIndicator, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, tapIndicator.getRegionWidth() * TAP_INDICATOR_RESIZE_WIDTH_FACTOR, tapIndicator.getRegionHeight() * TAP_INDICATOR_RESIZE_HEIGHT_FACTOR);
                batch.draw((TextureRegion) plane.getKeyFrame(planeAnimTime), planePosition.x, planePosition.y, planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR, planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR); // отрисовка самолета с параметрами (текстура с отрисвокой кадра в зависимости от planeAnimTime, координата по x, координата по y
            }
            if (gameManager3.getGameState() == GameManager3.GameState.ACTION) {
                smoke.draw(batch);
                batch.draw((TextureRegion) plane.getKeyFrame(planeAnimTime), planePosition.x, planePosition.y, planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR, planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR); // отрисовка самолета с параметрами (текстура с отрисвокой кадра в зависимости от planeAnimTime, координата по x, координата по y
                //batch.draw(testOverlapsPlane, planePosition.x + planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 4, planePosition.y + planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 4, planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 2, planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 2);
            }
            if (gameManager3.getGameState() == GameManager3.GameState.GAME_OVER){
                explosion.draw(batch);
            }
        }

        //batch.draw(testOverlapsPlane, planePosition.x + planeTexture.getRegionWidth()*PLANE_RESIZE_WIDTH_FACTOR/4, planePosition.y + planeTexture.getRegionHeight()*PLANE_RESIZE_HEIGHT_FACTOR/4, planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR/2, planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR/2);
        if(tapDrawTime > 0)
            if (Gdx.graphics.getWidth() <= 800){
                //batch.draw(tapIndicator, InputManager.touchPosition.x - 20f, InputManager.touchPosition.y - 20, Gdx.graphics.getWidth() / TAP_INDICATOR_RESIZE_WIDTH_FACTOR, Gdx.graphics.getHeight() / TAP_INDICATOR_RESIZE_HEIGHT_FACTOR);
                batch.draw(tapIndicator, InputManager.touchPosition.x - 20f, InputManager.touchPosition.y - 20, tapIndicator.getRegionWidth() * TAP_INDICATOR_RESIZE_WIDTH_FACTOR, tapIndicator.getRegionHeight() * TAP_INDICATOR_RESIZE_HEIGHT_FACTOR);
            }
            else if (Gdx.graphics.getWidth() > 1280){
                batch.draw(tapIndicator, InputManager.touchPosition.x - 55f, InputManager.touchPosition.y - 55f, tapIndicator.getRegionWidth() * TAP_INDICATOR_RESIZE_WIDTH_FACTOR, tapIndicator.getRegionHeight() * TAP_INDICATOR_RESIZE_HEIGHT_FACTOR);
            }
            else{
                //batch.draw(tapIndicator, InputManager.touchPosition.x - 29.5f, InputManager.touchPosition.y - 29.5f, Gdx.graphics.getWidth() / TAP_INDICATOR_RESIZE_WIDTH_FACTOR, Gdx.graphics.getHeight() / TAP_INDICATOR_RESIZE_HEIGHT_FACTOR);
                batch.draw(tapIndicator, InputManager.touchPosition.x - 29.5f, InputManager.touchPosition.y - 29.5f, tapIndicator.getRegionWidth() * TAP_INDICATOR_RESIZE_WIDTH_FACTOR, tapIndicator.getRegionHeight() * TAP_INDICATOR_RESIZE_HEIGHT_FACTOR);
            }
    }

    /*метод update служит для обновления позиций объектов и времени*/
    public void update(){
        //System.out.println("gravity = " + gravity);
        //System.out.println("planePosition.y = " + planePosition.y);
        //System.out.println("planeVelocity = " + planeVelocity);
        if (AirplaneScene1.isIsAirplaneScene1Initialized()) {

            if (gameManager.getGameState() == GameManager.GameState.ACTION) {
                deltaTime = Gdx.graphics.getDeltaTime();

                planeAnimTime += deltaTime;
                planeVelocity.scl(damping);
                planeVelocity.add(gravity);
                planeVelocity.add(scrollVelocity);
                planePosition.mulAdd(planeVelocity, deltaTime);
                smoke.setPosition(planePosition.x + (20 * PLANE_RESIZE_WIDTH_FACTOR), planePosition.y + (30 + PLANE_RESIZE_HEIGHT_FACTOR));
                smoke.update(deltaTime);

                tapDrawTime -= deltaTime;

                if (Gdx.graphics.getWidth() <= 800) {
                    planeRect.set(planePosition.x + planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 4, planePosition.y + planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 4, planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 2, planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 2);
                } else if (Gdx.graphics.getWidth() > 1280) {
                    planeRect.set(planePosition.x + planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 4, planePosition.y + planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 4, planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 2, planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 2);
                } else {
                    planeRect.set(planePosition.x + planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 4, planePosition.y + planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 4, planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 2, planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 2);
                }
            }

        }

        if (AirplaneScene2.isIsAirplaneScene2Initialized()){
            if (gameManager2.getGameState() == GameManager2.GameState.ACTION){
                deltaTime = Gdx.graphics.getDeltaTime();
                planeAnimTime += deltaTime;
                planeVelocity.scl(damping);
                planeVelocity.add(gravity);
                planeVelocity.add(scrollVelocity);
                planePosition.mulAdd(planeVelocity, deltaTime);
                smoke.setPosition(planePosition.x + (20 * PLANE_RESIZE_WIDTH_FACTOR), planePosition.y + (30 + PLANE_RESIZE_HEIGHT_FACTOR));
                smoke.update(deltaTime);

                tapDrawTime -= deltaTime;

                if (Gdx.graphics.getWidth() <= 800) {
                    planeRect.set(planePosition.x + planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 4, planePosition.y + planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 4, planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 2, planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 2);
                } else if (Gdx.graphics.getWidth() > 1280) {
                    planeRect.set(planePosition.x + planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 4, planePosition.y + planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 4, planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 2, planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 2);
                } else {
                    planeRect.set(planePosition.x + planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 4, planePosition.y + planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 4, planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 2, planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 2);
                }
            }
        }

        if (AirplaneScene3.isIsAirplaneScene3Initialized()){
            if (gameManager3.getGameState() == GameManager3.GameState.ACTION){
                deltaTime = Gdx.graphics.getDeltaTime();
                planeAnimTime += deltaTime;
                planeVelocity.scl(damping);
                planeVelocity.add(gravity);
                planeVelocity.add(scrollVelocity);
                planePosition.mulAdd(planeVelocity, deltaTime);
                smoke.setPosition(planePosition.x + (20 * PLANE_RESIZE_WIDTH_FACTOR), planePosition.y + (30 + PLANE_RESIZE_HEIGHT_FACTOR));
                smoke.update(deltaTime);

                tapDrawTime -= deltaTime;

                if (Gdx.graphics.getWidth() <= 800) {
                    planeRect.set(planePosition.x + planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 4, planePosition.y + planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 4, planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 2, planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 2);
                } else if (Gdx.graphics.getWidth() > 1280) {
                    planeRect.set(planePosition.x + planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 4, planePosition.y + planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 4, planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 2, planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 2);
                } else {
                    planeRect.set(planePosition.x + planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 4, planePosition.y + planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 4, planeTexture.getRegionWidth() * PLANE_RESIZE_WIDTH_FACTOR / 2, planeTexture.getRegionHeight() * PLANE_RESIZE_HEIGHT_FACTOR / 2);
                }
            }
        }

        /*if (GameManager.gameState != GameManager.GameState.GAME_OVER){
            explosion.reset();
            explosion.setPosition(planePosition.x + (30*PLANE_RESIZE_WIDTH_FACTOR), planePosition.y + (40+PLANE_RESIZE_HEIGHT_FACTOR));
        }
        if (GameManager2.gameState != GameManager2.GameState.GAME_OVER){
            explosion.reset();
            explosion.setPosition(planePosition.x + (30*PLANE_RESIZE_WIDTH_FACTOR), planePosition.y + (40+PLANE_RESIZE_HEIGHT_FACTOR));
        }*/

        if(AirplaneScene1.isIsAirplaneScene1Initialized()) {
            if (gameManager.getGameState() != GameManager.GameState.GAME_OVER) {
                explosion.reset();
                explosion.setPosition(planePosition.x + (30 * PLANE_RESIZE_WIDTH_FACTOR), planePosition.y + (40 + PLANE_RESIZE_HEIGHT_FACTOR));
            }
        }

        if(AirplaneScene2.isIsAirplaneScene2Initialized()) {
            if (gameManager2.getGameState() != GameManager2.GameState.GAME_OVER) {
                explosion.reset();
                explosion.setPosition(planePosition.x + (30 * PLANE_RESIZE_WIDTH_FACTOR), planePosition.y + (40 + PLANE_RESIZE_HEIGHT_FACTOR));
            }
        }

        if(AirplaneScene3.isIsAirplaneScene3Initialized()) {
            if (gameManager3.getGameState() != GameManager3.GameState.GAME_OVER) {
                explosion.reset();
                explosion.setPosition(planePosition.x + (30 * PLANE_RESIZE_WIDTH_FACTOR), planePosition.y + (40 + PLANE_RESIZE_HEIGHT_FACTOR));
            }
        }

        /*if (GameManager.gameState == GameManager.GameState.GAME_OVER){
            explosion.update(deltaTime);
        }
        if (GameManager2.gameState == GameManager2.GameState.GAME_OVER){
            explosion.update(deltaTime);
        }*/

        if(AirplaneScene1.isIsAirplaneScene1Initialized()) {
            if (gameManager.getGameState() == GameManager.GameState.GAME_OVER) {
                explosion.update(deltaTime);
            }
        }

        if(AirplaneScene2.isIsAirplaneScene2Initialized()) {
            if (gameManager2.getGameState() == GameManager2.GameState.GAME_OVER) {
                explosion.update(deltaTime);
            }
        }

        if(AirplaneScene3.isIsAirplaneScene3Initialized()) {
            if (gameManager3.getGameState() == GameManager3.GameState.GAME_OVER) {
                explosion.update(deltaTime);
            }
        }
    }

    public void handleTouch(float x, float y) {

        if(AirplaneScene1.isIsAirplaneScene1Initialized()) {
            if (gameManager.getGameState() == GameManager.GameState.ACTION) {

                if (airplane.soundEnabled) {
                    tapSound.play();
                }
                tempVector.set(planePosition.x, planePosition.y);
                //System.out.println("tempVector before subtract = " + tempVector);
                //System.out.println("touchPosition.x = " + x);
                //System.out.println("touchPosition.y = " + y);
                tempVector.sub(x, y).nor();
                //System.out.println("tempVector after subtract = " + tempVector);
                if (Gdx.graphics.getWidth() <= 800) {
                    planeVelocity.mulAdd(tempVector, (float) (TOUCH_IMPULSE * 0.7 - MathUtils.clamp(Vector2.dst(x, y, planePosition.x, planePosition.y), 0, TOUCH_IMPULSE)));
                } else if (Gdx.graphics.getWidth() > 1280) {
                    planeVelocity.mulAdd(tempVector, (float) (TOUCH_IMPULSE * 2.5 - MathUtils.clamp(Vector2.dst(x, y, planePosition.x, planePosition.y), 0, TOUCH_IMPULSE)));
                } else {
                    //System.out.println("planeVelocity = " + planeVelocity);
                    planeVelocity.mulAdd(tempVector, (float) (TOUCH_IMPULSE * 1.2 - MathUtils.clamp(Vector2.dst(x, y, planePosition.x, planePosition.y), 0, TOUCH_IMPULSE)));
                }

                tapDrawTime = TAP_DRAW_TIME_MAX;
            }
        }

        if(AirplaneScene2.isIsAirplaneScene2Initialized()) {
            if (gameManager2.getGameState() == GameManager2.GameState.ACTION) {
                if (airplane.soundEnabled) {
                    tapSound.play();
                }
                tempVector.set(planePosition.x, planePosition.y);
                //System.out.println("tempVector before subtract = " + tempVector);
                //System.out.println("touchPosition.x = " + x);
                //System.out.println("touchPosition.y = " + y);
                tempVector.sub(x, y).nor();
                //System.out.println("tempVector after subtract = " + tempVector);
                if (Gdx.graphics.getWidth() <= 800) {
                    planeVelocity.mulAdd(tempVector, (float) (TOUCH_IMPULSE * 0.7 - MathUtils.clamp(Vector2.dst(x, y, planePosition.x, planePosition.y), 0, TOUCH_IMPULSE)));
                } else if (Gdx.graphics.getWidth() > 1280) {
                    planeVelocity.mulAdd(tempVector, (float) (TOUCH_IMPULSE * 2.5 - MathUtils.clamp(Vector2.dst(x, y, planePosition.x, planePosition.y), 0, TOUCH_IMPULSE)));
                } else {
                    //System.out.println("planeVelocity = " + planeVelocity);
                    planeVelocity.mulAdd(tempVector, (float) (TOUCH_IMPULSE * 1.2 - MathUtils.clamp(Vector2.dst(x, y, planePosition.x, planePosition.y), 0, TOUCH_IMPULSE)));
                }

                tapDrawTime = TAP_DRAW_TIME_MAX;
            }
        }

        if(AirplaneScene3.isIsAirplaneScene3Initialized()) {
            if (gameManager3.getGameState() == GameManager3.GameState.ACTION) {
                if (airplane.soundEnabled) {
                    tapSound.play();
                }
                tempVector.set(planePosition.x, planePosition.y);
                //System.out.println("tempVector before subtract = " + tempVector);
                //System.out.println("touchPosition.x = " + x);
                //System.out.println("touchPosition.y = " + y);
                tempVector.sub(x, y).nor();
                //System.out.println("tempVector after subtract = " + tempVector);
                if (Gdx.graphics.getWidth() <= 800) {
                    planeVelocity.mulAdd(tempVector, (float) (TOUCH_IMPULSE * 0.7 - MathUtils.clamp(Vector2.dst(x, y, planePosition.x, planePosition.y), 0, TOUCH_IMPULSE)));
                } else if (Gdx.graphics.getWidth() > 1280) {
                    planeVelocity.mulAdd(tempVector, (float) (TOUCH_IMPULSE * 2.5 - MathUtils.clamp(Vector2.dst(x, y, planePosition.x, planePosition.y), 0, TOUCH_IMPULSE)));
                } else {
                    //System.out.println("planeVelocity = " + planeVelocity);
                    planeVelocity.mulAdd(tempVector, (float) (TOUCH_IMPULSE * 1.2 - MathUtils.clamp(Vector2.dst(x, y, planePosition.x, planePosition.y), 0, TOUCH_IMPULSE)));
                }

                tapDrawTime = TAP_DRAW_TIME_MAX;
            }
        }
    }

    public void resetPlane(){

        tempVector.set(0, 0);
        scrollVelocity.set(4, 0);
        planeVelocity.set(100, 0);
        planePosition.set(planeDefaultPosition); // установка позиции самолета начальной позицией
        tapDrawTime = 0;
        deltaTime = 0;
    }

    public Vector2 getPlanePosition() {
        return planePosition;
    }

    public Vector2 getPlaneDefaultPosition() {
        return planeDefaultPosition;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setGameManager2(GameManager2 gameManager2) {
        this.gameManager2 = gameManager2;
    }

    public void setGameManager3(GameManager3 gameManager3) {
        this.gameManager3 = gameManager3;
    }

}
