package com.turnbasedgame.game.Screens.GameScreen;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.UBJsonReader;
import com.turnbasedgame.game.Screens.Screen;
import com.turnbasedgame.game.TurnBasedGame;
import com.turnbasedgame.game.Utilities.Rendering.Renderer;
import com.turnbasedgame.game.Utilities.RewrittenClasses.DefaultShader;
import com.turnbasedgame.game.Utilities.RewrittenClasses.DefaultShaderProvider;

/**
 * Created by Boris on 09.02.2016.
 * Project: TurnBasedGame1.0
 */
public class GameScreen extends Screen {

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
        this.jsonReader = new UBJsonReader();
        this.modelLoader = new G3dModelLoader(jsonReader);

        this.modelBuilder = new ModelBuilder();

        DefaultShader.Config config = new DefaultShader.Config();

        ShaderProvider shaderProvider = new DefaultShaderProvider(config);

        this.environment = new Environment();

        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.0f, 0.0f, 0.0f, 1f));

        this.modelBatch = new ModelBatch(shaderProvider);
        shapeRenderer = new ShapeRenderer();
    }

    /** CREATING AND SETTING UP */

    @Override
    public void show() {
        super.show();

        this.informSet();
    }

    /** UPDATING */

    @Override
    public void update() {

    }

    /** RENDERING */

    @Override
    public void render(float delta) {
        super.render(delta);
        Renderer.clearScreen();
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
        this.informHid();
    }

    /** DISPOSING */

    @Override
    public void dispose() {
        this.informDisposed();
    }

    /** INFORMING */

    @Override
    public void informSet() {

    }

    @Override
    public void informHid() {

    }

    @Override
    public void informDisposed() {

    }
}
