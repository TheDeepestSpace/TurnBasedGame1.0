package com.turnbasedgame.game.Actors.AI.Tasks.EntityInteraction.Entity;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Actors.Entity.Entity;
import com.turnbasedgame.game.Utilities.Console;

/**
 * Created by Boris on 14.02.2016.
 * Project: TurnBasedGame1.0
 */
public class DeselectEntityTask extends LeafTask<AI> implements InformableTaskInterface {
    String failReason;
    String deselectedEntityFullName;

    @Override
    public void informExecuted() {

    }

    @Override
    public void informSucceeded() {
        Console.addLine("ai", "Deselected " + deselectedEntityFullName, Console.LineType.WARNING);
    }

    @Override
    public void informFailed() {
        Console.addLine("ai", "Could not deselect. Reason: " + failReason, Console.LineType.ERROR);
    }

    @Override
    public Status execute() {
        for (int i = 0; i < Entity.list.size(); i++) {
            if (Entity.list.get(i).isSelected()) {
                Entity.list.get(i).deselect(true);
                informSucceeded();
                return Status.SUCCEEDED;
            }
        }

        failReason = "no selected entities";
        informFailed();
        return Status.FAILED;
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
