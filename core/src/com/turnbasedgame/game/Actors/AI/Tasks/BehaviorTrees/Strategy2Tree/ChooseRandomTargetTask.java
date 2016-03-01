package com.turnbasedgame.game.Actors.AI.Tasks.BehaviorTrees.Strategy2Tree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Actors.Actors;
import com.turnbasedgame.game.Actors.Entity.Entity;
import com.turnbasedgame.game.Utilities.Console;

import java.util.Random;

/**
 * Created by Boris on 28.02.2016.
 * Project: TurnBasedGame1.0
 */
public class ChooseRandomTargetTask extends LeafTask<AI> implements InformableTaskInterface{
    Random random = new Random(System.nanoTime());
    String entityFullName = "n/a";

    @Override
    public void informExecuted() {
        Console.addLine("ai", "AI choosing random entity for strategy 2 ...", Console.LineType.REGULAR);
    }

    @Override
    public void informSucceeded() {
        Console.addLine("ai", "Ai chosen " + entityFullName, Console.LineType.SUCCESS);
    }

    @Override
    public void informFailed() {
        Console.addLine("ai", "entity for strategy 2 already chosen: " + entityFullName, Console.LineType.ERROR);
    }

    @Override
    public Status execute() {
        informExecuted();
        if (entityFullName.equals("n/a") || entityFullName == null) {
            int entityListIndex = random.nextInt(Entity.aiList.size());
            entityFullName = Entity.aiList.get(entityListIndex).getFullName();
            Actors.gameAI.strategy2entityName = entityFullName;
            informSucceeded();
        } else {
            informFailed();
        }

        return Status.SUCCEEDED;
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
