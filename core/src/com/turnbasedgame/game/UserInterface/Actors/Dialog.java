package com.turnbasedgame.game.UserInterface.Actors;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.turnbasedgame.game.Global;
import com.turnbasedgame.game.UserInterface.GlobalUI;

import java.util.ArrayList;

/**
 * Created by Boris on 09.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Dialog extends com.badlogic.gdx.scenes.scene2d.ui.Dialog{
    public static ArrayList<Dialog> list;

    String name;

    /** INITIALISING */

    public static void initialise() {
        list = new ArrayList<Dialog>();
    }

    /** CREATING AND SETTING */

    public static Dialog addInstance(String name, String title, Log bodyText, int dialogWindowWidth, Button... buttons) {
        Dialog dialog = new Dialog(title, GlobalUI.skin);
        dialog.setUp(name);
        bodyText.setWrap(true);
        bodyText.setAlignment(Align.center);
        dialog.getContentTable().add(bodyText).width(dialogWindowWidth);
        dialog.getButtonTable().add(buttons);
        dialog.pack();
        dialog.setMovable(false);
        dialog.center();

        list.add(dialog);

        return dialog;
    }

    Dialog(String title, Skin skin) {
        super(title, skin);
    }

    private void setUp(String name) {
        this.name = name;

        this.show(Global.stage);
    }

    /** GETTERS / SETTERS */

    public static Dialog getDialog(String name) {
        for (int i = 0, len = list.size(); i < len; i++) {
            if (list.get(i).name.equals(name)) {
                return list.get(i);
            }
        }

        return null;
    }

    /** RENDERING */

    /** DISPOSING */

    public static void disposeInstances() {
        for (int i = 0, len = list.size(); i < len; i++) {
            list.get(i).dispose();
        }

        list.clear();
    }

    public void dispose() {
        this.clear();
        this.reset();
        this.remove();

        this.name = null;
    }

    public void localDispose() {
        for (int i = 0, len = this.getCells().size; i < len; i++) {
            if (this.getCells().get(i).getActor() instanceof Button) {
                ((Button) this.getCells().get(i).getActor()).dispose();
            }

            if (this.getCells().get(i).getActor() instanceof Checkbox) {
                ((Checkbox) this.getCells().get(i).getActor()).dispose();
            }

            if (this.getCells().get(i).getActor() instanceof Log) {
                ((Log) this.getCells().get(i).getActor()).dispose();
            }
        }

        this.clear();
        this.reset();
        this.remove();

        this.name = null;

        list.remove(this);

        System.gc();
    }
}
