package com.airplane.game.GameObjects;

import com.airplane.game.Managers.GameManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.values.MeshSpawnShapeValue;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;



public class RockPillar {


    // Объявляем переменные класса
    private static Array<Vector2> pillars;
    private static Vector2 pillarPosition;
    private static Vector2 lastPillarPosition;
    private static TextureRegion pillarUp;
    private static TextureRegion pillarDown;
    public static Texture testOverlaps1;
    public static Texture testOverlaps2;
    public static Rectangle pillarRect1 = new Rectangle();
    public static Rectangle pillarRect2 = new Rectangle();


    // Инициализируем переменные класса тут
    public static void initializePillar(){

        pillars = new Array<Vector2>(); // Массив значений векторов, по которым будут обновляться скалы
        pillarUp = GameManager.atlas.findRegion("rockSnow"); // Инициализация текстуры
        pillarDown = GameManager.atlas.findRegion("rockSnowDown"); // Инициализация текстуры
        testOverlaps1 = new Texture(Gdx.files.internal("testoverlaps.png")); // Инициализация текстуры для тестовой отработки коллизий
        testOverlaps2 = new Texture(Gdx.files.internal("testoverlaps.png"));
        pillarPosition = new Vector2(); // вектор позиции скалы
        lastPillarPosition = new Vector2(); // последний вектор позиции скалы - нужен для отрисовки !1 элемента массива

        System.out.println("initializePillar here");

    }

    // метод добавления новой скалы на экран
    private static void addPillar(float width, float height){

        System.out.println("addPillar here");

        // если массив пуст, то добавить скалу относительно ширины экрана
        if(pillars.size == 0) {
            pillarPosition.x =(float) (width + Math.random()* (width/2));
        }
        // если массив не пуст, то добавить скалу относительно ширины экрана + позиция последней скалы
        else {
            pillarPosition.x =(float) (lastPillarPosition.x + (width + Math.random()* (width/2)));
        }
        // здесь определяем, снизу(1) или сверху(else) отрисовывать скалу
        if (MathUtils.randomBoolean()){
            pillarPosition.y = 1;
        }
        else{
            pillarPosition.y = -1;
        }
        lastPillarPosition = pillarPosition;
        pillars.add(pillarPosition);
    }

    public static void updatePillar(){


                for (Vector2 vec: pillars)
                {

                    vec.x -= Plane.planePosition.x - Plane.planeDefaultPosition.x; // метод для "движения" скалы относительно самолета
                    System.out.println("vec.x = " + vec.x);
                    System.out.println("pillars = " + pillars.size);

                    // если скала
                    if (vec.x + pillarUp.getRegionWidth() < 0 + pillarUp.getRegionWidth())
                    {
                        System.out.println("UDALIAU VECTOR");
                        pillars.removeValue(vec,false); // удаляем скалу, если она вылезла за пределы экрана слева
                    }

                    if (vec.y == 1){
                        pillarRect1.set(vec.x+20,0, Gdx.graphics.getWidth()/13, (float) (Gdx.graphics.getHeight()/4));
                        pillarRect2.set(vec.x + 50, Gdx.graphics.getHeight()/4, Gdx.graphics.getWidth()/30, (float) (Gdx.graphics.getHeight()/5.5));
                    }
                    else{
                        pillarRect1.set(vec.x + 10, Gdx.graphics.getHeight() - pillarDown.getRegionHeight()+10, pillarDown.getRegionWidth()-20, pillarDown.getRegionHeight());
                        pillarRect2.set(vec.x + 20, Gdx.graphics.getHeight()- Gdx.graphics.getHeight()/8, Gdx.graphics.getWidth()/13, (float) (Gdx.graphics.getHeight()/4));
                    }
                    if (isCollisionWithPillar()){
                        if (GameManager.gameState != GameManager.GameState.GAME_OVER){
                            GameManager.gameState = GameManager.GameState.GAME_OVER;
                        }
                    }
                    break;

                }
                if (lastPillarPosition.x < Gdx.graphics.getWidth()/1000){
                    System.out.println("DOBAVLIAU VECTOR");
                    addPillar(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // добавляем скалу если ближайшая скала достигла 1/3 экрана по x
                }

        }


    public static void renderPillar(SpriteBatch batch){

        System.out.println("renderPillar here");

                for (Vector2 vec: pillars){
                    if (vec.y == 1){
                        batch.draw(pillarUp, vec.x,0, Gdx.graphics.getWidth()/10, (float) (Gdx.graphics.getHeight()/2.3)); // Отрисовка скалы внизу экрана
                        //batch.draw(testOverlaps1, vec.x+20,0, Gdx.graphics.getWidth()/13, (float) (Gdx.graphics.getHeight()/4)); // Отрисовка черной области для проверки коллизий между объектами
                        //batch.draw(testOverlaps2, vec.x + 50, Gdx.graphics.getHeight()/4, Gdx.graphics.getWidth()/30, (float) (Gdx.graphics.getHeight()/5.5)); Отрисовка черной области для проверки коллизий между объектами
                    }
                    else{
                        batch.draw(pillarDown, vec.x, Gdx.graphics.getHeight() - pillarDown.getRegionHeight());// Отрисовка скалы сверху экрана
                        //batch.draw(testOverlaps1, vec.x + 50, Gdx.graphics.getHeight() - pillarDown.getRegionHeight()+10, Gdx.graphics.getWidth()/30, (float) (Gdx.graphics.getHeight()/6)); //Отрисовка черной области для проверки коллизий между объектами
                        //batch.draw(testOverlaps2, vec.x + 20, Gdx.graphics.getHeight()- Gdx.graphics.getHeight()/8, Gdx.graphics.getWidth()/13, (float) (Gdx.graphics.getHeight()/4)); //Отрисовка черной области для проверки коллизий между объектами
                    }
                }

    }

    private static boolean isCollisionWithPillar(){

        if(Plane.planeRect.overlaps(pillarRect1) || Plane.planeRect.overlaps(pillarRect2)){
            return true;
        }
        return false;
    }
}

