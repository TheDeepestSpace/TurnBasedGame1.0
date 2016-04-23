package com.turnbasedgame.game.Actors.Entity;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.turnbasedgame.game.Actors.Actors;
import com.turnbasedgame.game.Actors.Entity.Properties.Phase;
import com.turnbasedgame.game.Actors.Grid.Grid;
import com.turnbasedgame.game.Actors.User.User;
import com.turnbasedgame.game.UserInterface.Actors.Button;
import com.turnbasedgame.game.Utilities.Console;
import com.turnbasedgame.game.Utilities.Geometry;

import java.util.ArrayList;

/**
 * Created by Boris on 24.02.2016.
 * Project: TurnBasedGame1.0
 */
public class MovingEntity extends AttackingEntity {
    public static String className = "MOVING.ENTITY";

    /* PROPERTIES */

    public Vector3 targetNodeGridCoordinates;
    ArrayList<Vector3> path;

    int currentPathItemIndex;
    Vector3 gridNextCoordinates;
    Vector3 movement;
    int tileDivisionsOvercame;

    static final int tileDivisions = 20;

    /* SETTABLE PROPERTIES */

    /* FLAGS / TRIGGERS */

    boolean inMovement;
    boolean reachedNextTile;
    boolean movementByArtificial;

    /** INITIALISING */
    @Override
    void initialise() {
        super.initialise();

        this.targetNodeGridCoordinates = new Vector3();
        this.path = new ArrayList<Vector3>();

        this.currentPathItemIndex = 0;
        this.gridNextCoordinates = new Vector3();
        this.movement = new Vector3();
        this.tileDivisionsOvercame = 0;

        this.inMovement = false;
        this.reachedNextTile = true;
        this.movementByArtificial = false;
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

    @Override
    public void update() {
        super.update();

        if (this.inMovement && !this.isArtificial()) this.processMovement();
    }

    public void processMovement() {
        this.updateNextPosition();
        this.calculateMovement();
        this.updatePosition();
        this.reachTargetNode();
    }

    void updateNextPosition() {
        if (this.currentPathItemIndex + 1 < this.path.size()) {
            this.gridNextCoordinates.x = this.path.get(this.currentPathItemIndex + 1).x;
            this.gridNextCoordinates.y = this.path.get(this.currentPathItemIndex + 1).y;
            this.gridNextCoordinates.z = this.path.get(this.currentPathItemIndex + 1).z;
        }
    }

    void calculateMovement() {
        this.movement.x =
                Geometry.getSceneCoordinates(this.gridNextCoordinates).x
                        - Geometry.getSceneCoordinates(this.gridCoordinates).x;
        this.movement.y =
                Geometry.getSceneCoordinates(this.gridNextCoordinates).y
                        - Geometry.getSceneCoordinates(this.gridCoordinates).y;
        this.movement.z =
                Geometry.getSceneCoordinates(this.gridNextCoordinates).z
                        - Geometry.getSceneCoordinates(this.gridCoordinates).z;

        this.movement.x /= tileDivisions;
        this.movement.y /= tileDivisions;
        this.movement.z /= tileDivisions;
    }

    void updatePosition() {
        this.tileDivisionsOvercame++;
        this.reachedNextTile = false;

        if (this.tileDivisionsOvercame == tileDivisions) {
            this.reachedNextTile = true;
            this.tileDivisionsOvercame = 0;
        }

        if (this.reachedNextTile) {
            this.gridCoordinates = this.gridNextCoordinates.cpy();
            this.currentPathItemIndex++;
            if (this.selected) this.updatePropertiesTable();
        }

        this.updateSceneCoordinates();
        this.updateModelPosition();
    }

    @Override
    void updateSceneCoordinates() {
        this.sceneCoordinates.x = Geometry.getSceneCoordinates(this.gridCoordinates).x;

        if (!this.reachedNextTile) {
            this.sceneCoordinates.x += tileDivisionsOvercame * movement.x;
        }

        this.sceneCoordinates.y = Geometry.getSceneCoordinates(this.gridCoordinates).y;

        if (!this.reachedNextTile) {
            this.sceneCoordinates.y += tileDivisionsOvercame * movement.y;
        }

        this.sceneCoordinates.z = Geometry.getSceneCoordinates(this.gridCoordinates).z;

        if (!this.reachedNextTile) {
            this.sceneCoordinates.z += tileDivisionsOvercame * movement.z;
        }
    }

    void reachTargetNode() {
        if (this.gridCoordinates.x == this.targetNodeGridCoordinates.x
                && this.gridCoordinates.y == this.targetNodeGridCoordinates.y
                && this.gridCoordinates.z == this.targetNodeGridCoordinates.z) {
            this.inMovement = false;
            this.informMoved(targetNodeGridCoordinates);

            if (!this.movementByArtificial) {
                User.interactedWithEntity = true;
            }

            this.phases.get(2).escape();
        }
    }

    /** INTERACTING */

    void selectNodeToMoveTo() {
        User.selectingNodeToMoveTo();
        this.informSelectingNodeToMoveTo();
    }

    public void move(Vector3 targetNodeGridCoordinates, boolean byArtificial) {
        if (this.canReach(targetNodeGridCoordinates)) {
            this.targetNodeGridCoordinates = targetNodeGridCoordinates.cpy();
            this.movementByArtificial = byArtificial;
            this.inMovement = true;
            this.currentPathItemIndex = 0;
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
                    this.savePath();
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

    void savePath() {
        this.path.clear();
        for (int i = 0; i < Grid.finalPath.size; i++) {
            this.path.add(Grid.finalPath.get(i).gridCoordinates.cpy());
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

    public boolean isInMovement() {
        return inMovement;
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
