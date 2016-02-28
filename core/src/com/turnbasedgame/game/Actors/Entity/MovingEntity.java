package com.turnbasedgame.game.Actors.Entity;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.turnbasedgame.game.Actors.Entity.Properties.Phase;
import com.turnbasedgame.game.Actors.Grid.Grid;
import com.turnbasedgame.game.Actors.User.User;
import com.turnbasedgame.game.UserInterface.Actors.Button;
import com.turnbasedgame.game.Utilities.Console;
import com.turnbasedgame.game.Utilities.Geometry;

/**
 * Created by Boris on 24.02.2016.
 * Project: TurnBasedGame1.0
 */
public class MovingEntity extends AttackingEntity {
    public static String className = "MOVING.ENTITY";

    /* PROPERTIES */

    public Vector3 targetNodeGridCoordinates;

    /* SETTABLE PROPERTIES */

    /* FLAGS / TRIGGERS */

    /** INITIALISING */
    @Override
    void initialise() {
        super.initialise();

        this.targetNodeGridCoordinates = new Vector3();
    }

    /** CREATING AND SETTING UP */

    public static MovingEntity addInstance(Vector3 gridCoordinates, boolean artificial) {
        MovingEntity entity = new MovingEntity();
        entity.setUp(gridCoordinates, artificial);
        return entity;
    }

    MovingEntity() {super();}

    @Override
    void setUpListeners() {
        super.setUpListeners();

        this.listeners.add(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                phases.get(2).enter();
                phases.get(2).execute();
            }
        });
    }

    @Override
    void setUpPhases() {
        super.setUpPhases();

        this.phases.add(
                new Phase() {
                    @Override
                    public void enter() {
                        escapeCurrentPhase();
                        super.enter();
                    }

                    @Override
                    public void execute() {
                        super.execute();
                        selectNodeToMoveTo();
                    }

                    @Override
                    public void escape() {
                        super.escape();
                        User.selectingEntity();
                    }
                }.setUp("moving", false, this.fullName)
        );
    }

    @Override
    void setUpSettableProperties() {
        super.setUpSettableProperties();

        this.fullName = className + "_" + this.listIndex;
    }

    @Override
    void setUpActionsTable() {
        super.setUpActionsTable();

        actionsTable.add(
                Button.addInstance("ENTITY_ACTIONS_TABLE_MOVE", "MOVE", 30, this.listeners.get(2))
        );

        System.gc();
    }

    /** UPDATING */

    /** INTERACTING */

    void selectNodeToMoveTo() {
        User.selectingNodeToMoveTo();
        this.informSelectingNodeToMoveTo();
    }

    public void move(Vector3 targetNodeGridCoordinates, boolean byArtificial) {
        if (this.canReach(targetNodeGridCoordinates)) {
            this.informMoved(targetNodeGridCoordinates);
            this.gridCoordinates = targetNodeGridCoordinates.cpy();
            this.updateSceneCoordinates();
            this.updateModelPosition();

            if (!byArtificial) {
                User.interactedWithEntity = true;
            }

            this.phases.get(2).escape();
        }
    }

    /** GETTERS / SETTERS */

    public boolean canReach(Vector3 targetNodeGridCoordinates) {
        if (Geometry.inGridRange(
                this.gridCoordinates,
                targetNodeGridCoordinates,
                this.sightRange,
                0
        )) {
            if (nodeClear(targetNodeGridCoordinates)) {
                Grid.prepareGrid();
                Grid.setStart(this.gridCoordinates);
                Grid.setEnd(targetNodeGridCoordinates);

                if (Grid.findPath() == 1) {
                    Grid.unprepareGrid();
                    return true;
                }else {
                    Grid.unprepareGrid();
                    informCouldNotFindPath();
                    return false;
                }
            } else {
                informTargetNodeIsNotClear();
                return false;
            }

        }else {
            informTargetNodeIsOutOfRange();
            return false;
        }
    }

    static boolean nodeClear(Vector3 nodeGridCoordinates) {
        for (int i = 0; i < Entity.list.size(); i++) {
            if (Entity.list.get(i).gridCoordinates.x == nodeGridCoordinates.x
                    && Entity.list.get(i).gridCoordinates.y == nodeGridCoordinates.y
                    && Entity.list.get(i).gridCoordinates.z == nodeGridCoordinates.z) {
                return false;
            }
        }

        return true;
    }

    /** RENDERING */

    /** DISPOSING / RESETTING */

    /** INFORMING */

    void informTargetNodeIsOutOfRange() {
        Console.addLine("gameConsole", "Selected node is not in range of sight", Console.LineType.ERROR);
    }

    void informTargetNodeIsNotClear() {
        Console.addLine("gameConsole", "Selected node is not clear", Console.LineType.ERROR);
    }

    void informCouldNotFindPath() {
        Console.addLine("gameConsole", "Could not ind path to selected node", Console.LineType.ERROR);
    }

    void informSelectingNodeToMoveTo() {
        Console.addLine("gameConsole", "Click on node to move to", Console.LineType.TIP);
    }

    void informMoved(Vector3 targetNodeGridCoordinates) {
        Console.addLine("gameConsole", this.fullName + " moved: " + this.gridCoordinates + " -> " + targetNodeGridCoordinates, Console.LineType.REGULAR);
    }
}
