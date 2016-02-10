package com.turnbasedgame.game.Utilities.RewrittenClasses;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;

/**
 * Created by Boris on 10.02.2016.
 * Project: TurnBasedGame1.0
 */
public class DefaultShaderProvider extends BaseShaderProvider{
    public final DefaultShader.Config config;

    public DefaultShaderProvider (final DefaultShader.Config config) {
        this.config = (config == null) ? new DefaultShader.Config() : config;
    }

    public DefaultShaderProvider (final String vertexShader, final String fragmentShader) {
        this(new DefaultShader.Config(vertexShader, fragmentShader));
    }

    public DefaultShaderProvider (final FileHandle vertexShader, final FileHandle fragmentShader) {
        this(vertexShader.readString(), fragmentShader.readString());
    }

    public DefaultShaderProvider () {
        this(null);
    }

    @Override
    protected Shader createShader (final Renderable renderable) {
        return new DefaultShader(renderable, config);
    }
}
