package com.airplane.game.GameObjects;

import com.airplane.game.Airplane;
import com.airplane.game.Managers.GameManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.values.MeshSpawnShapeValue;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class RockPillar {


    private Array<Vector2> pillars;
    private Vector2 pillarPosition;
    private Vector2 lastPillarPosition;
    private TextureRegion pillarUp;
    private TextureRegion pillarDown;
    public Texture testOverlapsPillar1;
    public Texture testOverlapsPillar2;
    private Rectangle pillarRect1 = new Rectangle();

    private Rectangle pillarRect2 = new Rectangle();
    Airplane game;
    TextureAtlas atlas;

    public RockPillar(Airplane airplane) {

        game = airplane;
        System.out.println("game in rockpillar = " + game);
        atlas = game.atlas;
    }


    public void initializePillar(){

        pillars = new Array<Vector2>();
        pillarUp = atlas.findRegion("rockSnow");
        pillarDown = atlas.findRegion("rockSnowDown");
        testOverlapsPillar1 = new Texture(Gdx.files.internal("testoverlaps.png")); // Инициализация текстуры для тестовой отработки коллизий
        testOverlapsPillar2 = new Texture(Gdx.files.internal("testoverlaps.png"));
        pillarPosition = new Vector2();
        lastPillarPosition = new Vector2(); // последний вектор позиции скалы - нужен для отрисовки !1 элемента массива

        System.out.println("initializePillar here");
        System.out.println("lastpillarposition = " + lastPillarPosition);

    }


    private void addPillar(float width, float height){

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

    public void updatePillar(){

                for (Vector2 vec: pillars)
                {

                    vec.x -= Plane.planePosition.x - Plane.planeDefaultPosition.x; // перемещение скал относительно "движения" самолета
                    System.out.println("vec.x = " + vec.x);
                    System.out.println("pillars = " + pillars.size);

                    if (vec.x + pillarUp.getRegionWidth() < 0 + pillarUp.getRegionWidth())
                    {
                        System.out.println("UDALIAU VECTOR");
                        pillars.removeValue(vec,false); // удаляем скалу, если она вылезла за пределы экрана слева
                    }

                    if (vec.y == 1){
                        if (Gdx.graphics.getWidth() <= 800){
                            pillarRect1.set(vec.x + Gdx.graphics.getWidth() / 38,0, Gdx.graphics.getWidth()/18, (float) (Gdx.graphics.getHeight()/4));
                            pillarRect2.set(vec.x + Gdx.graphics.getWidth() / 19, Gdx.graphics.getHeight()/4, Gdx.graphics.getWidth()/38, (float) (Gdx.graphics.getHeight()/5.5));
                        }
                        else if (Gdx.graphics.getWidth() > 1280){
                            pillarRect1.set(vec.x + Gdx.graphics.getWidth() / 38,0, Gdx.graphics.getWidth()/18, (float) (Gdx.graphics.getHeight()/4));
                            pillarRect2.set(vec.x + Gdx.graphics.getWidth() / 19, Gdx.graphics.getHeight()/4, Gdx.graphics.getWidth()/38, (float) (Gdx.graphics.getHeight()/5.5));
                        }
                        else{
                            pillarRect1.set(vec.x + Gdx.graphics.getWidth() / 38,0, Gdx.graphics.getWidth()/18, (float) (Gdx.graphics.getHeight()/4));
                            pillarRect2.set(vec.x + Gdx.graphics.getWidth() / 19, Gdx.graphics.getHeight()/4, Gdx.graphics.getWidth()/38, (float) (Gdx.graphics.getHeight()/5.5));
                        }
                    }
                    else{
                        if (Gdx.graphics.getWidth() <= 800){
                            pillarRect1.set(vec.x + Gdx.graphics.getWidth() / 33, (float) (Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/2.4), Gdx.graphics.getWidth()/65, (float) (Gdx.graphics.getHeight()/4.4));
                            pillarRect2.set(vec.x + Gdx.graphics.getWidth() / 47, Gdx.graphics.getHeight()- Gdx.graphics.getHeight()/6, Gdx.graphics.getWidth()/25, (float) (Gdx.graphics.getHeight()/2));
                        }
                        else if (Gdx.graphics.getWidth() > 1280){
                            pillarRect1.set(vec.x + Gdx.graphics.getWidth() / 33, (float) (Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/2.4), Gdx.graphics.getWidth()/65, (float) (Gdx.graphics.getHeight()/4.4));
                            pillarRect2.set(vec.x + Gdx.graphics.getWidth() / 47, Gdx.graphics.getHeight()- Gdx.graphics.getHeight()/6, Gdx.graphics.getWidth()/25, (float) (Gdx.graphics.getHeight()/2));
                        }
                        else{
                            pillarRect1.set(vec.x + Gdx.graphics.getWidth() / 33, (float) (Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/2.4), Gdx.graphics.getWidth()/65, (float) (Gdx.graphics.getHeight()/4.4));
                            pillarRect2.set(vec.x + Gdx.graphics.getWidth() / 47, Gdx.graphics.getHeight()- Gdx.graphics.getHeight()/6, Gdx.graphics.getWidth()/25, (float) (Gdx.graphics.getHeight()/2));
                        }

                    }
                    if (isPlaneCollisionWithPillar()){
                        if (GameManager.gameState != GameManager.GameState.GAME_OVER){
                            GameManager.gameState = GameManager.GameState.GAME_OVER;
                        }
                    }
                    break;
                }
                if (lastPillarPosition.x <= Gdx.graphics.getWidth()/1000){ // <= потому что на экранах <1000 будет 0.9, 0.8... что = 0
                    System.out.println("DOBAVLIAU VECTOR");
                    addPillar(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // добавляем скалу
                }
        }

    public void renderPillar(SpriteBatch batch){

        //System.out.println("renderPillar here");
                for (Vector2 vec: pillars){
                    if (vec.y == 1){
                        if (Gdx.graphics.getWidth() <= 800){
                            batch.draw(pillarUp, vec.x, 0, Gdx.graphics.getWidth() / 10, (float) (Gdx.graphics.getHeight() / 2.3)); // Отрисовка скалы внизу экрана
                            //batch.draw(testOverlapsPillar1, vec.x + Gdx.graphics.getWidth() / 38,0, Gdx.graphics.getWidth()/18, (float) (Gdx.graphics.getHeight()/4)); // Отрисовка черной области для проверки коллизий между объектами
                            //batch.draw(testOverlapsPillar2, vec.x + Gdx.graphics.getWidth() / 19, Gdx.graphics.getHeight()/4, Gdx.graphics.getWidth()/38, (float) (Gdx.graphics.getHeight()/5.5)); //Отрисовка черной области для проверки коллизий между объектами
                        }
                        else if (Gdx.graphics.getWidth() > 1280){
                            batch.draw(pillarUp, vec.x, 0, Gdx.graphics.getWidth() / 10, (float) (Gdx.graphics.getHeight() / 2.3)); // Отрисовка скалы внизу экрана
                            //batch.draw(testOverlapsPillar1, vec.x + Gdx.graphics.getWidth() / 38,0, Gdx.graphics.getWidth()/18, (float) (Gdx.graphics.getHeight()/4)); // Отрисовка черной области для проверки коллизий между объектами
                            //batch.draw(testOverlapsPillar2, vec.x + Gdx.graphics.getWidth() / 19, Gdx.graphics.getHeight()/4, Gdx.graphics.getWidth()/38, (float) (Gdx.graphics.getHeight()/5.5)); //Отрисовка черной области для проверки коллизий между объектами
                        }
                        else {
                            batch.draw(pillarUp, vec.x, 0, Gdx.graphics.getWidth() / 10, (float) (Gdx.graphics.getHeight() / 2.3)); // Отрисовка скалы внизу экрана
                            //batch.draw(testOverlapsPillar1, vec.x + Gdx.graphics.getWidth() / 38,0, Gdx.graphics.getWidth()/18, (float) (Gdx.graphics.getHeight()/4)); // Отрисовка черной области для проверки коллизий между объектами
                            //batch.draw(testOverlapsPillar2, vec.x + Gdx.graphics.getWidth() / 19, Gdx.graphics.getHeight()/4, Gdx.graphics.getWidth()/38, (float) (Gdx.graphics.getHeight()/5.5)); //Отрисовка черной области для проверки коллизий между объектами
                        }
                    }
                    else{
                        if (Gdx.graphics.getWidth() <= 800){
                            batch.draw(pillarDown, vec.x, Gdx.graphics.getHeight() - (float) (Gdx.graphics.getHeight() / 2.3), Gdx.graphics.getHeight() / 10, (float) (Gdx.graphics.getHeight() / 2.3));// Отрисовка скалы сверху экрана
                            //batch.draw(testOverlapsPillar1, vec.x + Gdx.graphics.getWidth() / 33, (float) (Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/2.4), Gdx.graphics.getWidth()/65, (float) (Gdx.graphics.getHeight()/4.4)); //Отрисовка черной области для проверки коллизий между объектами
                            //batch.draw(testOverlapsPillar2, vec.x + Gdx.graphics.getWidth() / 47, Gdx.graphics.getHeight()- Gdx.graphics.getHeight()/6, Gdx.graphics.getWidth()/25, (float) (Gdx.graphics.getHeight()/2)); //Отрисовка черной области для проверки коллизий между объектами
                        }
                        else if (Gdx.graphics.getWidth() > 1280){
                            batch.draw(pillarDown, vec.x, Gdx.graphics.getHeight() - (float) (Gdx.graphics.getHeight() / 2.3), Gdx.graphics.getHeight() / 10, (float) (Gdx.graphics.getHeight() / 2.3));// Отрисовка скалы сверху экрана
                            //batch.draw(testOverlapsPillar1, vec.x + Gdx.graphics.getWidth() / 33, (float) (Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/2.4), Gdx.graphics.getWidth()/65, (float) (Gdx.graphics.getHeight()/4.4)); //Отрисовка черной области для проверки коллизий между объектами
                            //batch.draw(testOverlapsPillar2, vec.x + Gdx.graphics.getWidth() / 47, Gdx.graphics.getHeight()- Gdx.graphics.getHeight()/6, Gdx.graphics.getWidth()/25, (float) (Gdx.graphics.getHeight()/2)); //Отрисовка черной области для проверки коллизий между объектами
                        }
                        else {
                            //batch.draw(pillarDown, vec.x, Gdx.graphics.getHeight() - pillarDown.getRegionHeight());// Отрисовка скалы сверху экрана
                            batch.draw(pillarDown, vec.x, Gdx.graphics.getHeight() - (float) (Gdx.graphics.getHeight() / 2.3), Gdx.graphics.getHeight() / 10, (float) (Gdx.graphics.getHeight() / 2.3));// Отрисовка скалы сверху экрана
                            //batch.draw(testOverlapsPillar1, vec.x + Gdx.graphics.getWidth() / 33, (float) (Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/2.4), Gdx.graphics.getWidth()/65, (float) (Gdx.graphics.getHeight()/4.4)); //Отрисовка черной области для проверки коллизий между объектами
                            //batch.draw(testOverlapsPillar2, vec.x + Gdx.graphics.getWidth() / 47, Gdx.graphics.getHeight()- Gdx.graphics.getHeight()/6, Gdx.graphics.getWidth()/25, (float) (Gdx.graphics.getHeight()/2)); //Отрисовка черной области для проверки коллизий между объектами
                        }
                    }
                }
    }

    private boolean isPlaneCollisionWithPillar(){

        if(Plane.planeRect.overlaps(pillarRect1) || Plane.planeRect.overlaps(pillarRect2)){
            Gdx.input.vibrate(100);
            GameManager.crashSound.play();
            return true;
        }
        return false;
    }

    public Array<Vector2> getPillars() {
        return pillars;
    }

    public Rectangle getPillarRect1() {
        return pillarRect1;
    }

    public Rectangle getPillarRect2() {
        return pillarRect2;
    }
}

