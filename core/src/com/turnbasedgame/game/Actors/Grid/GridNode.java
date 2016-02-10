package com.turnbasedgame.game.Actors.Grid;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.turnbasedgame.game.Utilities.Geometry;

/**
 * Created by Boris on 10.02.2016.
 * Project: TurnBasedGame1.0
 */
public class GridNode {
    public Vector3 gridCoordinates;
    public Vector3 sceneCoordinates;

    String gridName;
    public GridNodeType type;

    public GridNode parent;
    public int F, G, H;

    /* VISUALISING */

    public BoundingBox bounds;

    /** INITIALISING */

    void initialise() {
        this.gridCoordinates = new Vector3();
        this.sceneCoordinates = new Vector3();
        this.gridName = "n/a";
        this.type = GridNodeType.BLOCK;

        this.parent = null;
        this.F = this.G = this.H = 0;
    }

    /** CREATING AND SETTING */

    public GridNode(String gridName, Vector3 gridCoordinates) {
        this.initialise();
        this.setUp(gridName, gridCoordinates);
    }

    void setUp(String gridName, Vector3 gridCoordinates) {
        this.gridName = gridName;
        this.gridCoordinates = gridCoordinates.cpy();
    }

    void setUpBounds() {
        this.sceneCoordinates = Geometry.getSceneCoordinates(this.gridCoordinates, "gameGrid");

        this.bounds = new BoundingBox();
        this.bounds.set(
                new Vector3(
                        sceneCoordinates.x - (float) Grid.tileSize / 2,
                        sceneCoordinates.y - (float) Grid.tileSize / 2,
                        sceneCoordinates.z - (float) Grid.tileSize / 2
                ),
                new Vector3(
                        sceneCoordinates.x + (float) Grid.tileSize / 2,
                        sceneCoordinates.y + (float) Grid.tileSize / 2,
                        sceneCoordinates.z + (float) Grid.tileSize / 2
                )
        );
    }

    /** PATHFINDING */

    public void calculateValues(GridNode parentNode, GridNode endNode) {
        this.parent = parentNode;

        double xDistance = Math.abs(this.gridCoordinates.x - this.parent.gridCoordinates.x);
        double yDistance = Math.abs(this.gridCoordinates.y - this.parent.gridCoordinates.y);
        double zDistance = Math.abs(this.gridCoordinates.z - this.parent.gridCoordinates.z);

        double xEndDistance = Math.abs(this.gridCoordinates.x - endNode.gridCoordinates.x);
        double yEndDistance = Math.abs(this.gridCoordinates.y - endNode.gridCoordinates.y);
        double zEndDistance = Math.abs(this.gridCoordinates.z - endNode.gridCoordinates.z);

        if (this.parent != null) {
            if (xDistance != 0 && yDistance != 0 && zDistance != 0) {
                this.G = this.parent.G + 17;
            } else if (
                    (xDistance != 0 && yDistance != 0) ||
                            (xDistance != 0 && zDistance != 0) ||
                            (yDistance != 0 && zDistance != 0)
                    ) {
                this.G = this.parent.G + 14;
            } else {
                this.G = this.parent.G + 10;
            }
        }

        this.H = (int)xEndDistance + (int)yEndDistance + (int)zEndDistance;
        this.F = this.G = this.H;
    }

    /** UPDATING */

    public void update() {
    }

    /** GETTERS / SETTERS */

    /** DISPOSING / RESETTING */

    public void reset() {
        this.F = this.G = this.H = 0;
        this.parent = null;
    }

    public void dispose() {
        this.gridCoordinates = null;
        this.sceneCoordinates = null;
        this.gridName = null;
        this.type = null;
        this.parent = null;
    }
}
