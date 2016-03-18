package com.turnbasedgame.game.Actors.AI.Tasks.EntityInteraction.MovingEntity;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.badlogic.gdx.math.Vector3;
import com.turnbasedgame.game.Actors.AI.AI;
import com.turnbasedgame.game.Actors.AI.Tasks.InformableTaskInterface;
import com.turnbasedgame.game.Actors.Entity.Entity;
import com.turnbasedgame.game.Actors.Entity.MovingEntity;
import com.turnbasedgame.game.Actors.Grid.Grid;
import com.turnbasedgame.game.Utilities.Console;

/**
 * Created by Boris on 27.02.2016.
 * Project: TurnBasedGame1.0
 */
public class MoveTask extends LeafTask<AI> implements InformableTaskInterface{
    @TaskAttribute (required = true)
    public String entityFullName;

    @TaskAttribute (required = true)
    public Vector3 targetNodeGridCoordinates = new Vector3();

    Vector3 lastGridCoordinates = new Vector3();
    String failReason;

    @Override
    public void informExecuted() {
        Console.addLine("ai", "AI invoked 'move' task ... ", Console.LineType.REGULAR);
    }

    @Override
    public void informSucceeded() {
        Console.addLine("ai", entityFullName + " moved: " + lastGridCoordinates + " -> " + targetNodeGridCoordinates, Console.LineType.SUCCESS);
    }

    @Override
    public void informFailed() {
        Console.addLine("ai", "Could not move. Reason: " + failReason, Console.LineType.ERROR);
    }

    @Override
    public Status execute() {
        informExecuted();
        if (entityFullName.equals("n/a") || entityFullName == null) {
            failReason = "entity's name is not valid";
            informFailed();
            return Status.FAILED;
        }else if ((targetNodeGridCoordinates.x < 0 || targetNodeGridCoordinates.x > Grid.size.x)
                ||(targetNodeGridCoordinates.y < 0 || targetNodeGridCoordinates.y > Grid.size.y)
                ||(targetNodeGridCoordinates.z < 0 || targetNodeGridCoordinates.z > Grid.size.z)) {
            failReason = "target node grid coordinates are not valid - " + targetNodeGridCoordinates;
            informFailed();
            return Status.FAILED;
        }else if (!(Entity.getEntity(entityFullName) instanceof MovingEntity)) {
            failReason = "entity can't be moved";
            informFailed();
            return Status.FAILED;
        }else {
            Entity.getEntity(entityFullName).getPhases().get(2).enter();
            lastGridCoordinates = Entity.getEntity(entityFullName).getGridCoordinates();
            ((MovingEntity) Entity.getEntity(entityFullName)).move(targetNodeGridCoordinates, true);
            informSucceeded();
            return Status.SUCCEEDED;
        }
    }

    @Override
    protected Task<AI> copyTo(Task<AI> task) {
        return null;
    }
}
