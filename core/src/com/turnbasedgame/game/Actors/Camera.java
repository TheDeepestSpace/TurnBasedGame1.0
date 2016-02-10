package com.turnbasedgame.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.turnbasedgame.game.Actors.Grid.Grid;
import com.turnbasedgame.game.Screens.GameScreen.GameScreen;

/**
 * Created by Boris on 10.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Camera {
    public static Preferences settings;

    public static OrthographicCamera camera;
    public static CameraInputController cameraInputController;
    public static int distance;
    public static String linkedGrid;

    public static Color centerColor;

    public static boolean isRenderingCenter;

    /** INITIALISING */

    public static void initialise() {
        settings = Gdx.app.getPreferences("CAMERA_PREFERENCES");
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cameraInputController = new CameraInputController(camera);
        distance = 1000;
        centerColor = Color.WHITE;
        isRenderingCenter = settings.getBoolean("IS_RENDERING_CENTER", true);
        linkedGrid = "n/a";
    }

    /** CREATING AND SETTING */
    public static void create() {
        camera.position.set(0 - distance, distance, 0 - distance);
        camera.lookAt(0, 0, 0);
        camera.near = 0;
        camera.far = 10000;
        camera.zoom = 0.03f;
        camera.update();
    }

    /** UPDATING */

    static Rectangle cameraR = new Rectangle(0, 0, 1, 1);
    public static void move(float deltaX, float deltaY) {

        cameraR.x = camera.position.x + distance;
        cameraR.y = camera.position.z + distance;

        deltaX = deltaX * camera.zoom * 2;
        deltaY = deltaY * camera.zoom * 2;

        if (camera.position.x + distance > camera.position.z + distance + Grid.getGrid(linkedGrid).size.x * Grid.tileSceneSize){
            camera.position.x -= 1;
            camera.position.z += 1;
        }else if (camera.position.x + distance > -(camera.position.z + distance) + Grid.getGrid(linkedGrid).size.x * Grid.tileSceneSize + Grid.getGrid(linkedGrid).size.z * Grid.tileSceneSize) {
            camera.position.x -= 1;
            camera.position.z -= 1;
        }else if (camera.position.x + distance < camera.position.z + distance - Grid.getGrid(linkedGrid).size.z * Grid.tileSceneSize) {
            camera.position.x += 1;
            camera.position.z -= 1;
        }else if (camera.position.x + distance < -(camera.position.z + distance)){
            camera.position.x += 1;
            camera.position.z += 1;
        }else{
            camera.position.add(deltaX / 2 + deltaY / 2, 0, deltaY / 2 - deltaX / 2);
            centerColor = Color.WHITE;
        }

        camera.update();
    }

    public static void zoom(float amount) {
        if ((camera.zoom + amount) >= 0.01f && (camera.zoom + amount) <= 0.7f){
            camera.zoom += amount;
        }else if ((camera.zoom + amount) < 0.01f){
            camera.zoom = 0.01f;
        }else if ((camera.zoom + amount) > 0.7f){
            camera.zoom = 0.7f;
        }

        camera.update();
    }

    /** INTERACTING */

    /** GETTERS / SETTING */

    public static void setLinkedGrid(String gridName) {
        linkedGrid = gridName;
    }

    /** RENDERING */

    public static void renderCenter() {
        if (isRenderingCenter) {
            GameScreen.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            GameScreen.shapeRenderer.setColor(centerColor);
            GameScreen.shapeRenderer.line(Gdx.graphics.getWidth() / 2 - 10, Gdx.graphics.getHeight() / 2 - 10, Gdx.graphics.getWidth() / 2 + 10, Gdx.graphics.getHeight() / 2 + 10);
            GameScreen.shapeRenderer.line(Gdx.graphics.getWidth() / 2 - 10, Gdx.graphics.getHeight() / 2 + 10, Gdx.graphics.getWidth() / 2 + 10, Gdx.graphics.getHeight() / 2 - 10);
            GameScreen.shapeRenderer.end();
        }
    }

    /** DISPOSING */

    public static void dispose() {
        settings.putBoolean("IS_RENDERING_CENTER", isRenderingCenter);
        settings.flush();

        linkedGrid = null;
    }
}
