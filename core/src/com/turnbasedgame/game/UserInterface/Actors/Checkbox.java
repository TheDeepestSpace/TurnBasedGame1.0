package com.turnbasedgame.game.UserInterface.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.turnbasedgame.game.Global;
import com.turnbasedgame.game.UserInterface.GlobalUI;

import java.util.ArrayList;

/**
 * Created by Boris on 09.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Checkbox extends CheckBox{
    public static Preferences preferences;
    public static ArrayList<Checkbox> list;

    String name;


    /** INITIALISING */

    public static void initialise() {
        preferences = Gdx.app.getPreferences("CHECKBOX_PREFERENCES");
        list = new ArrayList<Checkbox>();
    }

    /** CREATING AND SETTING */

    public static Checkbox addInstance(String name, String text, ChangeListener changeListener) {
        Checkbox checkbox = new Checkbox(text, GlobalUI.skin);
        checkbox.setUp(name);
        checkbox.setChecked(preferences.getBoolean("CHECKBOX_" + checkbox.name + "_CHECKED"));
        checkbox.addListener(changeListener);

        list.add(checkbox);

        return checkbox;
    }

    Checkbox(String text, Skin skin) {
        super(text, skin);
    }

    private void setUp(String name) {
        this.name = name;

        Global.stage.addActor(this);
    }

    /** GETTERS / SETTERS */

    public static Checkbox getCheckbox(String name) {
        for (int i = 0, len = list.size(); i < len; i++) {
            if (list.get(i).name.equals(name)) {
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
        preferences.flush();
    }

    public void dispose() {
        preferences.putBoolean("CHECKBOX_" + this.name + "_CHECKED", isChecked());

        this.clear();
        this.reset();
        this.remove();

        this.name = null;

    }
}
