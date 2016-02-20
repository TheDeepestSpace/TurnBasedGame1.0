package com.turnbasedgame.game.Actors.AI.Tasks.BehaviorTrees.Strategy1Tree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.EntityInteraction.AttackingEntity.AttackTask;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Actors.Actors;

/**
 * Created by Boris on 20.02.2016.
 * Project: TurnBasedGame1.0
 */
public class AttackClosestEnemyTask extends LeafTask<AI> implements InformableTaskInterface{
    AttackTask attackTask = new AttackTask();

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
    public Status execute() {
        attackTask.attackerFullName = Actors.gameAI.strategy1attackerName;
        attackTask.targetFullName = Actors.gameAI.strategy1targetName;
        return attackTask.execute();
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
