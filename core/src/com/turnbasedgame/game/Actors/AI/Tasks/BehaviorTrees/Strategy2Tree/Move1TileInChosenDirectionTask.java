package com.turnbasedgame.game.Actors.AI.Tasks.BehaviorTrees.Strategy2Tree;

import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector3;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.EntityInteraction.MovingEntity.MoveTask;
import com.turnbasedgame.game.Actors.Actors;
import com.turnbasedgame.game.Actors.Entity.Entity;

/**
 * Created by Boris on 01.03.2016.
 * Project: TurnBasedGame1.0
 */
public class Move1TileInChosenDirectionTask extends MoveTask {
    @Override
    public Status execute() {
        if (this.fresh) {
            entityFullName = Actors.gameAI.strategy2entityName;
            lastGridCoordinates = Entity.getEntity(entityFullName).getGridCoordinates();
            targetNodeGridCoordinates = getNewGridCoordinates();
        }

        if (super.execute() == Status.SUCCEEDED) {
            fresh = true;
            return Status.SUCCEEDED;
        }else {
            fresh = false;
            return Status.FAILED;
        }
    }

    Vector3  getNewGridCoordinates() {
        int dir = getDirection();

        if (dir == 0) return new Vector3(lastGridCoordinates.x + 1, lastGridCoordinates.y, lastGridCoordinates.z);
        else if (dir == 2) return new Vector3(lastGridCoordinates.x - 1, lastGridCoordinates.y, lastGridCoordinates.z);
        else if (dir == 1) return new Vector3(lastGridCoordinates.x, lastGridCoordinates.y, lastGridCoordinates.z + 1);
        else if (dir == 3) return new Vector3(lastGridCoordinates.x, lastGridCoordinates.y, lastGridCoordinates.z - 1);
        else return null;
    }

    int getDirection() {
        return Actors.gameAI.strategy2direction;
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
