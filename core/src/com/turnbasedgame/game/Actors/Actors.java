package com.turnbasedgame.game.Actors;

import com.badlogic.gdx.math.Vector3;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.Entity.AttackingEntity;
import com.turnbasedgame.game.Actors.Entity.Entity;
import com.turnbasedgame.game.Actors.Grid.Grid;
import com.turnbasedgame.game.Utilities.Console;
import com.turnbasedgame.game.Utilities.Game;

/**
 * Created by Boris on 10.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Actors {
    public static AI gameAI;

    /** INITIALISING */

    public static void initialise() {
        Camera.initialise();
        Grid.initialise();
        Entity.initialiseClass();
        informInitialised();

        gameAI = new AI();
        gameAI.initialise();
    }

    /** CREATING AND SETTING UP */

    public static void create() {
        Camera.create();

        Grid.setUp();
        Entity.setUp();
        AttackingEntity.setUp();

        AttackingEntity.addInstance(new Vector3(18, 2, 1), true);
        AttackingEntity.addInstance(new Vector3(4, 2, 15), false);

        informCreated();

        Game.start();
    }

    /** UPDATING */

    public static void update() {
        Entity.updateInstances();
    }

    /** RENDERING */

    public static void render() {
        Grid.render();
        Entity.renderInstances();
        Camera.renderCenter();
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
