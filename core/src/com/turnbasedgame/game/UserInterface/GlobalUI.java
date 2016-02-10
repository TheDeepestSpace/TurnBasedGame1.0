package com.turnbasedgame.game.UserInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.turnbasedgame.game.Global;
import com.turnbasedgame.game.UserInterface.Actors.Button;
import com.turnbasedgame.game.UserInterface.Actors.Checkbox;
import com.turnbasedgame.game.UserInterface.Actors.Dialog;
import com.turnbasedgame.game.UserInterface.Actors.Log;

/**
 * Created by Boris on 09.02.2016.
 * Project: TurnBasedGame1.0
 */
public class GlobalUI {
    public static TextureAtlas atlas;
    public static Skin skin;

    /* UI */
    public static Table table, globalTable;

    /** INITIALISING */

    public static void initialise() {
        atlas = new TextureAtlas("UI/ATLAS/atlas.pack");
        skin = new Skin(Gdx.files.internal("UI/SKIN/skin.json"), atlas);

        Button.initialise();
        Checkbox.initialise();
        Dialog.initialise();
        Log.initialise();

        table = new Table(skin);
        globalTable = new Table(skin);
    }

    /** CREATING AND SETTING */

    public static void create() {
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Global.stage.addActor(table);
        globalTable.setBounds(0, Gdx.graphics.getHeight() - 300, Gdx.graphics.getWidth(), 300);
        Global.stage.addActor(globalTable);

        globalTable.add(Log.addInstance("GLOBAL_GDX_JAVA_HEAP", new Vector2(20, Gdx.graphics.getHeight() - 50), "font64", Color.GREEN));
        globalTable.add().growX().row();
        globalTable.add(Log.addInstance("GLOBAL_FPS", new Vector2(20, Gdx.graphics.getHeight() - 100), "font64", Color.GREEN)).left();

    }

    /** UPDATING */

    public static void update() {
        Log.getLog("GLOBAL_GDX_JAVA_HEAP").update("JAVA HEAP: " + Gdx.app.getJavaHeap());
        Log.getLog("GLOBAL_FPS").update("FPS: " + Gdx.graphics.getFramesPerSecond());
    }

    /** RENDERING */

    public static void render() {

    }

    /** DISPOSING */

    public static void dispose() {
        atlas.dispose();
        skin.dispose();

        table.clear();
        table.reset();
        table.remove();

        globalTable.clear();
        globalTable.reset();
        globalTable.remove();

        Log.disposeInstances();
        Button.disposeInstances();
        Checkbox.disposeInstances();
        Dialog.disposeInstances();
    }
}
