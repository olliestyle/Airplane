package com.airplane.game.Managers;


import com.airplane.game.Airplane;
import com.airplane.game.GameObjects.Plane;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class InputManager extends InputAdapter {

    public static Vector3 touchPosition = new Vector3(); // объект, хранящий в себе информацию о касании пользователя экрана
    private OrthographicCamera camera;
    private Plane plane;

    public InputManager(OrthographicCamera camera, Plane plane) {

        this.camera = camera;
        this.plane = plane;
    }


    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {

        touchPosition.set(screenX,screenY,0);// получаем координаты касания относительно области просмотра нашей камеры
        //System.out.println("Before unproject x + " + touchPosition.x);
        //System.out.println("Before unproject y + " + touchPosition.y);
        camera.unproject(touchPosition);
        //System.out.println("After unproject x + " + touchPosition.x);
        //System.out.println("After unproject y + " + touchPosition.y);

        float touchX = touchPosition.x;
        float touchY = touchPosition.y;

        plane.handleTouch(touchX, touchY);

        return false;
    }

}
