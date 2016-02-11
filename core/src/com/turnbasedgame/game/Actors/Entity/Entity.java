package com.turnbasedgame.game.Actors.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.turnbasedgame.game.TurnBasedGame;
import com.turnbasedgame.game.Utilities.Console;
import com.turnbasedgame.game.Utilities.Geometry;
import com.turnbasedgame.game.Utilities.Rendering.Renderer;
import com.turnbasedgame.game.Utilities.RewrittenClasses.ModelInstance;

import java.util.ArrayList;

/**
 * Created by Boris on 11.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Entity {
    public static String className = "ENTITY";

    public static ArrayList<Entity> list;
    public static ArrayList<Entity> userList;
    public static ArrayList<Entity> aiList;

    /* PROPERTIES */

    String fullName;
    int listIndex;

    Vector3 gridCoordinates;
    Vector3 sceneCoordinates;

    boolean artificial;



    /* VISUALISING */

    Model model;
    ModelInstance modelInstance;

    /** INITIALISING */

    public static void initialiseClass() {
        list = new ArrayList<Entity>();
        userList = new ArrayList<Entity>();
        aiList = new ArrayList<Entity>();
    }
    
    public void initialise() {
        this.fullName = "n/a";
        this.listIndex = -1;

        this.gridCoordinates = new Vector3();
        this.sceneCoordinates = new Vector3();

        this.artificial = false;
    }

    Entity() {
        this.initialise();
    }

    /** CREATING AND SETTING UP */

    public static Entity addInstance(Vector3 gridCoordinates, boolean artificial) {
        Entity entity = new Entity();
        entity.setUp(gridCoordinates, artificial);
        return entity;
    }

    public void setUp(Vector3 gridCoordinates, boolean artificial) {
        this.gridCoordinates = gridCoordinates.cpy();
        this.artificial = artificial;

        list.add(this);

        this.listIndex = list.size() - 1;

        this.fullName = className + "_" + this.listIndex;

        if (artificial) aiList.add(this);
        if (!artificial) userList.add(this);

        this.setUpModel();

        this.informCreated();
    }

    void setUpModel() {
        this.model = TurnBasedGame.gameScreen.modelLoader.loadModel(Gdx.files.internal("ACTORS/ENTITY/" + className + "/MODELS/model" + this.artificial + ".g3db"));
        this.modelInstance = new ModelInstance(this.model);

        this.updateSceneCoordinates();
        this.updateModelPosition();
    }

    /** UPDATING */

    public static void updateInstances() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).update();
        }
    }

    public void update() {

    }

    void updateSceneCoordinates() {
        this.sceneCoordinates = Geometry.getSceneCoordinates(this.gridCoordinates);
    }

    void updateModelPosition() {
        this.modelInstance.transform.setToTranslation(this.sceneCoordinates);
        this.updateModelBoundingBox();
    }

    void updateModelBoundingBox() {

    }

    /** INTERACTING */

    void die() {
        if (this.artificial) aiList.remove(this);
        if (!this.artificial) userList.remove(this);

        list.remove(this);

        this.dispose();

        this.informDied();
    }

    /** GETTERS / SETTERS */

    /** RENDERING */

    public static void renderInstances() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).render();
        }
    }

    public void render() {
        Renderer.renderModelInstance(modelInstance);
    }

    /** DISPOSING / RESETTING */

    public static void disposeClass() {
        disposeInstances();

        list.clear();
        userList.clear();
        aiList.clear();
    }

    static void disposeInstances() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).dispose();
        }
    }

    public void dispose() {
        this.informDisposed();

        this.model.dispose();
    }

    /** INFORMING */

    void informCreated() {
        Console.addLine("gameConsole", this.fullName + " was spawned at " + this.gridCoordinates, Console.LineType.SUCCESS);
    }

    void informDisposed() {
        Console.addLine("gameConsole", this.fullName + " was successfully disposed!", Console.LineType.SUCCESS);
    }

    void informDied() {
        Console.addLine("gameConsole", this.fullName + " died!", Console.LineType.ERROR);
    }
}
