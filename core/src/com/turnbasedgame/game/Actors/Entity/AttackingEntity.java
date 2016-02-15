package com.turnbasedgame.game.Actors.Entity;

/**
 * Created by Boris on 13.02.2016.
 * Project: TurnBasedGame1.0
 */
public class AttackingEntity extends Entity{
    public static String className = "ATTACKING_ENTITY";

    /* PROPERTIES */

    String targetFullName;

    int killsCount;

    /* SETTABLE PROPERTIES */

    int damagePoints;

    /** INITIALISING */

    @Override
    void initialise() {
        super.initialise();

        this.targetFullName = "n/a";

        this.killsCount = 0;

        this.damagePoints = 0;
    }

    /** CREATING AND SETTING UP */

    AttackingEntity() {super();}

    /** UPDATING */

    /** INTERACTING */

    /** GETTERS / SETTERS */

    /** RENDERING */

    /** DISPOSING / RESETTING */
}
