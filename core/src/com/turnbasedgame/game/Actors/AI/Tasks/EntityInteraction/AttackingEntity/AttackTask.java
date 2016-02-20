package com.turnbasedgame.game.Actors.AI.Tasks.EntityInteraction.AttackingEntity;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Actors.Entity.AttackingEntity;
import com.turnbasedgame.game.Actors.Entity.Entity;
import com.turnbasedgame.game.Utilities.Console;

/**
 * Created by Boris on 16.02.2016.
 * Project: TurnBasedGame1.0
 */
public class AttackTask extends LeafTask<AI> implements InformableTaskInterface {
    @TaskAttribute (required = true)
    public String attackerFullName;

    @TaskAttribute (required = true)
    public String targetFullName;

    public String failReason;

    @Override
    public void informExecuted() {
        Console.addLine("ai", "AI entered attack task ... ", Console.LineType.REGULAR);
    }

    @Override
    public void informSucceeded() {
        Console.addLine("ai", attackerFullName + " attacked " + targetFullName, Console.LineType.SUCCESS);
    }

    @Override
    public void informFailed() {
        Console.addLine("ai", "Could not attack. Reason: " + failReason, Console.LineType.ERROR);
    }

    @Override
    public Status execute() {
        informExecuted();
        if (attackerFullName.equals(" ") || attackerFullName == null) {
            failReason = "attacker's name is not valid";
            informFailed();
            return Status.FAILED;
        }else if (targetFullName.equals(" ") || targetFullName == null) {
            failReason = "target's name is not valid";
            informFailed();
            return Status.FAILED;
        }else if (!(Entity.getEntity(attackerFullName) instanceof AttackingEntity)
                || !((AttackingEntity) Entity.getEntity(attackerFullName)).canAttack(targetFullName)) {
            failReason = "attacker can not attack";
            informFailed();
            return Status.FAILED;
        }else {
            ((AttackingEntity) Entity.getEntity(attackerFullName)).attack(targetFullName);
            informSucceeded();
            return Status.SUCCEEDED;
        }
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
