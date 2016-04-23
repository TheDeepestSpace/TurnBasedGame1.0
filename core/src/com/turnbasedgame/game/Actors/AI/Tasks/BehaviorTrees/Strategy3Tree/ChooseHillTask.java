package com.turnbasedgame.game.Actors.AI.Tasks.BehaviorTrees.Strategy3Tree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector3;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Actors.Actors;
import com.turnbasedgame.game.Actors.Entity.Entity;
import com.turnbasedgame.game.Utilities.Console;

/**
 * Created by Boris on 20.04.2016.
 * Project: TurnBasedGame1.0
 */
public class ChooseHillTask extends LeafTask<AI> implements InformableTaskInterface {
    Vector3 hillGridCoordinates = new Vector3();
    String entityFullName;

    @Override
    public void informExecuted() {
        Console.addLine("ai", "Choosing hill ... ", Console.LineType.REGULAR);
    }

    @Override
    public void informSucceeded() {
        Console.addLine("ai", "Chosen hill " + hillGridCoordinates + " for " + entityFullName, Console.LineType.SUCCESS);
    }

    @Override
    public void informFailed() {
        Console.addLine("ai", "Could not chose hill", Console.LineType.ERROR);
    }

    @Override
    public Status execute() {
        informExecuted();
        if (chosenHill()) {
            informSucceeded();
            Actors.gameAI.strategy3entityName = entityFullName;
            Actors.gameAI.hillGridCoordinates = hillGridCoordinates.cpy();
            return Status.SUCCEEDED;
        }else {
            informFailed();
            return Status.FAILED;
        }
    }

    boolean chosenHill() {
        double minDistance = Double.MAX_VALUE;
        double curDistance;
        int seenHillIndex = -1;
        for (int i = 0; i < Entity.aiList.size(); i++) {
            for (int j = 0; j < Actors.gameAI.seenHills.size(); j++) {
                curDistance = Entity.aiList.get(i).getGridCoordinates().dst(Actors.gameAI.seenHills.get(j));
                if (curDistance < minDistance) {
                    minDistance = curDistance;
                    seenHillIndex = j;
                    entityFullName = Entity.aiList.get(i).getFullName();
                }
            }
        }

        if (seenHillIndex != -1) {
            hillGridCoordinates.set(Actors.gameAI.seenHills.get(seenHillIndex).cpy());
            return true;
        }else return false;
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
