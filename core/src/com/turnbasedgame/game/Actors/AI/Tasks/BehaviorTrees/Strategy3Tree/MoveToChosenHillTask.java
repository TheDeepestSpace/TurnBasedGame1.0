package com.turnbasedgame.game.Actors.AI.Tasks.BehaviorTrees.Strategy3Tree;

import com.badlogic.gdx.ai.btree.Task;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.EntityInteraction.MovingEntity.MoveTask;
import com.turnbasedgame.game.Actors.Actors;
import com.turnbasedgame.game.Actors.Entity.Entity;

/**
 * Created by Boris on 20.04.2016.
 * Project: TurnBasedGame1.0
 */
public class MoveToChosenHillTask extends MoveTask{

    @Override
    public Status execute() {
        if (this.fresh) {
            entityFullName = Actors.gameAI.strategy3entityName;
            lastGridCoordinates = Entity.getEntity(entityFullName).getGridCoordinates();
            targetNodeGridCoordinates = Actors.gameAI.hillGridCoordinates;
        }

        if (super.execute() == Status.SUCCEEDED) {
            fresh = true;
            return Status.SUCCEEDED;
        }else {
            fresh = false;
            return Status.FAILED;
        }
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
