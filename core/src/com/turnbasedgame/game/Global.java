package com.turnbasedgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.turnbasedgame.game.UserInterface.GlobalUI;
import com.turnbasedgame.game.Utilities.Console;

/**
 *
 * Created by Boris on 09.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Global {
    public static Preferences preferences;
    public static BitmapFont defaultFont;
    public static SpriteBatch spriteBatch;
    public static FrameBuffer fb;
    public static SpriteBatch fbBatch;
    public static Stage stage;

    /** INITIALISING */

    public static void initialise() {
        preferences = Gdx.app.getPreferences("GAME_PREFERENCES");
        defaultFont = new BitmapFont(Gdx.files.internal("UI/FONTS/font16.fnt"));
        initialiseFrameBuffer();
        spriteBatch = new SpriteBatch();
        spriteBatch.enableBlending();
        stage = new Stage();
        stage.setDebugAll(preferences.getBoolean("STAGE_DEBUG", false));
        Gdx.input.setInputProcessor(stage);

        GlobalUI.initialise();
        Console.initialiseInstances();
    }

    static void initialiseFrameBuffer() {
        if (fb != null) fb.dispose();
        fb = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 3, true);
        fb.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        if (fbBatch != null) fbBatch.dispose();
        fbBatch = new SpriteBatch();
    }

    /** CREATING AND SETTING */

    public static void create() {
        GlobalUI.create();
        Console.addInstance("main", new Vector2(20, 20), 0, 20000);
    }

    /** UPDATING */

    public static void update() {
        stage.act();
        GlobalUI.update();
    }

    /** RENDERING */

    public static void render() {
        stage.draw();
        GlobalUI.render();
        Console.renderInstances();
    }

    /** DISPOSING */

    public static void dispose() {
        fb.dispose();
        fbBatch.dispose();
        spriteBatch.dispose();
        preferences.flush();
        GlobalUI.dispose();
        Console.disposeInstances();
    }
}
