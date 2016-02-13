package com.turnbasedgame.game.Screens.GameScreen;

import com.badlogic.gdx.InputProcessor;
import com.turnbasedgame.game.Actors.Camera;
import com.turnbasedgame.game.Actors.User.User;
import com.turnbasedgame.game.Utilities.Console;

/**
 * Created by Boris on 10.02.2016.
 * Project: TurnBasedGame1.0
 */
public class GameScreenInputProcessor implements InputProcessor {
    boolean dragged = false;

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        dragged = false;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!dragged) {
            informTouchUpRegistered(screenX, screenY);
            User.selectEntity(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        dragged = true;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        Camera.zoom((float) amount / 100);
        return false;
    }

    /** INFORMING */

    void informTouchUpRegistered(int screenX, int screenY) {
        Console.addLine("main", "Input Processor registered 'touch up' at [" + screenX + ";" + screenY + "]", Console.LineType.REGULAR);
    }
}
