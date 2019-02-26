package com.airplane.game.GameObjects;

import com.airplane.game.Managers.GameManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.concurrent.ThreadLocalRandom;

import static com.airplane.game.GameObjects.Plane.deltaTime;
import static com.airplane.game.GameObjects.Plane.planeRect;

public class Meteor {

    private static Array<TextureAtlas.AtlasRegion> meteorTextures = new Array<TextureAtlas.AtlasRegion>(); //массив для хранения текстур метеоров
    private static TextureRegion selectedMeteorTexture; // объект метеора, с которым будут проводиться взаимодействия
    private static boolean meteorInScene; // для определения отображается ли в данный момент метеор?
    private static final int METEOR_SPEED = 100; // скорость метеора
    public static Vector2 meteorPosition= new Vector2(); // вектор позиции метеора
    public static Vector2 meteorVelocity= new Vector2(); // вектор скорости метеора
    public static float nextMeteorIn; // переменная, по которой определяем время поялвления следующего метеора
    private static Rectangle meteorRect; // для коллизий
    public static Vector2 destination = new Vector2();

    public static void initializeMeteor(){

        /* добавляем в массив компоненты (все метеоры) нашего атласа текстур*/
        meteorTextures.add(GameManager.atlas.findRegion("meteorBrown_med1"));
        meteorTextures.add(GameManager.atlas.findRegion("meteorBrown_med2"));
        meteorTextures.add(GameManager.atlas.findRegion("meteorBrown_small1"));
        meteorTextures.add(GameManager.atlas.findRegion("meteorBrown_small2"));
        meteorTextures.add(GameManager.atlas.findRegion("meteorBrown_tiny1"));
        meteorTextures.add(GameManager.atlas.findRegion("meteorBrown_tiny2"));

        nextMeteorIn = (float) (Math.random()*5); // высчитываем время до появления следующего метеора
        meteorRect = new Rectangle(); // инициализируем прямоугольник
    }

    public static void renderMeteor(SpriteBatch batch){

        /*отрисовываем метеор если он находится/отображается на экране*/
        if(meteorInScene)
        {
            batch.draw(selectedMeteorTexture,meteorPosition.x, meteorPosition.y);
        }
    }

    public static void updateMeteor(){
        /*если метеор находится на экране*/
        if(meteorInScene)
        {

            meteorPosition.mulAdd(meteorVelocity, (float) (deltaTime*1.5)); // меняем позицию метеора на экране по этому вектору
            //meteorPosition.x -= Plane.planePosition.x - Plane.planeDefaultPosition.x;

            /*Устанавливаем область столкновения метеора в зависимости от его нынешней позиции на экране*/
            meteorRect.set(meteorPosition.x, meteorPosition.y,selectedMeteorTexture.getRegionWidth()-4, selectedMeteorTexture.getRegionHeight()-4);

            /*переходим в GAME_OVER при наложении области столкновения самолета и области столкновения метеора*/
            if (planeRect.overlaps(meteorRect))
            {
                if (GameManager.gameState != GameManager.GameState.GAME_OVER){
                    GameManager.gameState = GameManager.GameState.GAME_OVER;
                }
            }

            /*Если метеор вылетел за левый край экрана - он больше не на экране*/
            if(meteorPosition.x<-10)
            {
                meteorInScene=false;
            }
        }

        /*Вызываем метод launchMeteor только если nextMeteorIn становится меньше 0.
        * Но если метеор будет находится еще на экране то новый метеор не запускаем, а заново генерируем nextMeteorIn*/
        nextMeteorIn -= deltaTime;
        if(nextMeteorIn <= 0)
        {
            launchMeteor();
        }

    }

    private static void launchMeteor(){

        /* Math.random выдаёт от 0 до 0.999... */

        nextMeteorIn=1.5f+(float)Math.random()*5; // счетчик запуска следующего метеора
        if(meteorInScene) //создаем новый метеор, только если в данный момент на экране нет метеора
        {
            return;
        }
        meteorInScene = true; // метеора отображается на экране
        int id = (int)(Math.random()*meteorTextures.size); // определяем, какой метеор взять из массива
        selectedMeteorTexture = meteorTextures.get(id); // устанавливаем выбранную текстуру, относительно выбранного метеора

        //meteorPosition.x = Gdx.graphics.getWidth() + 20; // начальная позиция по x - ширина экрана + 20 пикселей

        //meteorPosition.x = (float) (Math.random()*Gdx.graphics.getWidth()); // начальная позиция по x - ширина экрана + 20 пикселей
        meteorPosition.x = ThreadLocalRandom.current().nextInt(Gdx.graphics.getWidth(), Gdx.graphics.getWidth()+20); // начальная позиция по x - ширина экрана + 20 пикселей
        meteorPosition.y =(float) (Math.random()*Gdx.graphics.getHeight()); // начальная позиция по y

        System.out.println("meteorPosition.x = " + meteorPosition.x);
        System.out.println("meteorPosition.y = " + meteorPosition.y);

        //destination.x = (float) (Math.random()*Gdx.graphics.getWidth()); // вектор направления куда будет стремиться наш метеор
        destination.x = ThreadLocalRandom.current().nextInt(-20, 0); // вектор направления куда будет стремиться наш метеор
        destination.y = (float) (Math.random()*Gdx.graphics.getHeight()); // вектор направления куда будет стремиться наш метеор

        System.out.println("destination.x = " + destination.x);
        System.out.println("destination.y = " + destination.y);

        destination.sub(meteorPosition).nor(); // nor - нормализация чтобы вектор направления имел длину 1

        /*System.out.println("destination.x after sub.nor = " + destination.x);
        System.out.println("destination.y after sub.nor = " + destination.y);*/

        meteorVelocity.mulAdd(destination, METEOR_SPEED);


    }

}
