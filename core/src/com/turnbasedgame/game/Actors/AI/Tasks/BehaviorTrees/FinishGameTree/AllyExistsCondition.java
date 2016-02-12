package com.turnbasedgame.game.Actors.AI.Tasks.BehaviorTrees.FinishGameTree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Actors.Entity.Entity;
import com.turnbasedgame.game.Utilities.Console;

/**
 * Created by Boris on 12.02.2016.
 * Project: TurnBasedGame1.0
 */
public class AllyExistsCondition extends LeafTask<AI> implements InformableTaskInterface {
    @Override
    public void informExecuted() {
        Console.addLine("ai", "Checking if there any allies ...", Console.LineType.REGULAR);
    }

    @Override
    public void informSucceeded() {
        Console.addLine("ai", "Ally exists!", Console.LineType.SUCCESS);
    }

    @Override
    public void informFailed() {
        Console.addLine("ai", "No allies found!", Console.LineType.ERROR);
    }

    @Override
    public Status execute() {
        informExecuted();

        if (Entity.aiList.size() == 0) {
            informFailed();
            return Status.FAILED;
        }else {
            informSucceeded();
            return Status.SUCCEEDED;
        }
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
