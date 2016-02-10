package com.turnbasedgame.game.Actors;

import com.turnbasedgame.game.Utilities.Console;

/**
 * Created by Boris on 10.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Actors {
    /** INITIALISING */

    public static void initialise() {
        informInitialised();
    }

    /** CREATING AND SETTING UP */

    public static void create() {
        informCreated();
    }

    /** UPDATING */

    public static void update() {

    }

    /** RENDERING */

    public static void render() {

    }

    /** DISPOSING */

    public static void dispose() {
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
