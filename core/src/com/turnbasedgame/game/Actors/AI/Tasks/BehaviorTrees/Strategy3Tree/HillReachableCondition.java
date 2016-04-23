package com.turnbasedgame.game.Actors.AI.Tasks.BehaviorTrees.Strategy3Tree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector3;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Actors.Actors;
import com.turnbasedgame.game.Actors.Entity.Entity;
import com.turnbasedgame.game.Actors.Grid.Grid;
import com.turnbasedgame.game.Utilities.Console;

/**
 * Created by Boris on 20.04.2016.
 * Project: TurnBasedGame1.0
 */
public class HillReachableCondition extends LeafTask<AI> implements InformableTaskInterface {
    String entityFullName;
    Vector3 hillGridCoordinates = new Vector3();
    @Override
    public void informExecuted() {
        Console.addLine("ai", "Checking if chosen hill is reachable ...", Console.LineType.REGULAR);
    }

    @Override
    public void informSucceeded() {
        Console.addLine("ai", "Hill at " + hillGridCoordinates + " is reachable by " + entityFullName, Console.LineType.SUCCESS);
    }

    @Override
    public void informFailed() {
        Console.addLine("ai", "Hill at" + hillGridCoordinates + " cannot be reached by " + entityFullName, Console.LineType.ERROR);
    }

    @Override
    public Status execute() {
        informExecuted();
        entityFullName = Actors.gameAI.strategy3entityName;
        hillGridCoordinates = Actors.gameAI.hillGridCoordinates.cpy();
        if (reachable()) {
            informSucceeded();
            return Status.SUCCEEDED;
        }else {
            informFailed();
            Actors.gameAI.seenHills.remove(hillGridCoordinates);
            Actors.gameAI.hills.remove(hillGridCoordinates);
            return Status.FAILED;
        }
    }

    boolean reachable() {
        Grid.setStart(Entity.getEntity(entityFullName).getGridCoordinates());
        Grid.setEnd(hillGridCoordinates);
        return Grid.findPath() == 1;
    }


    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
