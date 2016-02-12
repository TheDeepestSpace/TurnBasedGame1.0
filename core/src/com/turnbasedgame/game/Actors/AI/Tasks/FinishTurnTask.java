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
public class FinishTurnTask extends LeafTask<AI> implements InromablreTaskInterface{
    @Override
    public Status execute() {
        Game.finishTurn();

        informExecuted();
        informSucceeded();

        return Status.SUCCEEDED;
    }

    @Override
    public void informExecuted() {
        Console.addLine("ai", "AI invoked Task: 'finishTurn'. Proceeding ...", Console.LineType.REGULAR);
    }

    @Override
    public void informSucceeded() {
        Console.addLine("ai", "AI finished Task: 'finishTurn' with status: 'SUCCEEDED' ", Console.LineType.SUCCESS);
    }

    @Override
    public void informFailed() {

    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
