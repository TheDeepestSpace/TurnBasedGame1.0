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

import java.util.Random;

/**
 * Created by Boris on 29.02.2016.
 * Project: TurnBasedGame1.0
 */
public class ChooseDirectionTask extends LeafTask<AI> implements InformableTaskInterface{
    String entityFullName;
    int chosenDirection;
    Random random = new Random(System.nanoTime());

    @Override
    public void informExecuted() {
        Console.addLine("ai", "Choosing direction for " + entityFullName + " ...", Console.LineType.REGULAR);
    }

    @Override
    public void informSucceeded() {
        Console.addLine("ai", "Chosen direction for " + entityFullName + ": " + chosenDirection, Console.LineType.SUCCESS);
    }

    @Override
    public void informFailed() {

    }

    @Override
    public Status execute() {
        entityFullName = Actors.gameAI.strategy2entityName;
        informExecuted();
        chosenDirection = getRandomDirection();
        Actors.gameAI.strategy2direction = chosenDirection;
        informSucceeded();
        return Status.SUCCEEDED;
    }

    int getRandomDirection() {
        return random.nextInt(4) + 1;
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
