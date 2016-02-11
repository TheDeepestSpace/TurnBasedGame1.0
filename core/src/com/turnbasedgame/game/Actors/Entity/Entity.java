package com.turnbasedgame.game.Actors.Entity;

import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * Created by Boris on 11.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Entity implements EntityBaseInterface {
    public static ArrayList<Entity> list;
    public static ArrayList<Entity> userList;
    public static ArrayList<Entity> aiList;

    /* PROPERTIES */

    String fullName;

    Vector3 gridCoordinates;
    Vector3 sceneCoordinates;

    /** INITIALISING */

    public static void initialiseClass() {
        list = new ArrayList<Entity>();
        userList = new ArrayList<Entity>();
        aiList = new ArrayList<Entity>();
    }

    @Override
    public void initialise() {

    }

    Entity() {
        this.initialise();
    }

    /** CREATING AND SETTING UP */

    @Override
    public void setUp() {

    }

    /** UPDATING */

    public static void updateInstances() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).update();
        }
    }

    @Override
    public void update() {

    }

    /** INTERACTING */

    /** GETTERS / SETTERS */

    /** RENDERING */

    public static void renderInstances() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).render();
        }
    }

    @Override
    public void render() {

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

    @Override
    public void dispose() {

    }

    /** INFORMING */

    @Override
    public void informCreated() {

    }

    @Override
    public void informDisposed() {

    }
}
