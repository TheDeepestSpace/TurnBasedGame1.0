package com.turnbasedgame.game.Actors.User;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.collision.Ray;
import com.turnbasedgame.game.Actors.Camera;
import com.turnbasedgame.game.Actors.Entity.AttackingEntity;
import com.turnbasedgame.game.Actors.Entity.Entity;
import com.turnbasedgame.game.Utilities.Game;

/**
 * Created by Boris on 13.02.2016.
 * Project: TurnBasedGame1.0
 */
public class User {
    public static boolean interactedWithEntity = false;

    public static boolean selectingEntity = true;
    public static boolean selectingEntityToAttack = false;

    public static void actOnTouchUp(int screenX, int screenY) {
        if (selectingEntity) {
            pickEntity(screenX, screenY);

            if (!pickedEntityName.equals("n/a")) {
                if (selectingEntityToAttack) {
                    ((AttackingEntity) Entity.getSelectedEntity()).attack(pickedEntityName, false);
                }else {
                    deselectEntities();
                    Entity.getEntity(pickedEntityName).select(false);
                }
            }else {
                deselectEntities();
                selectingEntityToAttack = false;
            }
        }
    }

    /** SELECTING ENTITY */

    static String pickedEntityName;
    static void pickEntity(int screenX, int screenY) {
        Ray ray = Camera.camera.getPickRay(screenX, screenY);

        for (int i = 0; i < Entity.list.size(); i++) {
            if (Intersector.intersectRayBoundsFast(ray, Entity.list.get(i).getModelInstance().bounds)) {
                pickedEntityName = Entity.list.get(i).getFullName();
                return;
            }
        }

        pickedEntityName = "n/a";
    }

    /** SELECTING ENTITY TO ATTACK */

    static void deselectEntities() {
        for (int i = 0; i < Entity.list.size(); i++) {
            Entity.list.get(i).deselect(false);
        }
    }

    /** GAME */

    public static void finishTurn() {
        if (interactedWithEntity) {
            Game.finishTurn();
            interactedWithEntity = false;
        }
    }


}
