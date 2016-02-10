package com.turnbasedgame.game.Screens;

import com.turnbasedgame.game.TurnBasedGame;

/**
 * Created by Boris on 09.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Screen implements ScreenExtended{
    public TurnBasedGame game;

    public Screen(TurnBasedGame game) {
        this.game = game;
    }

    @Override
    public void initialise() {

    }

    @Override
    public void show() {
        this.initialise();
        this.informSet();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(float delta) {
        this.update();
    }

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
        this.dispose();
        this.informHid();
    }

    @Override
    public void dispose() {
        this.informDisposed();
    }

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
