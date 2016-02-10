package com.turnbasedgame.game.Actors;

import com.badlogic.gdx.math.Vector3;
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
        Grid.initialiseInstances();
        informInitialised();
    }

    /** CREATING AND SETTING UP */

    public static void create() {
        Camera.create();

        Grid.addInstance("gameGrid");
        Grid.setUpInstance(
                "gameGrid",
                new Vector3(20, 10, 20),
                "default",
                true
        );
        Camera.setLinkedGrid("gameGrid");
        informCreated();
    }

    /** UPDATING */

    public static void update() {
    }

    /** RENDERING */

    public static void render() {
        Camera.renderCenter();
        Grid.renderInstances();
    }

    /** DISPOSING */

    public static void dispose() {
        Camera.dispose();
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
