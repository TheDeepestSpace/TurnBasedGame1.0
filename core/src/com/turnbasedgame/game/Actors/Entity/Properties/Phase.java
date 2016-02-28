package com.turnbasedgame.game.Actors.Entity.Properties;

import com.turnbasedgame.game.Utilities.Console;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by Boris on 26.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Phase {
    static ArrayList<Phase> list;

    String name;
    String ownerFullName;
    Status status;
    boolean instant;

    public enum Status {
        FRESH,
        ENTERED,
        RUNNING,
        ESCAPED
    }

    /** INITIALISING */

    public static void initialiseClass() {
        list = new ArrayList<Phase>();
    }

    public Phase() {
        this.name = "n/a";
        this.ownerFullName = "n/a";
        this.status = Status.FRESH;

        list.add(this);
    }

    /** CREATING AND SETTING UP */

    public Phase setUp(String name, boolean instant, String ownerFullName) {
        this.name = name;
        this.instant = instant;
        this.ownerFullName = ownerFullName;
        return this;
    }

    /** INTERACTING */

    public void enter() {
        this.informEntered();
        this.status = Status.ENTERED;
        if (this.instant) {
            this.execute();
            this.escape();
        }
    }

    public void execute(){
        this.status = Status.RUNNING;
    }

    public void escape() {
        this.informEscaped();
        this.status = Status.ESCAPED;
    }

    /** GETTERS / SETTERS */

    public Status getStatus() {
        return this.status;
    }

    public boolean getInstant() {
        return this.instant;
    }

    public void setRunning() {
        this.status = Status.RUNNING;
    }

    public static Phase getPhase(String name, String ownerFullName) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).name.equals(name) && list.get(i).ownerFullName.equals(ownerFullName)) {
                return list.get(i);
            }
        }

        return null;
    }

    /** INFORMING */

    void informEntered() {
        Console.addLine("gameConsole", this.ownerFullName + " entered " + name + " phase", Console.LineType.PHASE);
    }

    void informEscaped() {
        Console.addLine("gameConsole", this.ownerFullName + " escaped " + name + " phase", Console.LineType.PHASE);
    }
}
