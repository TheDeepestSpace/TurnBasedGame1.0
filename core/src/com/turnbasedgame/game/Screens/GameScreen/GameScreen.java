package com.turnbasedgame.game.Screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.UBJsonReader;
import com.turnbasedgame.game.Actors.Actors;
import com.turnbasedgame.game.Actors.Camera;
import com.turnbasedgame.game.Global;
import com.turnbasedgame.game.Screens.Screen;
import com.turnbasedgame.game.TurnBasedGame;
import com.turnbasedgame.game.Utilities.Console;
import com.turnbasedgame.game.Utilities.Rendering.Renderer;
import com.turnbasedgame.game.Utilities.RewrittenClasses.DefaultShader;
import com.turnbasedgame.game.Utilities.RewrittenClasses.DefaultShaderProvider;

/**
 * Created by Boris on 09.02.2016.
 * Project: TurnBasedGame1.0
 */
public class GameScreen extends Screen {

    /* INPUT */

    public InputMultiplexer inputMultiplexer;

    /* LOADING MODELS */

    public UBJsonReader jsonReader;
    public G3dModelLoader modelLoader;

    /* CREATING MODELS */

    public ModelBuilder modelBuilder;

    /* RENDERING */

    public ModelBatch modelBatch;
    public static ShapeRenderer shapeRenderer;
    public Environment environment;
    public DirectionalShadowLight shadowLight;
    public ModelBatch shadowBatch;

    /** INITIALISING */

    public GameScreen(TurnBasedGame game) {
        super(game);
    }

    @Override
    public void initialise() {
        // INPUT

        this.inputMultiplexer = new InputMultiplexer();

        // LOADING MODELS

        this.jsonReader = new UBJsonReader();
        this.modelLoader = new G3dModelLoader(jsonReader);

        // CREATING MODEL

        this.modelBuilder = new ModelBuilder();

        // RENDERING

        DefaultShader.Config config = new DefaultShader.Config();
        ShaderProvider shaderProvider = new DefaultShaderProvider(config);

        this.environment = new Environment();

        this.modelBatch = new ModelBatch(shaderProvider);
        shapeRenderer = new ShapeRenderer();

        // OTHER

        Console.addInstance(
                "gameConsole",
                new Vector2(Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2),
                0,
                6000
        );

        // ACTORS

        Actors.initialise();
    }

    /** CREATING AND SETTING UP */

    @Override
    public void show() {
        super.show();

        UI.setUp();
        Actors.create();

        this.manageInputProcessors();
    }

    void manageInputProcessors() {
        this.inputMultiplexer.addProcessor(Global.stage);
        this.inputMultiplexer.addProcessor(new GameScreenInputProcessor());
        this.inputMultiplexer.addProcessor(Camera.cameraInputController);

        Gdx.input.setInputProcessor(this.inputMultiplexer);
    }

    /** UPDATING */

    @Override
    public void update() {
        Actors.update();
    }

    /** RENDERING */

    @Override
    public void render(float delta) {
        super.render(delta);
        Renderer.clearScreen();

        Actors.render();
    }

    /** INTERACTING */

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        super.hide();
    }

    /** DISPOSING */

    @Override
    public void dispose() {
        Actors.dispose();
        super.dispose();
    }

    /** INFORMING */

    @Override
    public void informSet() {
        Console.addLine("main", "Game was relocated to Game Screen", Console.LineType.REGULAR);
    }

    @Override
    public void informHid() {
        Console.addLine("main", "User left Game Screen", Console.LineType.REGULAR);
    }

    @Override
    public void informDisposed() {
        Console.addLine("main", "Game Screen was successfully disposed", Console.LineType.DISPOSED);
    }
}
