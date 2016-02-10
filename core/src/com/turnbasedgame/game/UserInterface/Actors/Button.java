package com.turnbasedgame.game.UserInterface.Actors;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.turnbasedgame.game.Global;
import com.turnbasedgame.game.UserInterface.GlobalUI;

import java.util.ArrayList;

/**
 * Created by Boris on 09.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Button extends TextButton{
    public static ArrayList<Button> list;

    ClickListener currentClickListener;

    String name;

    /** INITIALISING */

    public static void initialise() {
        list = new ArrayList<Button>();
    }

    /** CREATING AND SETTING */

    public static Button addInstance(String name, String text, ClickListener clickListener) {
        Button button = new Button(text, GlobalUI.skin);
        button.setUp(name);
        button.addListener(clickListener);

        list.add(button);

        return button;
    }

    public static Button addInstance(String name, String text, int pad, ClickListener clickListener) {
        Button button = new Button(text, GlobalUI.skin);
        button.setUp(name);
        button.addListener(clickListener);
        button.pad(pad);

        list.add(button);

        return button;
    }

    Button(String text, Skin skin) {
        super(text, skin);
    }

    private void setUp (String name) {
        this.name = name;
        Global.stage.addActor(this);
    }

    /** GETTERS / SETTERS */

    public static void setClickListener(String name, ClickListener clickListener) {
        getButton(name).addListener(clickListener);
        getButton(name).currentClickListener = clickListener;
    }

    public static void removeClickListener(String name) {
        if (getButton(name).currentClickListener != null) {
            getButton(name).removeListener(getButton(name).currentClickListener);
        }
    }

    public static Button getButton(String name) {
        for (int i = 0; i < list.size(); i++) {
            if (name.equals(list.get(i).name)) {
                return list.get(i);
            }
        }

        return null;
    }

    /** DISPOSING */

    public static void disposeInstances() {
        for (int i = 0, len = list.size(); i < len; i++) {
            list.get(i).dispose();
        }

        list.clear();
    }

    protected void dispose() {
        this.name = null;
    }
}
