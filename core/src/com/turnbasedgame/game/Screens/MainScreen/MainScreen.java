package com.turnbasedgame.game.Screens.MainScreen;

import com.turnbasedgame.game.Screens.Screen;
import com.turnbasedgame.game.TurnBasedGame;
import com.turnbasedgame.game.Utilities.Console;
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

        UI.setUp();
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
        super.hide();
    }

    /** DISPOSING */

    @Override
    public void dispose() {
        super.dispose();
    }

    /** INFORMING */

    @Override
    public void informSet() {
        Console.addLine("main", "Game was relocated to Main Screen", Console.LineType.REGULAR);
    }

    @Override
    public void informHid() {
        Console.addLine("main", "User left Main Screen", Console.LineType.REGULAR);
    }

    @Override
    public void informDisposed() {
        Console.addLine("main", "Main Screen was successfully disposed", Console.LineType.DISPOSED);
    }
}
