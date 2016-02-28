package com.turnbasedgame.game.Actors.User;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.turnbasedgame.game.Actors.Camera;
import com.turnbasedgame.game.Actors.Entity.AttackingEntity;
import com.turnbasedgame.game.Actors.Entity.Entity;
import com.turnbasedgame.game.Actors.Entity.MovingEntity;
import com.turnbasedgame.game.Actors.Grid.Grid;
import com.turnbasedgame.game.Actors.Grid.GridNodeType;
import com.turnbasedgame.game.Utilities.Game;

/**
 * Created by Boris on 13.02.2016.
 * Project: TurnBasedGame1.0
 */
public class User {
    public static boolean interactedWithEntity = false;

    static boolean selectingEntity = true;
    static boolean selectingEntityToAttack = false;
    static boolean selectingNode = false;
    static boolean selectingNodeToMoveTo = false;

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
                if (selectingEntityToAttack) {
                    User.selectingEntity();
                    Entity.getSelectedEntity().getPhases().get(1).escape();
                }
                else deselectEntities();
            }
        }
        if (selectingNode) {
            pickNode(screenX, screenY);

            if (pickedNodeCoordinates.x != -1) {
                if (selectingNodeToMoveTo) {
                    ((MovingEntity) Entity.getSelectedEntity()).move(pickedNodeCoordinates, false);
                }
            }else {
                if (selectingNodeToMoveTo) {
                    User.selectingEntity();
                    Entity.getSelectedEntity().getPhases().get(2).escape();
                }
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

    /** SELECTING NODE */

    static Vector3 pickedNodeCoordinates = new Vector3(-1, -1, -1);
    static void pickNode(int screenX, int screenY) {
        Ray ray = Camera.camera.getPickRay(screenX, screenY);

        for (int x = 0; x < Grid.size.x; x++) {
            for (int y = 0; y < Grid.size.y; y++) {
                for (int z = 0; z < Grid.size.z; z++) {
                    if (Intersector.intersectRayBoundsFast(ray, Grid.getNode(x, y, z).bounds)
                            && Grid.getNode(x, y, z).type != GridNodeType.BLOCK) {
                        pickedNodeCoordinates.set(x, y, z);
                        return;
                    }
                }
            }
        }

        pickedNodeCoordinates.set(-1, -1, -1);
    }

    /** SWITCHERS */

    public static void selectingEntity() {
        selectingEntity = true;
        selectingEntityToAttack = false;
        selectingNode = false;
        selectingNodeToMoveTo = false;
    }

    public static void selectingEntityToAttack() {
        selectingEntity = true;
        selectingEntityToAttack = true;
        selectingNode = false;
        selectingNodeToMoveTo = false;
    }

    public static void selectingNode() {
        selectingEntity = false;
        selectingEntityToAttack = false;
        selectingNode = true;
        selectingNodeToMoveTo = false;
    }

    public static void selectingNodeToMoveTo() {
        selectingEntity = false;
        selectingEntityToAttack = false;
        selectingNode = true;
        selectingNodeToMoveTo = true;
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
