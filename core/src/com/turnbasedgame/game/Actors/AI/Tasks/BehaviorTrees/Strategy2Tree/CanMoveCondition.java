package com.turnbasedgame.game.Actors.AI.Tasks.BehaviorTrees.Strategy2Tree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Actors.Actors;
import com.turnbasedgame.game.Actors.Entity.Entity;
import com.turnbasedgame.game.Actors.Grid.Grid;
import com.turnbasedgame.game.Actors.Grid.GridNodeType;
import com.turnbasedgame.game.Utilities.Console;

/**
 * Created by Boris on 28.02.2016.
 * Project: TurnBasedGame1.0
 */
public class CanMoveCondition extends LeafTask<AI> implements InformableTaskInterface{
    String entityFullName;

    @Override
    public void informExecuted() {
        Console.addLine("ai", "Checking if " + entityFullName + " selected for strategy 2 can move ... ", Console.LineType.REGULAR);
    }

    @Override
    public void informSucceeded() {

    }

    @Override
    public void informFailed() {
        Console.addLine("ai", entityFullName + " cannot be moved", Console.LineType.ERROR);
    }

    @Override
    public Status execute() {
        entityFullName = Actors.gameAI.strategy2entityName;
        informExecuted();
        if (
                Grid.getNode(
                        Entity.getEntity(entityFullName).getGridCoordinates().x + 1,
                        Entity.getEntity(entityFullName).getGridCoordinates().y,
                        Entity.getEntity(entityFullName).getGridCoordinates().z
                ).type != GridNodeType.BLOCK
                || Grid.getNode(
                        Entity.getEntity(entityFullName).getGridCoordinates().x - 1,
                        Entity.getEntity(entityFullName).getGridCoordinates().y,
                        Entity.getEntity(entityFullName).getGridCoordinates().z
                ).type != GridNodeType.BLOCK
                || Grid.getNode(
                        Entity.getEntity(entityFullName).getGridCoordinates().x,
                        Entity.getEntity(entityFullName).getGridCoordinates().y,
                        Entity.getEntity(entityFullName).getGridCoordinates().z + 1
                ).type != GridNodeType.BLOCK
                || Grid.getNode(
                        Entity.getEntity(entityFullName).getGridCoordinates().x,
                        Entity.getEntity(entityFullName).getGridCoordinates().y,
                        Entity.getEntity(entityFullName).getGridCoordinates().z - 1
                ).type != GridNodeType.BLOCK
                ) {
            return Status.SUCCEEDED;
        }else {
            informFailed();
            return Status.FAILED;
        }
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
