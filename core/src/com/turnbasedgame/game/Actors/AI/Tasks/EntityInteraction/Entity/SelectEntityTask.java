package com.turnbasedgame.game.Actors.AI.Tasks.EntityInteraction.Entity;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Actors.Entity.Entity;
import com.turnbasedgame.game.Utilities.Console;

/**
 * Created by Boris on 13.02.2016.
 * Project: TurnBasedGame1.0
 */
public class SelectEntityTask extends LeafTask<AI> implements InformableTaskInterface {
    @TaskAttribute (required = false)
    String entityFullName;

    String failReason;

    @Override
    public void informExecuted() {

    }

    @Override
    public void informSucceeded() {
        Console.addLine("ai", "Selected " + entityFullName, Console.LineType.WARNING);
    }

    @Override
    public void informFailed() {
        Console.addLine("ai", "Could not select entity. Reason: " + failReason, Console.LineType.ERROR);
    }

    @Override
    public Status execute() {
        if (entityFullName != null && entityFullName.equals(" ")) {
            Entity.getEntity(entityFullName).select(true);
            informSucceeded();
            return Status.SUCCEEDED;
        } else {
            failReason = "entity name is NULL";
            informFailed();
            return Status.FAILED;
        }
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
