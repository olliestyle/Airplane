package com.airplane.game.GameObjects;

import com.airplane.game.Managers.GameManager;
import com.airplane.game.Managers.InputManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static com.airplane.game.Managers.GameManager.gameState;
import static com.airplane.game.Managers.GameManager.terrainAbove;
import static com.airplane.game.Managers.GameManager.terrainBelow;

public class Plane {

    /* объявление объектов классов*/
    private static Animation plane;
    public static Vector2 planeDefaultPosition;
    public static Vector2 planePosition;
    private static Vector2 planeVelocity;
    private static Vector2 scrollVelocity;
    public static Vector2 tempVector = new Vector2(); // временный вектор для хранения координат касания
    private static final int TOUCH_IMPULSE = 500;
    public static float tapDrawTime;
    private static final float TAP_DRAW_TIME_MAX = 1.0f;
    public static float planeAnimTime;
    private static final Vector2 damping = new Vector2(0.99f,0.99f);
    private static Vector2 gravity = new Vector2();
    public static float deltaTime;
    private static TextureRegion tapIndicator;
    public static Rectangle planeRect = new Rectangle();
    private static float PLANE_RESIZE_WIDTH_FACTOR;
    private static float PLANE_RESIZE_HEIGHT_FACTOR;
    private static float TAP_INDICATOR_RESIZE_WIDTH_FACTOR;
    private static float TAP_INDICATOR_RESIZE_HEIGHT_FACTOR;
    private static Texture testOverlapsPlane;


    /*метод initialize служит для инициализации ранее созданных объектов перед их применением (отрисовка и т.д.)*/
    public static void initialize(float width, float height){

        plane = new Animation(0.05f, GameManager.atlas.findRegion("planeGreen1"), GameManager.atlas.findRegion("planeGreen2"), GameManager.atlas.findRegion("planeGreen3")); // инициализация анимации объекта plane
        plane.setPlayMode(Animation.PlayMode.LOOP); // "запуск" анимации
        planeDefaultPosition = new Vector2(); // инициализация начальной позиции самолета
        planeDefaultPosition.set(width/10, height/1.5f); // установка начальной позиции самолета
        gravity.set(0, -2f); // установка "гравитации"
        planeVelocity = new Vector2(); // инициализация вектора скорости самолета
        planeVelocity.set(100,0); // установка скорости самолета
        scrollVelocity = new Vector2();
        scrollVelocity.set(4,0);
        planePosition = new Vector2(); // инициализация вектора позиции самолета
        planePosition.set(planeDefaultPosition); // установка позиции самолета начальной позицией
        testOverlapsPlane = new Texture(Gdx.files.internal("testoverlaps.png")); // Инициализация текстуры для тестовой отработки коллизий
        tapIndicator = GameManager.atlas.findRegion("tap2");
        setPlaneResizeWidthFactor();
        setPlaneResizeHeightFactor();

    }


    // Устанавливаем значения ресайз факторов для разных экранов устройств
    public static void setPlaneResizeWidthFactor(){

        if (Gdx.graphics.getWidth() <= 800){
            PLANE_RESIZE_WIDTH_FACTOR = 15f;
            TAP_INDICATOR_RESIZE_WIDTH_FACTOR = 22f;
        }
        else if (Gdx.graphics.getWidth() > 1280){
            PLANE_RESIZE_WIDTH_FACTOR = 17f;
            TAP_INDICATOR_RESIZE_WIDTH_FACTOR = 20f;
        }
        else{
            PLANE_RESIZE_WIDTH_FACTOR = 15.6f;
            TAP_INDICATOR_RESIZE_WIDTH_FACTOR = 18.6f;
        }
    }

    public static void setPlaneResizeHeightFactor(){
        if (Gdx.graphics.getHeight() <= 480){
            PLANE_RESIZE_HEIGHT_FACTOR = 10f;
            TAP_INDICATOR_RESIZE_HEIGHT_FACTOR = 11f;
        }
        else if (Gdx.graphics.getHeight() > 768){
            PLANE_RESIZE_HEIGHT_FACTOR = 12f;
            TAP_INDICATOR_RESIZE_HEIGHT_FACTOR = 12f;
        }
        else{
            PLANE_RESIZE_HEIGHT_FACTOR = 13f;
            TAP_INDICATOR_RESIZE_HEIGHT_FACTOR = 13f;
        }
    }

    /*метод render служит для отрисовки объектов и анимаций*/
    public static void renderPlane(SpriteBatch batch){

        if (gameState == GameManager.GameState.INIT)
            batch.draw(tapIndicator, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/TAP_INDICATOR_RESIZE_WIDTH_FACTOR, Gdx.graphics.getHeight()/TAP_INDICATOR_RESIZE_HEIGHT_FACTOR);


        //batch.draw((TextureRegion) plane.getKeyFrame(planeAnimTime), planePosition.x, planePosition.y); // отрисовка самолета с параметрами (текстура с отрисвокой кадра в зависимости от planeAnimTime, координата по x, координата по y
        batch.draw((TextureRegion) plane.getKeyFrame(planeAnimTime), planePosition.x, planePosition.y, Gdx.graphics.getWidth()/PLANE_RESIZE_WIDTH_FACTOR, Gdx.graphics.getHeight()/PLANE_RESIZE_HEIGHT_FACTOR); // отрисовка самолета с параметрами (текстура с отрисвокой кадра в зависимости от planeAnimTime, координата по x, координата по y
        batch.draw(testOverlapsPlane, planePosition.x+10, planePosition.y+10, (Gdx.graphics.getWidth()/PLANE_RESIZE_WIDTH_FACTOR)-17, (Gdx.graphics.getHeight()/PLANE_RESIZE_HEIGHT_FACTOR)-17); //хорошо подходит для всех экранов

        if(tapDrawTime > 0)

            if (Gdx.graphics.getWidth() <= 800){
                batch.draw(tapIndicator, InputManager.touchPosition.x - 20f, InputManager.touchPosition.y - 20, Gdx.graphics.getWidth() / TAP_INDICATOR_RESIZE_WIDTH_FACTOR, Gdx.graphics.getHeight() / TAP_INDICATOR_RESIZE_HEIGHT_FACTOR); //29.5 половина ширины/высоты нашего изображения
            }
            else if (Gdx.graphics.getWidth() > 1280){
                batch.draw(tapIndicator, InputManager.touchPosition.x - 55f, InputManager.touchPosition.y - 55f, Gdx.graphics.getWidth() / TAP_INDICATOR_RESIZE_WIDTH_FACTOR, Gdx.graphics.getHeight() / TAP_INDICATOR_RESIZE_HEIGHT_FACTOR); //29.5 половина ширины/высоты нашего изображения
            }
            else{
                batch.draw(tapIndicator, InputManager.touchPosition.x - 29.5f, InputManager.touchPosition.y - 29.5f, Gdx.graphics.getWidth() / TAP_INDICATOR_RESIZE_WIDTH_FACTOR, Gdx.graphics.getHeight() / TAP_INDICATOR_RESIZE_HEIGHT_FACTOR); //29.5 половина ширины/высоты нашего изображения
            }

    }

    /*метод update служит для обновления позиций объектов и времени*/
    public static void update(){


        switch (gameState){
            case INIT:

                break;

            case ACTION:

                deltaTime = Gdx.graphics.getDeltaTime();
                planeAnimTime += deltaTime;
                planeVelocity.scl(damping);
                planeVelocity.add(gravity);
                planeVelocity.add(scrollVelocity);
                planePosition.mulAdd(planeVelocity, deltaTime);
                tapDrawTime -= deltaTime;

                //planeRect.set(planePosition.x + 16, planePosition.y, 50 , 73);

                planeRect.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth() / TAP_INDICATOR_RESIZE_WIDTH_FACTOR, Gdx.graphics.getHeight() / TAP_INDICATOR_RESIZE_HEIGHT_FACTOR);

                if (planePosition.y < terrainBelow.getRegionHeight() - 25 || planePosition.y + GameManager.atlas.findRegion("planeGreen1").originalHeight > Gdx.graphics.getHeight() -  terrainAbove.getRegionHeight() + 25)
                    if (gameState != GameManager.GameState.GAME_OVER)
                        gameState = GameManager.GameState.GAME_OVER;

                break;

            case GAME_OVER:


                break;

            default:
                break;
        }

    }


    // метод handleTouch служит для обработки касаний относительно самолета
    public static void handleTouch(float x, float y){

        tempVector.set(planePosition.x, planePosition.y);
        System.out.println("tempVector before subtract = " + tempVector);
        System.out.println("touchPosition.x = " + x);
        System.out.println("touchPosition.y = " + y);
        tempVector.sub(x, y).nor();
        System.out.println("tempVector after subtract = " + tempVector);
        planeVelocity.mulAdd(tempVector, TOUCH_IMPULSE-MathUtils.clamp(Vector2.dst(x, y, planePosition.x, planePosition.y), 0, TOUCH_IMPULSE));
        tapDrawTime = TAP_DRAW_TIME_MAX;

    }

}
