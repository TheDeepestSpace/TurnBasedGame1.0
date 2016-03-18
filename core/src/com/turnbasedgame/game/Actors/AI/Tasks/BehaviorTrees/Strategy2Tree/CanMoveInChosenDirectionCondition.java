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
 * Created by Boris on 15.03.2016.
 * Project: TurnBasedGame1.0
 */
public class CanMoveInChosenDirectionCondition extends LeafTask<AI> implements InformableTaskInterface{
    String entityFullName;
    int chosenDirection;

    @Override
    public void informExecuted() {
        Console.addLine("ai", "Checking if " + entityFullName + " can move in its direction - " + chosenDirection + " ...", Console.LineType.REGULAR);
    }

    @Override
    public void informSucceeded() {

    }

    @Override
    public void informFailed() {
        Console.addLine("ai", entityFullName + " can not move in its direction!", Console.LineType.ERROR);
    }

    @Override
    public Status execute() {
        entityFullName = Actors.gameAI.strategy2entityName;
        chosenDirection = Actors.gameAI.strategy2direction;

        informExecuted();

        if (nextTileOutOfBounds() || nextTileBlocked()) {
            informFailed();
            return Status.FAILED;
        }

        return Status.SUCCEEDED;
    }

    boolean nextTileBlocked() {
        return chosenDirection == 1 && Grid.getNode(
                Entity.getEntity(entityFullName).getGridCoordinates().x + 1,
                Entity.getEntity(entityFullName).getGridCoordinates().y,
                Entity.getEntity(entityFullName).getGridCoordinates().z
        ).type == GridNodeType.BLOCK
                || chosenDirection == 3 && Grid.getNode(
                Entity.getEntity(entityFullName).getGridCoordinates().x - 1,
                Entity.getEntity(entityFullName).getGridCoordinates().y,
                Entity.getEntity(entityFullName).getGridCoordinates().z
        ).type == GridNodeType.BLOCK
                || chosenDirection == 2 && Grid.getNode(
                Entity.getEntity(entityFullName).getGridCoordinates().x,
                Entity.getEntity(entityFullName).getGridCoordinates().y,
                Entity.getEntity(entityFullName).getGridCoordinates().z + 1
        ).type == GridNodeType.BLOCK
                || chosenDirection == 4 && Grid.getNode(
                Entity.getEntity(entityFullName).getGridCoordinates().x,
                Entity.getEntity(entityFullName).getGridCoordinates().y,
                Entity.getEntity(entityFullName).getGridCoordinates().z - 1
        ).type == GridNodeType.BLOCK;
    }

    boolean nextTileOutOfBounds() {
        return chosenDirection == 1 && Entity.getEntity(entityFullName).getGridCoordinates().x + 1 >= Grid.size.x ||
                chosenDirection == 3 && Entity.getEntity(entityFullName).getGridCoordinates().x - 1 < 0 ||
                chosenDirection == 2 && Entity.getEntity(entityFullName).getGridCoordinates().z + 1 >= Grid.size.z ||
                chosenDirection == 4 && Entity.getEntity(entityFullName).getGridCoordinates().z - 1 < 0;
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
