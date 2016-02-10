package com.turnbasedgame.game;

import com.badlogic.gdx.Game;
import com.turnbasedgame.game.Screens.GameScreen.GameScreen;
import com.turnbasedgame.game.Screens.MainScreen.MainScreen;

public class TurnBasedGame extends Game {

	/* GAME PROPERTIES */

	public static MainScreen mainScreen;
	public static GameScreen gameScreen;

	public static String currentScreen;

	// GAME SCREENS

	/** INITIALISING */

	public void initialise() {
		this.initialiseScreens();
		informGameInitialised();
	}

	void initialiseScreens() {
		mainScreen = new MainScreen(this);
		gameScreen = new GameScreen(this);
	}

	/** CREATING AND SETTING UP */

	public void setMainScreen() {
		setScreen(mainScreen);
		currentScreen = "mainScreen";
		System.gc();
	}

	public void setGameScreen() {
		setScreen(gameScreen);
		currentScreen = "gameScreen";
		System.gc();
	}

	@Override
	public void create () {
		this.initialiseScreens();

		this.setMainScreen();
	}

	/** UPDATING */

	public void update() {

	}

	/** RENDERING */

	@Override
	public void render () {
		this.update();

		super.render();
	}

	/** DISPOSING */

	@Override
	public void dispose() {
		super.dispose();
		this.informGameDisposed();
	}

	/** INFORMING */

	public void informGameInitialised() {

	}

	public void informGameDisposed() {

	}
}
