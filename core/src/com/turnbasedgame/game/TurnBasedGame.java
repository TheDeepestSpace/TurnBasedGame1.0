package com.turnbasedgame.game;

import com.badlogic.gdx.Game;
import com.turnbasedgame.game.Screens.GameScreen.GameScreen;
import com.turnbasedgame.game.Screens.MainScreen.MainScreen;

public class TurnBasedGame extends Game {

	/* GAME PROPERTIES */

	public static MainScreen mainScreen;
	public static GameScreen gameScreen;

	// GAME SCREENS

	/** INITIALISING */

	public void initialise() {
		informGameInitialised();
	}

	/** CREATING AND SETTING UP */

	@Override
	public void create () {

	}

	/** UPDATING */

	public void update() {

	}

	/** RENDERING */

	@Override
	public void render () {
		update();

		super.render();
	}

	/** DISPOSING */

	@Override
	public void dispose() {
		super.dispose();
		informGameDisposed();
	}

	/** INFORMING */

	public void informGameInitialised() {

	}

	public void informGameDisposed() {

	}
}
