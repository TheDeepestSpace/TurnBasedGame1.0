package com.turnbasedgame.game.Actors.Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.turnbasedgame.game.Actors.User.User;
import com.turnbasedgame.game.UserInterface.Actors.Button;
import com.turnbasedgame.game.UserInterface.Actors.Log;
import com.turnbasedgame.game.Utilities.Console;
import com.turnbasedgame.game.Utilities.Geometry;

/**
 * Created by Boris on 13.02.2016.
 * Project: TurnBasedGame1.0
 */
public class AttackingEntity extends Entity{
    public static String className = "ATTACKING.ENTITY";

    /* PROPERTIES */

    int killsCount;

    /* SETTABLE PROPERTIES */

    int damagePoints;

    /** INITIALISING */

    @Override
    void initialise() {
        super.initialise();

        this.killsCount = 0;
        this.damagePoints = 0;
    }

    /** CREATING AND SETTING UP */

    public static AttackingEntity addInstance(Vector3 gridCoordinates, boolean artificial) {
        AttackingEntity entity = new AttackingEntity();
        entity.setUp(gridCoordinates, artificial);
        return entity;
    }

    AttackingEntity() {super();}

    @Override
    void setUpListeners() {
        super.setUpListeners();

        listeners.add(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        selectTarget();
                    }
                }
        );
    }

    @Override
    void setUpSettableProperties() {
        super.setUpSettableProperties();

        this.fullName = className + "_" + this.listIndex;
        this.damagePoints = 10;
    }

    public static void setUp() {
        setUpPropertiesTable();
    }

    static void setUpPropertiesTable() {
        propertiesTable.add(
                Log.addInstance("ENTITY_PROPERTIES_TABLE_KILLS_COUNT", "n/a", "font16", Color.WHITE)
        ).left().row();
        propertiesTable.add(
                Log.addInstance("ENTITY_PROPERTIES_TABLE_DAMAGE_POINTS", "n/a", "font16", Color.WHITE)
        ).left().row();
    }

    /** UPDATING */
    @Override
    void updatePropertiesTable() {
        super.updatePropertiesTable();

        Log.getLog("ENTITY_PROPERTIES_TABLE_KILLS_COUNT").setText("kills count: " + this.killsCount);
        Log.getLog("ENTITY_PROPERTIES_TABLE_DAMAGE_POINTS").setText("damage points: " + this.damagePoints);
    }

    @Override
    void setUpActionsTable() {
        super.setUpActionsTable();

        actionsTable.add(
                Button.addInstance("ENTITY_ACTIONS_TABLE_ATTACK", "ATTACK", 30, listeners.get(1))
        );

        System.gc();
    }

    /** INTERACTING */

    public void attack(String targetFullName) {
        if (this.canAttack(targetFullName)) {
            Entity.getEntity(targetFullName).healthPoints -= this.damagePoints;
            if (Entity.getEntity(targetFullName).selected) Entity.getEntity(targetFullName).updatePropertiesTable();

            if (Entity.getEntity(targetFullName).healthPoints <= 0) {
                Entity.getEntity(targetFullName).die(false);
                this.killsCount++;
                if (this.selected) this.updatePropertiesTable();
            }

            this.informAttacked(targetFullName);

            User.selectingEntityToAttack = false;
        }
    }

    void selectTarget() {
        User.selectingEntityToAttack = true;
        informSelectingTarget();
    }

    /** GETTERS / SETTERS */

    public boolean canAttack(String targetFullName) {
        if (Geometry.inGridRange(
                this.gridCoordinates,
                Entity.getEntity(targetFullName).gridCoordinates,
                this.radiusOfSight,
                0
        )) {
            if ((this.artificial && Entity.getEntity(targetFullName).artificial)
                    || !(this.artificial && Entity.getEntity(targetFullName).artificial)) {
                if (!this.fullName.equals(targetFullName)) {
                    return true;
                }else {
                    informTargetIsAttacker();
                    return false;
                }
            }else {
                informTargetOfTheSameTeam();
                return false;
            }
        }else {
            informTargetOutOfRange();
            return false;
        }
    }

    /** RENDERING */

    /** DISPOSING / RESETTING */

    /** INFORMING */

    void informAttacked(String targetFullName) {
        Console.addLine("gameConsole", this.fullName + " attacked " + targetFullName, Console.LineType.REGULAR);
    }

    void informTargetOutOfRange() {
        Console.addLine("gameConsole", "target is out of range", Console.LineType.ERROR);
    }

    void informTargetOfTheSameTeam() {
        if (this.artificial) Console.addLine("gameConsole", "Target is AI driven", Console.LineType.ERROR);
        else Console.addLine("gameConsole", "target is User driven", Console.LineType.ERROR);
    }

    void informTargetIsAttacker() {
        Console.addLine("gameConsole", "target cannot be attacker", Console.LineType.ERROR);
    }

    static void informSelectingTarget() {
        Console.addLine("gameConsole", "Click on entity to select it as target", Console.LineType.WARNING);
    }
}
