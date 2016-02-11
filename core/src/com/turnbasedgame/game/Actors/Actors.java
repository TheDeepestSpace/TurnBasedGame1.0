package com.turnbasedgame.game.Actors;

import com.badlogic.gdx.math.Vector3;
import com.turnbasedgame.game.Actors.Entity.Entity;
import com.turnbasedgame.game.Actors.Grid.Grid;
import com.turnbasedgame.game.Utilities.Console;

/**
 * Created by Boris on 10.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Actors {
    /** INITIALISING */

    public static void initialise() {
        Camera.initialise();
        Grid.initialise();
        Entity.initialiseClass();
        informInitialised();
    }

    /** CREATING AND SETTING UP */

    public static void create() {
        Camera.create();

        Grid.setUp();
        Camera.setLinkedGrid("gameGrid");
        informCreated();
    }

    /** UPDATING */

    public static void update() {
        Entity.updateInstances();
    }

    /** RENDERING */

    public static void render() {
        Camera.renderCenter();
        Grid.render();
        Entity.renderInstances();
    }

    /** DISPOSING */

    public static void dispose() {
        Camera.dispose();
        Grid.dispose();
        Entity.disposeClass();
        informDisposed();
    }

    /** INFORMING */

    static void informInitialised() {
        Console.addLine("main", "Actors were successfully initialised", Console.LineType.SUCCESS);
    }

    static void informCreated() {
        Console.addLine("main", "Actors were successfully created", Console.LineType.SUCCESS);
    }

    static void informDisposed() {
        Console.addLine("main", "Actors were successfully disposed", Console.LineType.SUCCESS);
    }
}
