package com.airplane.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;

public class BaseScene extends ScreenAdapter {

    protected Airplane game;
    private boolean keyHandled;

    public BaseScene(Airplane airplane){

        game = airplane;
        keyHandled = false;
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            if (keyHandled) {
                return;
            }
            handleBackPress();
            keyHandled = true;
        } else {
            keyHandled = false;
        }
    }

    protected void handleBackPress(){

    }

}
