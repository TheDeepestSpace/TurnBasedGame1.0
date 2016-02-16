package com.turnbasedgame.game.Actors.AI.Tasks.EntityInteraction.Entity;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Actors.Entity.Entity;
import com.turnbasedgame.game.Utilities.Console;

/**
 * Created by Boris on 16.02.2016.
 * Project: TurnBasedGame1.0
 */
public class KillEntityTask extends LeafTask<AI> implements InformableTaskInterface{
    @TaskAttribute (required = true)
    String entityFullName;

    String failReason;

    @Override
    public void informExecuted() {

    }

    @Override
    public void informSucceeded() {
        Console.addLine("ai", "AI successfully removed " + entityFullName, Console.LineType.SUCCESS);
    }

    @Override
    public void informFailed() {
        Console.addLine("ai", "AI could not delete entity. Reason: " + failReason, Console.LineType.ERROR);
    }

    @Override
    public Status execute() {
        if (entityFullName.equals(" ") || entityFullName == null) {
            failReason = "Entity name is invalid";
            informFailed();
            return Status.FAILED;
        }else {
            Entity.getEntity(entityFullName).die(true);
            informSucceeded();
            return Status.SUCCEEDED;
        }
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
