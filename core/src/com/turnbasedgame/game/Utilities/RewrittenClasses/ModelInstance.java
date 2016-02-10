package com.turnbasedgame.game.Utilities.RewrittenClasses;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.collision.BoundingBox;

/**
 * Created by Boris on 10.02.2016.
 * Project: TurnBasedGame1.0
 */
public class ModelInstance extends com.badlogic.gdx.graphics.g3d.ModelInstance{
    public final BoundingBox bounds = new BoundingBox();

    public ModelInstance(Model model) {
        super(model);
    }
}
