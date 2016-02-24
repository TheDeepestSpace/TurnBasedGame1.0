package com.turnbasedgame.game.Actors.Grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.turnbasedgame.game.Actors.Entity.Entity;
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

    public static final int tileSize = 1;
    public static final int tileSceneSize = 1;

    public static final int pathFound = 1;
    public static final int pathNotFound = 2;

    public static ArrayList<ArrayList<ArrayList<GridNode>>> nodes;
    public static Array<GridNode> openedList;
    public static Array<GridNode> closedList;
    public static Array<GridNode> finalPath;

    private static Vector3 start, end;

    static String name;
    public static Vector3 size;

    static File gridLayout;
    static Scanner fileScanner;
    static String gridLayoutName;
    static boolean gridLayoutLocal;

    /* VISUALISING */

    static Model gridModel;
    static ModelInstance gridModelInstance;

    /** INITIALISING */

    public static void initialise() {
        nodes = new ArrayList<ArrayList<ArrayList<GridNode>>>();
        openedList = new Array<GridNode>();
        closedList = new Array<GridNode>();
        finalPath = new Array<GridNode>();

        start = new Vector3(-1, -1, -1);
        end = new Vector3(-1, -1, -1);

        name = "gameGrid";
        size = new Vector3(20, 10, 20);

        gridLayoutName = "default";
        gridLayoutLocal = true;

        gridModel = new Model();
    }

    /** CREATING AND SETTING */

    public static void setUp() {
        createNodes();
        setUpNodes();

        informCreated();
    }

    static void createNodes() {
        Vector3 nodePos = new Vector3();

        for (int x = 0; x < size.x; x++) {
            nodes.add(new ArrayList<ArrayList<GridNode>>());
            nodePos.x = x;
            for (int y = 0; y < size.y; y++) {
                nodes.get(x).add(new ArrayList<GridNode>());
                nodePos.y = y;
                for (int z = 0; z < size.z; z++) {
                    nodePos.z = z;
                    nodes.get(x).get(y).add(new GridNode(name, nodePos));
                }
            }
        }
    }

    static void setUpNodes() {

        for (int x = 0; x < size.x; x++) {
            for (int y = 0; y < size.y; y++) {
                for (int z = 0; z < size.z; z++) {
                    getNode(x, y, z).setUpBounds();
                }
            }
        }

        if (gridLayoutLocal) {
            gridLayout = new File("ACTORS/GRID/LAYOUTS/" + gridLayoutName + ".txt");
        } else {
            gridLayout = new File("sdcard/" + gridLayoutName + ".txt");
        }

        setUpScanner();
        resetNodes();
        while (fileScanner.hasNext("BLOCK")) {
            fileScanner.next("BLOCK");
            setGridNodeBlock(
                    Scanning.nextVector3(fileScanner),
                    Scanning.nextVector3(fileScanner),
                    GridNodeType.valueOf(fileScanner.next())
            );
        }

        fileScanner.close();

        setUpGridModel();
    }

    static void setUpGridModel() {
        gridModel = TurnBasedGame.gameScreen.modelLoader.loadModel(Gdx.files.internal("ACTORS/GRID/GRID_MODELS/" + gridLayoutName + ".g3db"));
        gridModelInstance = new ModelInstance(gridModel);
        gridModelInstance.transform.scale(0.1f, 0.1f, 0.1f);
    }

    static void setUpScanner() {
        try {
            fileScanner = new Scanner(gridLayout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** PATHFINDING */

    static Vector3 openListPosition = new Vector3();

    public static int findPath() {
        openedList.clear();
        closedList.clear();
        finalPath.clear();

        resetNodesValues();

        if (start.x == -1 || start.y == -1 || start.z == -1
                || end.x == -1 || end.y == -1 || end.z == -1) {
            return pathNotFound;
        } else if (start.x == end.x
                && start.y == end.y
                && start.z == end.z) {
            return pathFound;
        } else {
            openedList.add(
                    getStartNode()
            );

            setOpenList(start);

            closedList.add(openedList.first());
            openedList.removeIndex(0);

            while (closedList.peek() != getEndNode()) {
                if (openedList.size != 0) {
                    int bestFIndex = getBestFIndex();
                    if (bestFIndex != -1) {
                        closedList.add(openedList.get(bestFIndex));
                        openedList.removeIndex(bestFIndex);

                        openListPosition = closedList.peek().gridCoordinates;

                        setOpenList(openListPosition);
                    }else {
                        return pathNotFound;
                    }
                }else {
                    return pathNotFound;
                }
            }
        }

        GridNode g = closedList.peek();
        finalPath.add(g);


        while (g != getStartNode()) {
            g = g.parent;
            finalPath.add(g);
        }

        finalPath.reverse();

        return pathFound;
    }

    static void setOpenList(Vector3 gridCoordinates) {
        boolean ignoreLeft = (gridCoordinates.x - 1) < 0;
        boolean ignoreRight = (gridCoordinates.x + 1) >= size.x;
        boolean ignoreDown = (gridCoordinates.y - 1) < 0;
        boolean ignoreUp = (gridCoordinates.y + 1) >= size.y;
        boolean ignoreBack = (gridCoordinates.z - 1) < 0;
        boolean ignoreFront = (gridCoordinates.z + 1) >= size.z;

        if (!ignoreLeft && !ignoreDown && !ignoreBack) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x - 1, gridCoordinates.y - 1, gridCoordinates.z - 1));
        }

        if (!ignoreLeft && !ignoreDown) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x - 1, gridCoordinates.y - 1, gridCoordinates.z));
        }

        if (!ignoreLeft && !ignoreDown && !ignoreFront) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x - 1, gridCoordinates.y - 1, gridCoordinates.z + 1));
        }

        if (!ignoreDown && !ignoreBack) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x, gridCoordinates.y - 1, gridCoordinates.z - 1));
        }

        if (!ignoreDown) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x, gridCoordinates.y - 1, gridCoordinates.z));
        }

        if (!ignoreDown && !ignoreFront) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x, gridCoordinates.y - 1, gridCoordinates.z + 1));
        }

        if (!ignoreRight && !ignoreDown && !ignoreBack) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x + 1, gridCoordinates.y - 1, gridCoordinates.z - 1));
        }

        if (!ignoreRight && !ignoreDown) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x + 1, gridCoordinates.y - 1, gridCoordinates.z));
        }

        if (!ignoreRight && !ignoreDown && !ignoreFront) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x + 1, gridCoordinates.y - 1, gridCoordinates.z + 1));
        }

        if (!ignoreLeft && !ignoreBack) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x - 1, gridCoordinates.y, gridCoordinates.z - 1));
        }

        if (!ignoreLeft) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x - 1, gridCoordinates.y, gridCoordinates.z));
        }

        if (!ignoreLeft && !ignoreFront) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x - 1, gridCoordinates.y, gridCoordinates.z + 1));
        }

        if (!ignoreBack) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x, gridCoordinates.y, gridCoordinates.z - 1));
        }

        if (!ignoreFront) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x, gridCoordinates.y, gridCoordinates.z + 1));
        }

        if (!ignoreRight && !ignoreBack) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x + 1, gridCoordinates.y, gridCoordinates.z - 1));
        }

        if (!ignoreRight) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x + 1, gridCoordinates.y, gridCoordinates.z));
        }

        if (!ignoreRight && !ignoreFront) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x + 1, gridCoordinates.y, gridCoordinates.z + 1));
        }

        if (!ignoreLeft && !ignoreUp && !ignoreBack) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x - 1, gridCoordinates.y + 1, gridCoordinates.z - 1));
        }

        if (!ignoreLeft && !ignoreUp) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x - 1, gridCoordinates.y + 1, gridCoordinates.z));
        }

        if (!ignoreLeft && !ignoreUp && !ignoreFront) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x - 1, gridCoordinates.y + 1, gridCoordinates.z + 1));
        }

        if (!ignoreUp && !ignoreBack) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x, gridCoordinates.y + 1, gridCoordinates.z - 1));
        }

        if (!ignoreUp) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x, gridCoordinates.y + 1, gridCoordinates.z));
        }

        if (!ignoreUp && !ignoreFront) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x, gridCoordinates.y + 1, gridCoordinates.z + 1));
        }

        if (!ignoreRight && !ignoreUp && !ignoreBack) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x + 1, gridCoordinates.y + 1, gridCoordinates.z - 1));
        }

        if (!ignoreRight && !ignoreUp) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x + 1, gridCoordinates.y +1, gridCoordinates.z));
        }

        if (!ignoreRight && !ignoreUp && !ignoreFront) {
            lookNode(getNode(gridCoordinates),
                    getNode(gridCoordinates.x + 1, gridCoordinates.y + 1, gridCoordinates.z + 1));
        }
    }

    static void lookNode(GridNode parentNode, GridNode currentNode) {
        if (currentNode.type != GridNodeType.BLOCK &&
                !(closedList.contains(currentNode, true) || closedList.contains(currentNode, false))) {
            if (!(openedList.contains(currentNode, true) || closedList.contains(currentNode, false))) {
                currentNode.calculateValues(parentNode, getEndNode());
                openedList.add(currentNode);
            } else {
                compareParentWithOpen(parentNode, currentNode);
            }
        }
    }

    static void compareParentWithOpen(GridNode parentNode, GridNode openNode) {
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
            openNode.calculateValues(parentNode, getEndNode());
            openedList.set(openedList.indexOf(openNode, true), openNode);
        }

    }

    public static void setGridNodeBlock(Vector3 position, Vector3 size, GridNodeType type) {
        for (int x = (int) position.x; x < (int) position.x + (int) size.x; x++) {
            for (int y = (int)position.y; y < (int)position.y + (int)size.y; y++) {
                for (int z = (int)position.z; z < (int)position.z + (int)size.z; z++) {
                    setGridNode(new Vector3(x, y, z), type);
                }
            }
        }
    }

    public static void setGridNode(Vector3 position, GridNodeType type) {
        if (position.x >= 0 && position.x < size.x) {
            if (position.y >= 0 && position.y < size.y) {
                if (position.z >= 0 && position.z < size.z) {
                    if (type == GridNodeType.START || type == GridNodeType.END) {
                        for (int x = 0; x < size.x; x++) {
                            for (int y = 0; y < size.y; y++) {
                                for (int z = 0; z < size.z; z++) {
                                    if (getNode(x, y, z).type == type) {
                                        if (type == GridNodeType.START) {
                                            start.x = -1;
                                            start.y = -1;
                                            start.z = -1;
                                        } else {
                                            end.x = -1;
                                            end.y = -1;
                                            end.z = -1;
                                        }

                                        getNode(x, y, z).type = GridNodeType.BLANK;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (type == GridNodeType.START) {
            start = position.cpy();
        } else if (type == GridNodeType.END) {
            end = position.cpy();
        }
        getNode(position).type = type;
        getNode(position).update();
    }


    public static void prepareGrid() {
        for (int i = 0; i < Entity.list.size(); i++) {
            Grid.setGridNode(Entity.list.get(i).getGridCoordinates(), GridNodeType.BLOCK);
        }
    }

    public static void unprepareGrid() {
        for (int i = 0; i < Entity.list.size(); i++) {
            Grid.setGridNode(Entity.list.get(i).getGridCoordinates(), GridNodeType.BLANK);
        }
    }

    public static void setStart(Vector3 gridCoordinates) {
        Grid.setGridNode(gridCoordinates.cpy(), GridNodeType.START);
    }

    public static void setEnd(Vector3 gridCoordinates) {
        Grid.setGridNode(gridCoordinates.cpy(), GridNodeType.END);
    }

    /** GETTERS / SETTERS */

    public static GridNode getNode(Vector3 gridCoordinates) {
        return nodes.get((int) gridCoordinates.x).get((int) gridCoordinates.y).get((int) gridCoordinates.z);
    }

    public static GridNode getNode(float gridCoordinatesX, float gridCoordinatesY, float gridCoordinatesZ) {
        return nodes.get((int) gridCoordinatesX).get((int) gridCoordinatesY).get((int) gridCoordinatesZ);
    }

    public static GridNode getStartNode() {
        return nodes.get((int) start.x).get((int) start.y).get((int) start.z);
    }

    public static GridNode getEndNode() {
        return nodes.get((int) end.x).get((int) end.y).get((int) end.z);
    }

    static int getBestFIndex () {
        double bestF = Float.MAX_VALUE;
        int index = -1;

        for (int i = 0; i < openedList.size; i++) {
            if (bestF > openedList.get(i).F) {
                bestF = openedList.get(i).F;
                index = i;
            }
        }

        return index;
    }

    /** RENDERING */

    public static void render() {
        Renderer.renderModelInstance(gridModelInstance);
    }

    static void renderShadow() {
        Renderer.renderModelInstanceShadow(gridModelInstance);
    }

    /** DISPOSING / RESETTING */

    public static void dispose() {
        disposeNodes();
    }

    static void disposeNodes() {
        for (int x = 0; x < size.x; x++) {
            for (int y = 0; y < size.y; y++) {
                for (int z = 0; z < size.z; z++) {
                    getNode(x, y, z).dispose();
                }
            }
        }

        nodes.clear();
        openedList.clear();
        closedList.clear();
        finalPath.clear();
    }

    static void resetNodesValues() {
        for (int x = 0; x < size.x; x++) {
            for (int y = 0; y < size.y; y++) {
                for (int z = 0; z < size.z; z++) {
                    getNode(x, y, z).reset();
                }
            }
        }
    }

    static void resetNodes() {
        setGridNodeBlock(new Vector3(0, 0, 0), size, GridNodeType.BLOCK);
    }

    /** INFORMING */

    static void informCreated() {
        Console.addLine("gameConsole", "Playing grid was successfully created!", Console.LineType.SUCCESS);
    }

}
