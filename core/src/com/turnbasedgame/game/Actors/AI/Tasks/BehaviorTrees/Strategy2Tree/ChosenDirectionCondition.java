package com.turnbasedgame.game.Actors.AI.Tasks.BehaviorTrees.Strategy2Tree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Actors.Actors;
import com.turnbasedgame.game.Utilities.Console;

/**
 * Created by Boris on 29.02.2016.
 * Project: TurnBasedGame1.0
 */
public class ChosenDirectionCondition extends LeafTask<AI> implements InformableTaskInterface {
    String entityFullName;
    int direction;

    @Override
    public void informExecuted() {
        Console.addLine("ai", "Checking if " + entityFullName + " already chosen direction ...", Console.LineType.REGULAR);
    }

    @Override
    public void informSucceeded() {
        Console.addLine("ai", entityFullName + "'s direction: " + direction, Console.LineType.SUCCESS);
    }

    @Override
    public void informFailed() {
        Console.addLine("ai", entityFullName + " doesn't have direction yet", Console.LineType.ERROR);
    }

    @Override
    public Status execute() {
        entityFullName = Actors.gameAI.strategy2entityName;
        informExecuted();
        direction = Actors.gameAI.strategy2direction;
        if (direction == -1) {
            informFailed();
            return Status.FAILED;
        }else if (direction < 0 || direction > 3) {
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
