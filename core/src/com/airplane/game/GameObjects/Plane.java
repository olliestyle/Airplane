package com.airplane.game.GameObjects;

import com.airplane.game.Managers.GameManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Plane {

    /* объявление объектов классов*/
    private static TextureAtlas atlas;
    private static Animation plane;
    public static Vector2 planeDefaultPosition;
    public static Vector2 planePosition;
    private static Vector2 planeVelocity;
    public static Vector2 tempVector = new Vector2(); // временный вектор для хранения координат касания
    private static final int TOUCH_IMPULSE = 500;
    static float tapDrawTime;
    private static final float TAP_DRAW_TIME_MAX = 1.0f;
    public static float planeAnimTime;
    private static final Vector2 damping = new Vector2(0.99f,0.99f);
    private static Vector2 gravity = new Vector2();
    public static float deltaTime;

    /*метод initialize служит для инициализации ранее созданных объектов перед их применением (отрисовка и т.д.)*/
    public static void initialize(float width, float height){

        atlas = new TextureAtlas(Gdx.files.internal("Airplane.pack")); // инициализируем атлас текстур
        plane = new Animation(0.05f, atlas.findRegion("planeGreen1"), atlas.findRegion("planeGreen2"), atlas.findRegion("planeGreen3")); // инициализация анимации объекта plane
        plane.setPlayMode(Animation.PlayMode.LOOP); // "запуск" анимации
        planeDefaultPosition = new Vector2(); // инициализация начальной позиции самолета
        planeDefaultPosition.set(width/10, height/1.5f); // установка начальной позиции самолета
        gravity.set(0, -2f); // установка "гравитации"
        planeVelocity = new Vector2(); // инициализация вектора скорости самолета
        planeVelocity.set(750,0); // установка скорости самолета
        planePosition = new Vector2(); // инициализация вектора позиции самолета
        planePosition.set(planeDefaultPosition); // установка позиции самолета начальной позицией

    }

    /*метод render служит для отрисовки объектов и анимаций*/
    public static void renderPlane(SpriteBatch batch){

        batch.draw((TextureRegion) plane.getKeyFrame(planeAnimTime), planePosition.x, planePosition.y); // отрисовка самолета с параметрами (текстура с отрисвокой кадра в зависимости от planeAnimTime, координата по x, координата по y

    }

    /*метод update служит для обновления позиций объектов*/
    public static void update(){

        deltaTime = Gdx.graphics.getDeltaTime();
        planeAnimTime += deltaTime;
        planeVelocity.scl(damping);
        planeVelocity.add(gravity);
        planePosition.mulAdd(planeVelocity, deltaTime);

    }

    // метод handleTouch служит для обработки касаний относительно самолета
    public static void handleTouch(float x, float y){

        tempVector.set(planePosition.x, planePosition.y);
        tempVector.sub(x, y).nor();
        planeVelocity.mulAdd(tempVector, TOUCH_IMPULSE-MathUtils.clamp(Vector2.dst(x, y, planePosition.x, planePosition.y), 0, TOUCH_IMPULSE));
        tapDrawTime=TAP_DRAW_TIME_MAX;
    }



}
