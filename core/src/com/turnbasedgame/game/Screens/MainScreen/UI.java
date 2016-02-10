package com.turnbasedgame.game.Screens.MainScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.turnbasedgame.game.TurnBasedGame;
import com.turnbasedgame.game.UserInterface.Actors.Button;
import com.turnbasedgame.game.UserInterface.Actors.Dialog;
import com.turnbasedgame.game.UserInterface.Actors.Log;
import com.turnbasedgame.game.UserInterface.GlobalUI;

/**
 * Created by Boris on 09.02.2016.
 * Project: TurnBasedGame1.0
 */
public class UI {

    /** CREATING AND SETTING */

    public static void setUp() {
        GlobalUI.table.reset();
        GlobalUI.table.add(Log.addInstance("MAIN_SCREEN_TITLE", "TINY BATTLES", "font1_64", Color.GREEN)).row();
        GlobalUI.table.add(Button.addInstance("MAIN_SCREEN_START", "PREPARE FOR THE BATTLE", 30, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TurnBasedGame.mainScreen.game.setGameScreen();
            }
        })).row();
        GlobalUI.table.add(Button.addInstance("MAIN_SCREEN_EXIT", "EXIT", 30, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog.addInstance("MAIN_SCREEN_EXIT_CONFIRMATION", "CONFIRM EXIT",
                        Log.addInstance("MAIN_SCREEN_EXIT_CONFIRMATION_DIALOG_BODY", "DO YOU REALLY WANT TO LEAVE?", "font1_32", Color.BLACK),
                        300,
                        Button.addInstance("MAIN_SCREEN_EXIT_CONFIRMATION_DIALOG_EXIT", "EXIT", 30, new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                Gdx.app.exit();
                            }
                        }),
                        Button.addInstance("MAIN_SCREEN_EXIT_CONFIRMATION_DIALOG_STAY", "STAY", 30, new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                Dialog.getDialog("MAIN_SCREEN_EXIT_CONFIRMATION").hide();
                                Dialog.getDialog("MAIN_SCREEN_EXIT_CONFIRMATION").localDispose();
                            }
                        }));
            }
        }));
    }
}
