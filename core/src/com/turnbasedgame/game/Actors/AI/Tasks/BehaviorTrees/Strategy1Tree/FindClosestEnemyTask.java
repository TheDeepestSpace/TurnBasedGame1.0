package com.turnbasedgame.game.Actors.AI.Tasks.BehaviorTrees.Strategy1Tree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Actors.Actors;
import com.turnbasedgame.game.Actors.Entity.Entity;
import com.turnbasedgame.game.Utilities.Console;
import com.turnbasedgame.game.Utilities.Geometry;

/**
 * Created by Boris on 20.02.2016.
 * Project: TurnBasedGame1.0
 */
public class FindClosestEnemyTask extends LeafTask<AI> implements InformableTaskInterface{

    String attackerName = "n/a", closestTargetName = "n/a";

    @Override
    public void informExecuted() {
        Console.addLine("ai", "AI invoked 'findClosestEnemy' task ...", Console.LineType.REGULAR);
    }

    @Override
    public void informSucceeded() {
        Console.addLine("ai", "Result:" + attackerName + " -> " + closestTargetName, Console.LineType.SUCCESS);
    }

    @Override
    public void informFailed() {
        Console.addLine("ai", "Failed to find any enemies!", Console.LineType.ERROR);
    }

    @Override
    public Status execute() {
        informExecuted();

        float lastDistance = Float.MAX_VALUE;
        float currentDistance;
        for (int i = 0; i < Entity.aiList.size(); i++) {
            for (int j = 0; j < Entity.userList.size(); j++) {
                if (Geometry.inGridRange(Entity.aiList.get(i).getGridCoordinates(), Entity.userList.get(j).getGridCoordinates(), Entity.aiList.get(i).getRadiusOfSight(), 0)) {
                    currentDistance = Entity.aiList.get(i).getGridCoordinates().dst(Entity.userList.get(j).getGridCoordinates());
                    if (currentDistance < lastDistance) {
                        lastDistance = currentDistance;
                        attackerName = Entity.aiList.get(i).getFullName();
                        closestTargetName = Entity.userList.get(j).getFullName();
                    }
                }

            }
        }

        if (!attackerName.equals("n/a") && !closestTargetName.equals("n/a")) {
            informSucceeded();
            Actors.gameAI.strategy1attackerName = attackerName;
            Actors.gameAI.strategy1targetName = closestTargetName;
            return Status.SUCCEEDED;
        }else {
            informFailed();
            return Status.FAILED;
        }
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
