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
public class EnemyExistsCondition extends LeafTask<AI> implements InformableTaskInterface {
    @Override
    public void informExecuted() {
        Console.addLine("ai", "Checking if there are any enemies ... ", Console.LineType.REGULAR);
    }

    @Override
    public void informSucceeded() {

    }

    @Override
    public void informFailed() {
        Console.addLine("ai", "No enemies found!", Console.LineType.ERROR);
    }

    @Override
    public Status execute() {
        informExecuted();

        if (Entity.userList.size() == 0) {
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
