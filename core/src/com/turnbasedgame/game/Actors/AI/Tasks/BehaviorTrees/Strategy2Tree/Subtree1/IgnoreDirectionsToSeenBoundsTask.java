package com.turnbasedgame.game.Actors.AI.Tasks.BehaviorTrees.Strategy2Tree.Subtree1;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Actors.Actors;
import com.turnbasedgame.game.Actors.Entity.Entity;
import com.turnbasedgame.game.Actors.Grid.Grid;
import com.turnbasedgame.game.Utilities.Console;

/**
 * Created by Boris on 19.03.2016.
 * Project: TurnBasedGame1.0
 */
public class IgnoreDirectionsToSeenBoundsTask extends LeafTask<AI> implements InformableTaskInterface {
    String entityFullName;
    int excludedDirection;

    @Override
    public void informExecuted() {

    }

    @Override
    public void informSucceeded() {
        Console.addLine("ai", "Excluded direction: " + excludedDirection + " for " + entityFullName, Console.LineType.REGULAR);
    }

    @Override
    public void informFailed() {

    }

    @Override
    public Status execute() {
        entityFullName = Actors.gameAI.strategy2entityName;
        excludeDirection();
        return Status.SUCCEEDED;
    }

    void excludeDirection(){
        if (Entity.getEntity(entityFullName).getGridCoordinates().x + Entity.getEntity(entityFullName).getSightRange() > Grid.size.x){
            Actors.gameAI.excludedDirections[0] = true;
            excludedDirection = 0;
            informSucceeded();
        }
        if (Entity.getEntity(entityFullName).getGridCoordinates().x - Entity.getEntity(entityFullName).getSightRange() < 0) {
            Actors.gameAI.excludedDirections[2] = true;
            excludedDirection = 2;
            informSucceeded();
        }
        if (Entity.getEntity(entityFullName).getGridCoordinates().z + Entity.getEntity(entityFullName).getSightRange() > Grid.size.z) {
            Actors.gameAI.excludedDirections[1] = true;
            excludedDirection = 1;
            informSucceeded();
        }
        if (Entity.getEntity(entityFullName).getGridCoordinates().z - Entity.getEntity(entityFullName).getSightRange() < 0) {
            Actors.gameAI.excludedDirections[3] = true;
            excludedDirection = 3;
            informSucceeded();
        }
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
