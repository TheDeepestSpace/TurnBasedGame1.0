package com.turnbasedgame.game.Screens;

import com.badlogic.gdx.Screen;

/**
 * Created by Boris on 09.02.2016.
 * Project: TurnBasedGame1.0
 */
public interface ScreenExtended extends Screen {
    /** INITIALISING */

    void initialise();

    /** UPDATING */

    void update();

    /** INFORMING */

    void informSet();
    void informHid();
    void informDisposed();
}
