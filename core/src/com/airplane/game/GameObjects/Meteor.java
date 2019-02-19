package com.airplane.game.GameObjects;

import com.airplane.game.Managers.GameManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import static com.airplane.game.GameObjects.Plane.deltaTime;

public class Meteor {

    private static Array<TextureAtlas.AtlasRegion> meteorTextures = new Array<TextureAtlas.AtlasRegion>();
    private static TextureRegion selectedMeteorTexture;
    private static boolean meteorInScene;
    private static final int METEOR_SPEED=60;
    private static Vector2 meteorPosition= new Vector2();
    private static Vector2 meteorVelocity= new Vector2();
    private static float nextMeteorIn;

    public static void initializeMeteor(){

        meteorTextures.add(GameManager.atlas.findRegion("meteorBrown_med1"));
        meteorTextures.add(GameManager.atlas.findRegion("meteorBrown_med2"));
        meteorTextures.add(GameManager.atlas.findRegion("meteorBrown_small1"));
        meteorTextures.add(GameManager.atlas.findRegion("meteorBrown_small2"));
        meteorTextures.add(GameManager.atlas.findRegion("meteorBrown_tiny1"));
        meteorTextures.add(GameManager.atlas.findRegion("meteorBrown_tiny2"));

        nextMeteorIn = (float) (Math.random()*5);
    }

    public static void renderMeteor(SpriteBatch batch){

        if(meteorInScene)
        {
            batch.draw(selectedMeteorTexture,meteorPosition.x, meteorPosition.y);
        }
    }

    public static void updateMeteor(){

        if(meteorInScene)
        {
            meteorPosition.mulAdd(meteorVelocity, deltaTime);
            meteorPosition.x -= Plane.planePosition.x - Plane.planeDefaultPosition.x;
            if(meteorPosition.x<-10)
            {
                meteorInScene=false;
            }
        }
        nextMeteorIn-=deltaTime;
        if(nextMeteorIn<=0)
        {
            launchMeteor();
        }

    }

    private static void launchMeteor(){

        nextMeteorIn=1.5f+(float)Math.random()*5;
        if(meteorInScene)
        {
            return;
        }
        meteorInScene=true;
        int id= (int)(Math.random()*meteorTextures.size);
        selectedMeteorTexture=meteorTextures.get(id);
        meteorPosition.x= Gdx.graphics.getWidth();
        meteorPosition.y=(float) (80+Math.random()*(Gdx.graphics.getHeight()-80));
        Vector2 destination=new Vector2();
        destination.x=-10;
        destination.y=(float) (80+Math.random()*320);
        destination.sub(meteorPosition).nor();
        meteorVelocity.mulAdd(destination, METEOR_SPEED);
    }

}
