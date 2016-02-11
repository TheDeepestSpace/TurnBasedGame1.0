package com.turnbasedgame.game.Actors.Entity;

/**
 * Created by Boris on 11.02.2016.
 * Project: TurnBasedGame1.0
 */
public interface EntityBaseInterface {
    /** INITIALISING */

    void initialise();

    /** SETTING UP */

    void setUp();

    /** UPDATING */

    void update();

    /** RENDERING */

    void render();

    /** DISPOSING */

    void dispose();

    /** INFORMING */

    void informCreated();
    void informDisposed();

}
