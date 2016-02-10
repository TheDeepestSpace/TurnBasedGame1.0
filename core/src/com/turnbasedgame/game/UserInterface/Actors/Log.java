package com.turnbasedgame.game.UserInterface.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.turnbasedgame.game.Global;
import com.turnbasedgame.game.UserInterface.GlobalUI;

import java.util.ArrayList;

/**
 * Created by Boris on 09.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Log extends Label{
    public static Preferences preferences;
    public static ArrayList<Log> list;

    String name;

    /** INITIALISING */

    public static void initialise() {
        preferences = Gdx.app.getPreferences("LOG_PREFERENCES");
        list = new ArrayList<Log>();
    }

    /** CREATING AND SETTING */

    public static Log addInstance(String name, Vector2 position, String fontName, Color textColor) {
        Log log = new Log("n/a", GlobalUI.skin, fontName, textColor);
        log.setPosition(position.x, position.y);
        log.setUp(name);
        log.setVisible(preferences.getBoolean("LOG_" + log.name + "_VISIBLE", true));

        list.add(log);

        return log;
    }

    public static Log addInstance(String name, String text, String fontName, Color textColor) {
        Log log = new Log(text, GlobalUI.skin, fontName, textColor);
        log.setUp(name);
        log.setVisible(preferences.getBoolean("LOG_" + log.name + "_VISIBLE", true));

        list.add(log);

        return log;
    }

    public Log(String text, Skin skin, String fontName, Color color) {
        super(text, skin, fontName, color);
    }

    private void setUp(String name) {
        this.name = name;

        Global.stage.addActor(this);
    }

    /** UPDATING */

    public void update(String text) {
        setText(text);
    }

    /** GETTERS / SETTERS */

    public static Log getLog(String name) {
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

        preferences.flush();
        list.clear();
    }

    public void dispose() {
        preferences.putBoolean("LOG_" + this.name + "_VISIBLE", isVisible());

        this.clear();
        this.remove();

        this.name = null;
    }
}
