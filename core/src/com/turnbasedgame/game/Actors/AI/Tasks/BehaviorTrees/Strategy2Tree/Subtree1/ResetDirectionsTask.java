package com.turnbasedgame.game.Actors.AI.Tasks.BehaviorTrees.Strategy2Tree.Subtree1;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Actors.Actors;
import com.turnbasedgame.game.Utilities.Console;

/**
 * Created by Boris on 26.03.2016.
 * Project: TurnBasedGame1.0
 */
public class ResetDirectionsTask extends LeafTask<AI> implements InformableTaskInterface {
    @Override
    public void informExecuted() {
        Console.addLine("ai", "Resetting directions ... ", Console.LineType.REGULAR);
    }

    @Override
    public void informSucceeded() {

    }

    @Override
    public void informFailed() {

    }

    @Override
    public Status execute() {
        informExecuted();
        Actors.gameAI.excludedDirections[0] = false;
        Actors.gameAI.excludedDirections[1] = false;
        Actors.gameAI.excludedDirections[2] = false;
        Actors.gameAI.excludedDirections[3] = false;
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
