package com.turnbasedgame.game;

import com.badlogic.gdx.Game;
import com.turnbasedgame.game.Screens.GameScreen.GameScreen;
import com.turnbasedgame.game.Screens.MainScreen.MainScreen;
import com.turnbasedgame.game.Utilities.Console;

public class TurnBasedGame extends Game {

	/* GAME PROPERTIES */

	public static MainScreen mainScreen;
	public static GameScreen gameScreen;

	public static String currentScreen;

	// GAME SCREENS

	/** INITIALISING */

	public void initialise() {
		Global.initialise();
		this.initialiseScreens();
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
		this.initialise();

		Global.create();

		this.informGameStarted();

		this.setMainScreen();
	}

	/** UPDATING */

	public void update() {
		Global.update();
	}

	/** RENDERING */

	@Override
	public void render () {
		this.update();

		super.render();
		Global.render();
	}

	/** DISPOSING */

	@Override
	public void dispose() {
		super.dispose();
		this.informGameDisposed();
		Global.dispose();
	}

	/** INFORMING */

	public void informGameStarted() {
		Console.addLine("main", "GAME SUCCESSFULLY INITIALISED AND STARTED!", Console.LineType.SUCCESS);
	}

	public void informGameDisposed() {
		Console.addLine("main", "GAME SUCCESSFULLY DISPOSED AND FINISHED!", Console.LineType.SUCCESS);
	}
}
