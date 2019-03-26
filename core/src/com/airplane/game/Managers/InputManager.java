package com.airplane.game.Managers;


import com.airplane.game.GameObjects.Plane;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputManager extends InputAdapter {

    public static Vector3 touchPosition = new Vector3(); // объект, хранящий в себе информацию о касании пользователя экрана
    OrthographicCamera camera;

    //конструктор
    public InputManager(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {

        GameManager.tapSound.play();
        touchPosition.set(screenX,screenY,0);// получаем координаты касания относительно области просмотра нашей камеры


        camera.unproject(touchPosition);

        float touchX = touchPosition.x;
        float touchY = touchPosition.y;

        Plane.handleTouch(touchX, touchY);

        return false;
    }

}
