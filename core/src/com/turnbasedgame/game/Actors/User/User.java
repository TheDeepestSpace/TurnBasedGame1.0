package com.turnbasedgame.game.Actors.User;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.collision.Ray;
import com.turnbasedgame.game.Actors.Camera;
import com.turnbasedgame.game.Actors.Entity.Entity;

/**
 * Created by Boris on 13.02.2016.
 * Project: TurnBasedGame1.0
 */
public class User {

    public static void selectEntity(int screenX, int screenY) {
        Ray ray = Camera.camera.getPickRay(screenX, screenY);

        for (int i = 0; i < Entity.list.size(); i++) {
            if (Intersector.intersectRayBoundsFast(ray, Entity.list.get(i).getModelInstance().bounds)) {
                if (!Entity.list.get(i).isSelected()) {
                    deselectEntity();

                    Entity.list.get(i).select(false);

                    return;
                }
            }
        }

        deselectEntity();
    }

    static void deselectEntity() {
        for (int i = 0; i < Entity.list.size(); i++) {
            if (Entity.list.get(i).isSelected()) {
                Entity.list.get(i).deselect(false);
            }
        }
    }
}
