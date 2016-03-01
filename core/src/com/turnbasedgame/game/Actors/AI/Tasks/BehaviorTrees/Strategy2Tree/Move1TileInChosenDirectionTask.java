package com.turnbasedgame.game.Actors.AI.Tasks.BehaviorTrees.Strategy2Tree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector3;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Actors.Actors;
import com.turnbasedgame.game.Actors.Entity.Entity;
import com.turnbasedgame.game.Actors.Entity.MovingEntity;
import com.turnbasedgame.game.Utilities.Console;

/**
 * Created by Boris on 01.03.2016.
 * Project: TurnBasedGame1.0
 */
public class Move1TileInChosenDirectionTask extends LeafTask<AI> implements InformableTaskInterface {
    String entityFullName;
    Vector3 lastGidCoordinates;
    Vector3 newGridCoordinates;
    @Override
    public void informExecuted() {
        Console.addLine("ai", "Moving " + entityFullName + " in chosen direction ...", Console.LineType.REGULAR);
    }

    @Override
    public void informSucceeded() {
        Console.addLine("ai", "Moved " + entityFullName + ": " + lastGidCoordinates + " -> " + newGridCoordinates, Console.LineType.SUCCESS);
    }

    @Override
    public void informFailed() {
        Console.addLine("ai", "Failed to move entity", Console.LineType.ERROR);
    }

    @Override
    public Status execute() {
        entityFullName = Actors.gameAI.strategy2entityName;
        informExecuted();
        lastGidCoordinates = Entity.getEntity(entityFullName).getGridCoordinates();
        newGridCoordinates = getNewGridCoordinates();
        ((MovingEntity) Entity.getEntity(entityFullName)).move(newGridCoordinates, true);
        informSucceeded();
        // TODO implement MoveTask class!!!
        // TODO add 'failed' situation!!!
        return Status.SUCCEEDED;
    }

    Vector3 getNewGridCoordinates() {
        int dir = getDirection();

        if (dir == 1) return new Vector3(lastGidCoordinates.x + 1, lastGidCoordinates.y, lastGidCoordinates.z);
        else if (dir == 3) return new Vector3(lastGidCoordinates.x - 1, lastGidCoordinates.y, lastGidCoordinates.z);
        else if (dir == 2) return new Vector3(lastGidCoordinates.x, lastGidCoordinates.y, lastGidCoordinates.z + 1);
        else if (dir == 4) return new Vector3(lastGidCoordinates.x, lastGidCoordinates.y, lastGidCoordinates.z - 1);
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
