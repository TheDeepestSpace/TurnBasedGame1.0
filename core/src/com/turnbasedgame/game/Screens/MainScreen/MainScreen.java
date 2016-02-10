package com.turnbasedgame.game.Screens.MainScreen;

import com.turnbasedgame.game.Screens.Screen;
import com.turnbasedgame.game.TurnBasedGame;
import com.turnbasedgame.game.Utilities.Rendering.Renderer;

/**
 * Created by Boris on 09.02.2016.
 * Project: TurnBasedGame1.0
 */
public class MainScreen extends Screen{

    /** INITIALISING */

    public MainScreen(TurnBasedGame game) {
        super(game);
    }

    @Override
    public void initialise() {

    }

    /** CREATING AND SETTING UP */

    @Override
    public void show() {
        super.show();
        this.informSet();
    }

    /** UPDATING */

    @Override
    public void update() {

    }

    /** RENDERING */

    @Override
    public void render(float delta) {
        super.render(delta);

        Renderer.clearScreen();
    }

    /** INTERACTING */

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /** DISPOSING */

    @Override
    public void dispose() {

    }

    /** INFORMING */

    @Override
    public void informSet() {
    }

    @Override
    public void informHid() {

    }

    @Override
    public void informDisposed() {

    }
}
