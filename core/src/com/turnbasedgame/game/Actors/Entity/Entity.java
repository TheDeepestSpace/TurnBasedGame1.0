package com.turnbasedgame.game.Actors.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.turnbasedgame.game.Actors.User.User;
import com.turnbasedgame.game.Global;
import com.turnbasedgame.game.TurnBasedGame;
import com.turnbasedgame.game.UserInterface.Actors.Button;
import com.turnbasedgame.game.UserInterface.Actors.Log;
import com.turnbasedgame.game.UserInterface.GlobalUI;
import com.turnbasedgame.game.Utilities.Console;
import com.turnbasedgame.game.Utilities.Geometry;
import com.turnbasedgame.game.Utilities.Rendering.Renderer;
import com.turnbasedgame.game.Utilities.RewrittenClasses.ModelInstance;

import java.util.ArrayList;

/**
 * Created by Boris on 11.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Entity {
    public static String className = "ENTITY";

    public static ArrayList<Entity> list;
    public static ArrayList<Entity> userList;
    public static ArrayList<Entity> aiList;

    /* PROPERTIES */

    String fullName;
    int listIndex;

    Vector3 gridCoordinates;
    Vector3 sceneCoordinates;

    boolean artificial;

    int healthPoints;

    boolean selected;

    static boolean multipleSelection;

    /* SETTABLE PROPERTIES */

    Vector3 modelSize;

    int radiusOfSight;
    int initialHealthPoints;

    /* VISUALISING */

    Model model;
    ModelInstance modelInstance;

    PointLight sightRange;
    Vector3 pointLightSceneCoordinates;

    /* STATS / TRACKING */

    static Table propertiesTable;

    /* INTERACTING */

    static Table actionsTable;
    ArrayList<ClickListener> listeners;

    /** INITIALISING */

    public static void initialiseClass() {
        list = new ArrayList<Entity>();
        userList = new ArrayList<Entity>();
        aiList = new ArrayList<Entity>();
        initialisePropertiesTable();
        initialiseActionsTable();
        multipleSelection = false;
    }

    static void initialisePropertiesTable() {
        propertiesTable = new Table(GlobalUI.skin);
        propertiesTable.setBounds(1200, Gdx.graphics.getHeight() / 5, 500, 300);
        propertiesTable.setVisible(false);

        Global.stage.addActor(propertiesTable);
    }

    static void initialiseActionsTable() {
        actionsTable = new Table(GlobalUI.skin);
        actionsTable.setBounds(Gdx.graphics.getWidth() / 2 - 200, 100, 400, 200);
        actionsTable.setVisible(false);

        Global.stage.addActor(actionsTable);
    }
    
    void initialise() {
        this.fullName = "n/a";
        this.listIndex = -1;
        this.gridCoordinates = new Vector3();
        this.sceneCoordinates = new Vector3();
        this.modelSize = new Vector3();
        this.artificial = false;
        this.selected = false;
        this.healthPoints = 0;
        this.radiusOfSight = 0;
        this.initialHealthPoints = 0;
        this.pointLightSceneCoordinates = new Vector3();
        this.listeners = new ArrayList<ClickListener>();
    }

    Entity() {
        this.initialise();
    }

    /** CREATING AND SETTING UP */

    public static Entity addInstance(Vector3 gridCoordinates, boolean artificial) {
        Entity entity = new Entity();
        entity.setUp(gridCoordinates, artificial);
        return entity;
    }

    public void setUp(Vector3 gridCoordinates, boolean artificial) {
        this.gridCoordinates = gridCoordinates.cpy();
        this.artificial = artificial;
        list.add(this);
        this.listIndex = list.size() - 1;
        if (artificial) aiList.add(this);
        if (!artificial) userList.add(this);
        this.setUpSettableProperties();
        this.setUpModel();
        this.setUpPointLight();
        if (!this.artificial) this.setUpListeners();
        this.informCreated();
    }

    void setUpModel() {
        this.model = TurnBasedGame.gameScreen.modelLoader.loadModel(Gdx.files.internal("ACTORS/ENTITY/" + className + "/MODELS/model" + this.artificial + ".g3db"));
        this.modelInstance = new ModelInstance(this.model);
        this.updateSceneCoordinates();
        this.updateModelPosition();
    }

    void setUpPointLight() {
        if (this.artificial) {
            TurnBasedGame.gameScreen.environment.add(
                    this.sightRange = new PointLight().set(new Color(0.5f, 0.2f, 0.2f, 1f), null, 30)
            );
        } else {
            TurnBasedGame.gameScreen.environment.add(
                    this.sightRange = new PointLight().set(new Color(0.2f, 0.2f, 0.5f, 1f), null, 30)
            );
        }
        this.updatePointLight();
    }

    void setUpListeners() {
        this.listeners.add(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        die(false);
                    }
                }
        );
    }

    void setUpSettableProperties() {
        this.fullName = className + "_" + this.listIndex;
        this.modelSize.set(1, 2, 1);
        this.radiusOfSight = 5;
        this.healthPoints = this.initialHealthPoints = 100;
    }

    public static void setUp() {
        setUpPropertiesTable();
    }

    static void setUpPropertiesTable() {
        propertiesTable.reset();
        propertiesTable.add(
                Log.addInstance("ENTITY_PROPERTIES_TABLE_FULL_NAME", "n/a", "font32", Color.GOLD)
        ).left().row();
        propertiesTable.add(
                Log.addInstance("ENTITY_PROPERTIES_TABLE_HEALTH_POINTS", "n/a", "font16", Color.WHITE)
        ).left().row();
        propertiesTable.add(
                Log.addInstance("ENTITY_PROPERTIES_TABLE_ARTIFICIAL", "n/a", "font16", Color.WHITE)
        ).left().row();
        propertiesTable.add(
                Log.addInstance("ENTITY_PROPERTIES_TABLE_GRID_COORDINATES", "n/a", "font16", Color.WHITE)
        ).left().row();
        propertiesTable.add(
                Log.addInstance("ENTITY_PROPERTIES_TABLE_RANGE_OF_SIGHT", "n/a", "font16", Color.WHITE)
        ).left().row();

    }

    /** UPDATING */

    public static void updateInstances() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).update();
        }
    }

    public void update() {

    }

    void updateSceneCoordinates() {
        this.sceneCoordinates = Geometry.getSceneCoordinates(this.gridCoordinates);
    }

    void updateModelPosition() {
        this.modelInstance.transform.setToTranslation(this.sceneCoordinates);
        this.updateModelBoundingBox();
    }

    static Vector3 boundsMax = new Vector3();
    static Vector3 boundsMin = new Vector3();
    void updateModelBoundingBox() {
        boundsMax.x = this.sceneCoordinates.x + this.modelSize.x / 2;
        boundsMax.y = this.sceneCoordinates.y + this.modelSize.y / 2;
        boundsMax.z = this.sceneCoordinates.z + this.modelSize.z / 2;
        boundsMin.x = this.sceneCoordinates.x - this.modelSize.x / 2;
        boundsMin.y = this.sceneCoordinates.y - this.modelSize.y / 2;
        boundsMin.z = this.sceneCoordinates.z - this.modelSize.z / 2;
        this.modelInstance.bounds.set(boundsMin, boundsMax);
    }

    void updatePointLight() {
        this.updatePointLightSceneCoordinates();
        this.sightRange.setPosition(this.pointLightSceneCoordinates);
    }

    void updatePointLightSceneCoordinates() {
        this.pointLightSceneCoordinates.x = this.sceneCoordinates.x;
        this.pointLightSceneCoordinates.y = this.sceneCoordinates.y + 3;
        this.pointLightSceneCoordinates.z = this.sceneCoordinates.z;
    }

    void updatePropertiesTable() {
        Log.getLog("ENTITY_PROPERTIES_TABLE_FULL_NAME").setText(this.fullName);
        Log.getLog("ENTITY_PROPERTIES_TABLE_HEALTH_POINTS").setText("health points: " + this.healthPoints + " of " + this.initialHealthPoints);
        if (this.artificial) {
            Log.getLog("ENTITY_PROPERTIES_TABLE_ARTIFICIAL").setText("AI driven");
        } else {
            Log.getLog("ENTITY_PROPERTIES_TABLE_ARTIFICIAL").setText("User driven");
        }
        Log.getLog("ENTITY_PROPERTIES_TABLE_GRID_COORDINATES").setText("grid coordinates: " + this.gridCoordinates);
        Log.getLog("ENTITY_PROPERTIES_TABLE_RANGE_OF_SIGHT").setText("range of sight: " + this.radiusOfSight);
    }

    void setUpActionsTable() {
        actionsTable.reset();
        actionsTable.add(
                Button.addInstance("ENTITY_ACTIONS_TABLE_DIE", "KILL", 30, listeners.get(0))
        );
        System.gc();
    }

    /** INTERACTING */

    public void die(boolean byArtificial) {
        if (this.artificial) aiList.remove(this);
        if (!this.artificial) userList.remove(this);
        if (this.selected) this.deselect(byArtificial);
        list.remove(this);
        this.dispose();
        this.informDied();

        User.interactedWithEntity = true;
    }

    public void select(boolean byArtificial) {
        if (!this.selected) {
            this.selected = true;
            propertiesTable.setVisible(true);
            this.updatePropertiesTable();
            if (!this.artificial) {
                actionsTable.setVisible(true);
                this.setUpActionsTable();
            }
            this.informSelected(byArtificial);
        }
    }

    public void deselect(boolean byArtificial) {
        if (this.selected) {
            this.selected = false;
            propertiesTable.setVisible(false);
            if (!this.artificial) actionsTable.setVisible(false);
            this.informDeselected(byArtificial);
        }
    }

    /** GETTERS / SETTERS */

    public static Entity getEntity(String fullName) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).fullName.equals(fullName)) return list.get(i);
        }

        return null;
    }

    public static Entity getSelectedEntity() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).selected) return list.get(i);
        }

        return null;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Vector3 getGridCoordinates() { return this.gridCoordinates.cpy(); }

    public Vector3 getSceneCoordinates() {
        return this.sceneCoordinates.cpy();
    }

    public int getRadiusOfSight() {
        return this.radiusOfSight;
    }

    public ModelInstance getModelInstance() {
        return this.modelInstance;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public boolean isArtificial() {
        return this.artificial;
    }

    /** RENDERING */

    public static void renderInstances() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).render();
        }
    }

    public void render() {
        Renderer.renderModelInstance(modelInstance);
    }

    /** DISPOSING / RESETTING */

    public static void disposeClass() {
        disposeInstances();

        list.clear();
        userList.clear();
        aiList.clear();
    }

    static void disposeInstances() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).dispose();
        }
    }

    public void dispose() {
        this.informDisposed();

        this.model.dispose();
        TurnBasedGame.gameScreen.environment.remove(this.sightRange);
    }

    /** INFORMING */

    void informCreated() {
        Console.addLine("gameConsole", this.fullName + " was spawned at " + this.gridCoordinates, Console.LineType.SPAWNED);
    }

    void informDisposed() {
        Console.addLine("gameConsole", this.fullName + " was successfully disposed!", Console.LineType.SUCCESS);
    }

    void informDied() {
        Console.addLine("gameConsole", this.fullName + " died!", Console.LineType.ERROR);
    }

    void informSelected(boolean byArtificial) {
        if (byArtificial) {
            Console.addLine("gameConsole", this.fullName + " was selected by AI", Console.LineType.WARNING);
        } else {
            Console.addLine("gameConsole", this.fullName + " was selected by User", Console.LineType.WARNING);
        }
    }

    void informDeselected(boolean byArtificial) {
        if (byArtificial) {
            Console.addLine("gameConsole", this.fullName + " was deselected by AI", Console.LineType.WARNING);
        } else {
            Console.addLine("gameConsole", this.fullName + " was deselected by User", Console.LineType.WARNING);
        }
    }
}
