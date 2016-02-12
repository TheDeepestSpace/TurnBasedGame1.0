package com.turnbasedgame.game.Actors.AI.Tasks.BehaviorTrees.FinishGameTree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Utilities.Console;
import com.turnbasedgame.game.Utilities.Game;

/**
 * Created by Boris on 12.02.2016.
 * Project: TurnBasedGame1.0
 */
public class FinishGameTask extends LeafTask<AI> implements InformableTaskInterface {
    @TaskAttribute(required = true)
    public String reason;

    @Override
    public void informExecuted() {
        Console.addLine("ai", "Finishing game ... ", Console.LineType.WARNING);
    }

    @Override
    public void informSucceeded() {
        Console.addLine("ai", "Finished game! Reason: " + reason, Console.LineType.ERROR);
        Console.addLine("gameConsole", "Reason: " + reason, Console.LineType.ERROR);
    }

    @Override
    public void informFailed() {

    }

    @Override
    public Status execute() {
        informExecuted();
        Game.finish();
        informSucceeded();

        return Status.SUCCEEDED;
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
