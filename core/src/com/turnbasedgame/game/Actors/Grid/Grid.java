package com.turnbasedgame.game.Actors.Grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.turnbasedgame.game.TurnBasedGame;
import com.turnbasedgame.game.Utilities.Console;
import com.turnbasedgame.game.Utilities.Rendering.Renderer;
import com.turnbasedgame.game.Utilities.RewrittenClasses.ModelInstance;
import com.turnbasedgame.game.Utilities.Scanning;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Boris on 10.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Grid {
    public static ArrayList<Grid> list;

    public static final int tileSize = 1;
    public static final int tileSceneSize = 1;

    public static final int pathFound = 1;
    public static final int pathNotFound = 2;

    public ArrayList<ArrayList<ArrayList<GridNode>>> nodes;
    public Array<GridNode> openedList;
    public Array<GridNode> closedList;
    public Array<GridNode> finalPath;

    private Vector3 start, end;

    String name;
    public Vector3 size;

    File gridLayout;
    Scanner fileScanner;
    String gridLayoutName;

    /* VISUALISING */

    Model gridModel;
    ModelInstance gridModelInstance;

    /** INITIALISING */

    public static void initialiseInstances() {
        list = new ArrayList<Grid>();
    }

    private void initialise() {
        this.nodes = new ArrayList<ArrayList<ArrayList<GridNode>>>();
        this.openedList = new Array<GridNode>();
        this.closedList = new Array<GridNode>();
        this.finalPath = new Array<GridNode>();

        this.start = new Vector3();
        this.end = new Vector3();

        this.name = "n/a";
        this.size = new Vector3(0, 0, 0);

        this.gridLayoutName = "n/a";

        this.gridModel = new Model();
    }

    /** CREATING AND SETTING */

    public static Grid addInstance(String name) {
        Grid grid = new Grid(name);
        list.add(grid);
        return grid;
    }

    public static void setUpInstance(String name, Vector3 size, String gridLayout, boolean isLayoutLocal) {
        getGrid(name).setUp(size, gridLayout, isLayoutLocal);
    }

    private Grid(String name) {
        this.initialise();
        this.name = name;
    }

    private void setUp(Vector3 size, String gridLayout, boolean isLayoutLocal) {
        this.size.x = size.x;
        this.size.y = size.y;
        this.size.z = size.z;

        this.createNodes();
        this.setUpNodes(gridLayout, isLayoutLocal);

        this.informCreated();
    }

    void createNodes() {
        Vector3 nodePos = new Vector3();

        for (int x = 0; x < this.size.x; x++) {
            this.nodes.add(new ArrayList<ArrayList<GridNode>>());
            nodePos.x = x;
            for (int y = 0; y < this.size.y; y++) {
                this.nodes.get(x).add(new ArrayList<GridNode>());
                nodePos.y = y;
                for (int z = 0; z < this.size.z; z++) {
                    nodePos.z = z;
                    this.nodes.get(x).get(y).add(new GridNode(this.name, nodePos));
                }
            }
        }
    }

    public void setUpNodes(String gridLayout, boolean isLocal) {

        for (int x = 0; x < this.size.x; x++) {
            for (int y = 0; y < this.size.y; y++) {
                for (int z = 0; z < this.size.z; z++) {
                    this.getNode(x, y, z).setUpBounds();
                }
            }
        }

        if (isLocal) {
            this.gridLayout = new File("ACTORS/GRID/LAYOUTS/" + gridLayout + ".txt");
        } else {
            this.gridLayout = new File("sdcard/" + gridLayout + ".txt");
        }

        this.gridLayoutName = gridLayout;

        this.setUpScanner();
        this.resetNodes();
        while (this.fileScanner.hasNext("BLOCK")) {
            this.fileScanner.next("BLOCK");
            this.setGridNodeBlock(
                    Scanning.nextVector3(this.fileScanner),
                    Scanning.nextVector3(this.fileScanner),
                    GridNodeType.valueOf(this.fileScanner.next())
            );
        }

        this.fileScanner.close();

        this.setUpGridModel();
    }

    private void setUpGridModel() {
        this.gridModel = TurnBasedGame.gameScreen.modelLoader.loadModel(Gdx.files.internal("ACTORS/GRID/GRID_MODELS/" + this.gridLayoutName + ".g3db"));
        this.gridModelInstance = new ModelInstance(this.gridModel);
        this.gridModelInstance.transform.scale(0.1f, 0.1f, 0.1f);
    }

    void setUpScanner() {
        try {
            this.fileScanner = new Scanner(this.gridLayout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** PATHFINDING */

    static Vector3 openListPosition = new Vector3();

    public int findPath() {
        this.openedList.clear();
        this.closedList.clear();
        this.finalPath.clear();

        this.resetNodesValues();

        if (this.start.x == -1 || this.start.y == -1 || this.start.z == -1
                ||this.end.x == -1 || this.end.y == -1 || this.end.z == -1) {
            return pathNotFound;
        } else if (this.start.x == this.end.x
                &&this.start.y == this.end.y
                &&this.start.z == this.end.z) {
            return pathFound;
        } else {
            this.openedList.add(
                    this.getStartNode()
            );

            this.setOpenList(this.start);

            this.closedList.add(this.openedList.first());
            this.openedList.removeIndex(0);

            while (this.closedList.peek() != this.getEndNode()) {
                if (this.openedList.size != 0) {
                    int bestFIndex = this.getBestFIndex();
                    if (bestFIndex != -1) {
                        this.closedList.add(this.openedList.get(bestFIndex));
                        this.openedList.removeIndex(bestFIndex);

                        openListPosition = this.closedList.peek().gridCoordinates;

                        this.setOpenList(openListPosition);
                    }else {
                        return pathNotFound;
                    }
                }else {
                    return pathNotFound;
                }
            }
        }

        GridNode g = this.closedList.peek();
        this.finalPath.add(g);


        while (g != this.getStartNode()) {
            g = g.parent;
            this.finalPath.add(g);
        }

        this.finalPath.reverse();

        return pathFound;
    }

    private void setOpenList(Vector3 gridCoordinates) {
        boolean ignoreLeft = (gridCoordinates.x - 1) < 0;
        boolean ignoreRight = (gridCoordinates.x + 1) >= this.size.x;
        boolean ignoreDown = (gridCoordinates.y - 1) < 0;
        boolean ignoreUp = (gridCoordinates.y + 1) >= this.size.y;
        boolean ignoreBack = (gridCoordinates.z - 1) < 0;
        boolean ignoreFront = (gridCoordinates.z + 1) >= this.size.z;

        if (!ignoreLeft && !ignoreDown && !ignoreBack) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x - 1, gridCoordinates.y - 1, gridCoordinates.z - 1));
        }

        if (!ignoreLeft && !ignoreDown) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x - 1, gridCoordinates.y - 1, gridCoordinates.z));
        }

        if (!ignoreLeft && !ignoreDown && !ignoreFront) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x - 1, gridCoordinates.y - 1, gridCoordinates.z + 1));
        }

        if (!ignoreDown && !ignoreBack) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x, gridCoordinates.y - 1, gridCoordinates.z - 1));
        }

        if (!ignoreDown) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x, gridCoordinates.y - 1, gridCoordinates.z));
        }

        if (!ignoreDown && !ignoreFront) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x, gridCoordinates.y - 1, gridCoordinates.z + 1));
        }

        if (!ignoreRight && !ignoreDown && !ignoreBack) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x + 1, gridCoordinates.y - 1, gridCoordinates.z - 1));
        }

        if (!ignoreRight && !ignoreDown) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x + 1, gridCoordinates.y - 1, gridCoordinates.z));
        }

        if (!ignoreRight && !ignoreDown && !ignoreFront) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x + 1, gridCoordinates.y - 1, gridCoordinates.z + 1));
        }

        if (!ignoreLeft && !ignoreBack) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x - 1, gridCoordinates.y, gridCoordinates.z - 1));
        }

        if (!ignoreLeft) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x - 1, gridCoordinates.y, gridCoordinates.z));
        }

        if (!ignoreLeft && !ignoreFront) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x - 1, gridCoordinates.y, gridCoordinates.z + 1));
        }

        if (!ignoreBack) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x, gridCoordinates.y, gridCoordinates.z - 1));
        }

        if (!ignoreFront) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x, gridCoordinates.y, gridCoordinates.z + 1));
        }

        if (!ignoreRight && !ignoreBack) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x + 1, gridCoordinates.y, gridCoordinates.z - 1));
        }

        if (!ignoreRight) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x + 1, gridCoordinates.y, gridCoordinates.z));
        }

        if (!ignoreRight && !ignoreFront) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x + 1, gridCoordinates.y, gridCoordinates.z + 1));
        }

        if (!ignoreLeft && !ignoreUp && !ignoreBack) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x - 1, gridCoordinates.y + 1, gridCoordinates.z - 1));
        }

        if (!ignoreLeft && !ignoreUp) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x - 1, gridCoordinates.y + 1, gridCoordinates.z));
        }

        if (!ignoreLeft && !ignoreUp && !ignoreFront) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x - 1, gridCoordinates.y + 1, gridCoordinates.z + 1));
        }

        if (!ignoreUp && !ignoreBack) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x, gridCoordinates.y + 1, gridCoordinates.z - 1));
        }

        if (!ignoreUp) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x, gridCoordinates.y + 1, gridCoordinates.z));
        }

        if (!ignoreUp && !ignoreFront) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x, gridCoordinates.y + 1, gridCoordinates.z + 1));
        }

        if (!ignoreRight && !ignoreUp && !ignoreBack) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x + 1, gridCoordinates.y + 1, gridCoordinates.z - 1));
        }

        if (!ignoreRight && !ignoreUp) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x + 1, gridCoordinates.y +1, gridCoordinates.z));
        }

        if (!ignoreRight && !ignoreUp && !ignoreFront) {
            lookNode(this.getNode(gridCoordinates),
                    this.getNode(gridCoordinates.x + 1, gridCoordinates.y + 1, gridCoordinates.z + 1));
        }
    }

    private void lookNode(GridNode parentNode, GridNode currentNode) {
        if (currentNode.type != GridNodeType.BLOCK &&
                !(this.closedList.contains(currentNode, true) || this.closedList.contains(currentNode, false))) {
            if (!(this.openedList.contains(currentNode, true) || this.closedList.contains(currentNode, false))) {
                currentNode.calculateValues(parentNode, this.getEndNode());
                this.openedList.add(currentNode);
            } else {
                compareParentWithOpen(parentNode, currentNode);
            }
        }
    }

    private void compareParentWithOpen(GridNode parentNode, GridNode openNode) {
        double tempG = openNode.G;
        double xDistance = Math.abs(openNode.gridCoordinates.x - parentNode.gridCoordinates.x) / tileSize;
        double yDistance = Math.abs(openNode.gridCoordinates.y - parentNode.gridCoordinates.y) / tileSize;
        double zDistance = Math.abs(openNode.gridCoordinates.z - parentNode.gridCoordinates.z) / tileSize;

        if (xDistance == 1 && yDistance == 1 && zDistance == 1) {
            tempG += 17;
        }else if ((xDistance == 1 && yDistance == 1)
                ||(xDistance == 1 && zDistance == 1)
                ||(yDistance == 1 && zDistance == 1)) {
            tempG += 14;
        }else {
            tempG += 10;
        }

        if (tempG < parentNode.G) {
            openNode.calculateValues(parentNode, this.getEndNode());
            this.openedList.set(this.openedList.indexOf(openNode, true), openNode);
        }

    }

    public void setGridNodeBlock(Vector3 position, Vector3 size, GridNodeType type) {
        for (int x = (int) position.x; x < (int) position.x + (int) size.x; x++) {
            for (int y = (int)position.y; y < (int)position.y + (int)size.y; y++) {
                for (int z = (int)position.z; z < (int)position.z + (int)size.z; z++) {
                    this.setGridNode(new Vector3(x, y, z), type);
                }
            }
        }
    }

    public void setGridNode(Vector3 position, GridNodeType type) {
        if (position.x >= 0 && position.x < this.size.x) {
            if (position.y >= 0 && position.y < this.size.y) {
                if (position.z >= 0 && position.z < this.size.z) {
                    if (type == GridNodeType.START || type == GridNodeType.END) {
                        for (int x = 0; x < this.size.x; x++) {
                            for (int y = 0; y < this.size.y; y++) {
                                for (int z = 0; z < this.size.z; z++) {
                                    if (this.getNode(x, y, z).type == type) {
                                        if (type == GridNodeType.START) {
                                            this.start.x = -1;
                                            this.start.y = -1;
                                            this.start.z = -1;
                                        } else {
                                            this.end.x = -1;
                                            this.end.y = -1;
                                            this.end.z = -1;
                                        }

                                        this.getNode(x, y, z).type = GridNodeType.BLANK;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (type == GridNodeType.START) {
            this.start = position.cpy();
        } else if (type == GridNodeType.END) {
            this.end = position.cpy();
        }
        this.getNode(position).type = type;
        this.getNode(position).update();
    }

    /** GETTERS / SETTERS */

    public GridNode getNode(Vector3 gridCoordinates) {
        return this.nodes.get((int) gridCoordinates.x).get((int) gridCoordinates.y).get((int) gridCoordinates.z);
    }

    public GridNode getNode(float gridCoordinatesX, float gridCoordinatesY, float gridCoordinatesZ) {
        return this.nodes.get((int) gridCoordinatesX).get((int) gridCoordinatesY).get((int) gridCoordinatesZ);
    }

    public GridNode getStartNode() {
        return this.nodes.get((int) start.x).get((int) start.y).get((int) start.z);
    }

    public GridNode getEndNode() {
        return this.nodes.get((int) end.x).get((int) end.y).get((int) end.z);
    }

    private int getBestFIndex () {
        double bestF = Float.MAX_VALUE;
        int index = -1;

        for (int i = 0; i < this.openedList.size; i++) {
            if (bestF > this.openedList.get(i).F) {
                bestF = this.openedList.get(i).F;
                index = i;
            }
        }

        return index;
    }

    public static Grid getGrid(String name) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).name.equals(name)) return list.get(i);
        }

        return null;
    }

    /** RENDERING */

    public static void renderInstances() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).render();
        }
    }

    public static void renderInstancesShadows() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).renderShadow();
        }
    }

    private void render() {
        Renderer.renderModelInstance(this.gridModelInstance);
    }

    private void renderShadow() {
        Renderer.renderModelInstanceShadow(this.gridModelInstance);
    }

    /** DISPOSING / RESETTING */

    public static void disposeInstances() {
        for (int i = 0, len = list.size(); i < len; i++) {
            list.get(i).dispose();
        }

        list.clear();
    }

    private void dispose() {
        this.disposeNodes();
    }

    private void disposeNodes() {
        for (int x = 0; x < this.size.x; x++) {
            for (int y = 0; y < this.size.y; y++) {
                for (int z = 0; z < this.size.z; z++) {
                    this.getNode(x, y, z).dispose();
                }
            }
        }

        this.nodes.clear();
        this.openedList.clear();
        this.closedList.clear();
        this.finalPath.clear();
    }

    private void resetNodesValues() {
        for (int x = 0; x < this.size.x; x++) {
            for (int y = 0; y < this.size.y; y++) {
                for (int z = 0; z < this.size.z; z++) {
                    this.getNode(x, y, z).reset();
                }
            }
        }
    }

    private void resetNodes() {
        this.setGridNodeBlock(new Vector3(0, 0, 0), this.size, GridNodeType.BLOCK);
    }

    /** INFORMING */

    void informCreated() {
        Console.addLine("gameConsole", "Playing grid was successfully created!", Console.LineType.SUCCESS);
    }

}
