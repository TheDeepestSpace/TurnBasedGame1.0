package com.turnbasedgame.game.Actors.AI.Tasks.BehaviorTrees.Strategy3Tree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector3;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Actors.Actors;
import com.turnbasedgame.game.Actors.Entity.Entity;
import com.turnbasedgame.game.Actors.Grid.Grid;
import com.turnbasedgame.game.Actors.Grid.GridNodeType;
import com.turnbasedgame.game.Utilities.Console;
import com.turnbasedgame.game.Utilities.Geometry;

/**
 * Created by Boris on 20.04.2016.
 * Project: TurnBasedGame1.0
 */
public class CheckHillsTask extends LeafTask<AI> implements InformableTaskInterface {
    int numHills = 0;

    @Override
    public void informExecuted() {
        Console.addLine("ai", "Checking for hills ...", Console.LineType.REGULAR);
    }

    @Override
    public void informSucceeded() {
        Console.addLine("ai", "Found " + numHills + " hills in sight", Console.LineType.SUCCESS);
    }

    @Override
    public void informFailed() {
        Console.addLine("ai", "No hills in sight found", Console.LineType.ERROR);
    }

    @Override
    public Status execute() {
        informExecuted();
        findHills();
        if (numHills > 0) {
            informSucceeded();
            return Status.SUCCEEDED;
        }else {
            informFailed();
            return Status.FAILED;
        }
    }

    void findHills() {
        Actors.gameAI.seenHills.clear();
        for (int i = 0; i < Entity.aiList.size(); i++) {
            for (int x = 0; x < Grid.size.x; x++) {
                for (int y = 0; y < Grid.size.y; y++) {
                    for (int z = 0; z < Grid.size.z; z++) {
                        if (Geometry.inGridRange(
                                Entity.aiList.get(i).getGridCoordinates(),
                                Grid.getNode(x, y, z).gridCoordinates,
                                Entity.aiList.get(i).getSightRange(), 0)
                                && Grid.getNode(x, y, z).gridCoordinates.y > Entity.aiList.get(i).getGridCoordinates().y
                                && Grid.getNode(x, y, z).type != GridNodeType.BLOCK) {
                            for (int j = 0; j < Actors.gameAI.hills.size(); j++) {
                                if (!(Actors.gameAI.hills.get(j).x == x
                                        && Actors.gameAI.hills.get(j).y == y
                                        && Actors.gameAI.hills.get(j).x == z)) {
                                    Actors.gameAI.hills.add(new Vector3(x, y, z));
                                }
                            }
                            Actors.gameAI.seenHills.add(new Vector3(x, y, z));
                        }
                    }
                }
            }
        }

        numHills = Actors.gameAI.seenHills.size();
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
