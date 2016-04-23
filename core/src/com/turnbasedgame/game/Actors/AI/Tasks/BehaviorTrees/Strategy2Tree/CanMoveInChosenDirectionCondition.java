package com.turnbasedgame.game.Actors.AI.Tasks.BehaviorTrees.Strategy2Tree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Actors.Actors;
import com.turnbasedgame.game.Utilities.Console;

/**
 * Created by Boris on 15.03.2016.
 * Project: TurnBasedGame1.0
 */
public class CanMoveInChosenDirectionCondition extends LeafTask<AI> implements InformableTaskInterface{
    String entityFullName;
    int chosenDirection;
    String failReason;

    @Override
    public void informExecuted() {
        Console.addLine("ai", "Checking if " + entityFullName + " can move in its direction - " + chosenDirection + " ...", Console.LineType.REGULAR);
    }

    @Override
    public void informSucceeded() {

    }

    @Override
    public void informFailed() {
        Console.addLine("ai", entityFullName + " can not move in " + chosenDirection + "direction! Reason: " + failReason, Console.LineType.ERROR);
    }

    @Override
    public Status execute() {
        entityFullName = Actors.gameAI.strategy2entityName;
        chosenDirection = Actors.gameAI.strategy2direction;

        informExecuted();

        if (noDirectionsAvailable()) {
            failReason = "no direction available";
            informFailed();
            return Status.FAILED;
        }
        if (tileExcluded()) {
            failReason = "chosen direction excluded";
            informFailed();
            return Status.FAILED;
        }

        return Status.SUCCEEDED;
    }

    boolean noDirectionsAvailable(){
        return Actors.gameAI.excludedDirections[0] &&
                Actors.gameAI.excludedDirections[1] &&
                Actors.gameAI.excludedDirections[2] &&
                Actors.gameAI.excludedDirections[3];
    }

    boolean tileExcluded() {
        return chosenDirection == 0 && Actors.gameAI.excludedDirections[0] ||
                chosenDirection == 2 && Actors.gameAI.excludedDirections[2] ||
                chosenDirection == 1 && Actors.gameAI.excludedDirections[1] ||
                chosenDirection == 3 && Actors.gameAI.excludedDirections[3];
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
