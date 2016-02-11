package com.turnbasedgame.game.Utilities.Rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.turnbasedgame.game.Actors.Camera;
import com.turnbasedgame.game.Global;
import com.turnbasedgame.game.TurnBasedGame;
import com.turnbasedgame.game.Utilities.RewrittenClasses.ModelInstance;

/**
 * Created by Boris on 10.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Renderer {
    /** CLEARING SCREEN */

    public static void clearScreen() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
        Gdx.gl20.glBlendEquation(GL20.GL_BLEND);
    }

    /** FONT RENDERING */

    public static void renderTextLine(String text, Vector2 position, Color textColor){
        Global.spriteBatch.begin();
        Global.defaultFont.setColor(textColor);
        Global.defaultFont.draw(Global.spriteBatch, text, position.x, position.y);
        Global.spriteBatch.end();
    }

    /** MODEL INSTANCE RENDERING */

    public static void renderModelInstance(ModelInstance modelInstance) {
        if (visible(modelInstance)) {
            TurnBasedGame.gameScreen.modelBatch.begin(Camera.camera);
            TurnBasedGame.gameScreen.modelBatch.render(modelInstance, TurnBasedGame.gameScreen.environment);
            TurnBasedGame.gameScreen.modelBatch.end();
            TurnBasedGame.gameScreen.modelBatch.flush();
        }
    }

    public static void renderModelInstanceShadow(ModelInstance modelInstance) {
        if (visible(modelInstance)) {
            TurnBasedGame.gameScreen.shadowBatch.render(modelInstance);
        }
    }

    /** GETTERS */

    public static boolean visible(ModelInstance modelInstance) {
        return Camera.camera.frustum.boundsInFrustum(modelInstance.bounds);
    }
}
