package com.airplane.game.GameObjects;

import com.airplane.game.Managers.GameManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;



public class RockPillar {

    private static Array<Vector2> pillars;
    private static Vector2 pillarPosition;
    private static Vector2 lastPillarPosition;
    private static TextureRegion pillarUp;
    private static TextureRegion pillarDown;

    public static void initializePillar(float width, float height){

        pillars = new Array<Vector2>();
        pillarUp = GameManager.atlas.findRegion("rockSnow");
        pillarDown = GameManager.atlas.findRegion("rockSnowDown");

        pillarPosition = new Vector2();
        lastPillarPosition = new Vector2();

        System.out.println("initializePillar here");

    }

    private static void addPillar(float width, float height){

        System.out.println("addPillar here");

        if(pillars.size == 0) {
            pillarPosition.x = (float) (width + Math.random()* (width/2));
        }
        else {
            pillarPosition.x = (float) (lastPillarPosition.x + (width + Math.random()* (width/2)));
        }

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




                for (Vector2 vec: pillars){

                    vec.x -= Plane.planePosition.x - Plane.planeDefaultPosition.x;

                   /*if (vec.x + pillarUp.getRegionWidth() < -10);
                    {
                        System.out.println("NE UDALIAU MENYA");
                        pillars.removeValue(vec,false); // удаляем скалу, если она вылезла за пределы экрана слева
                    }*/

                }
                if (lastPillarPosition.x < Gdx.graphics.getWidth()/10){
                    addPillar(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // добавляем скалу если ближайшая скала достигла 1/3 экрана по x
                }

        }


    public static void renderPillar(SpriteBatch batch){

        System.out.println("renderPillar here");

                for (Vector2 vec: pillars){
                    if (vec.y == 1){
                        batch.draw(pillarUp, vec.x,0);
                    }
                    else{
                        batch.draw(pillarDown, vec.x, Gdx.graphics.getHeight() - pillarDown.getRegionHeight());
                    }
                }

    }
}

