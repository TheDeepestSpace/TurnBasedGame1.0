package com.turnbasedgame.game.Actors.AI.Tasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Utilities.Console;
import com.turnbasedgame.game.Utilities.Game;

/**
 * Created by Boris on 11.02.2016.
 * Project: TurnBasedGame1.0
 */
public class FinishTurnTask extends LeafTask<AI> implements InformableTaskInterface {
    @Override
    public Status execute() {
        Console.addLine("ai", "----------------------------------------------", Console.LineType.ERROR);
        Game.finishTurn();

        return Status.SUCCEEDED;
    }

    @Override
    public void informExecuted() {

    }

    @Override
    public void informSucceeded() {

    }

    @Override
    public void informFailed() {

    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
