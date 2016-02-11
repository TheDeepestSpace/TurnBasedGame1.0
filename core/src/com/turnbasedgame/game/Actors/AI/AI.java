package com.turnbasedgame.game.Actors.AI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.math.Vector2;
import com.turnbasedgame.game.Utilities.Console;

import java.io.Reader;

/**
 * Created by Boris on 11.02.2016.
 * Project: TurnBasedGame1.0
 */
public class AI {

    BehaviorTree<AI> behaviorTree;

    /** INITIALISING */

    public void initialise() {
        this.createConsole();
        this.parseBehaviorTree();

        this.informAIInitialised();
    }

    void parseBehaviorTree() {
        Reader reader = Gdx.files.internal("AI/behavior_tree.btree").reader();
        BehaviorTreeParser<AI> parser = new BehaviorTreeParser<AI>(BehaviorTreeParser.DEBUG_NONE);
        behaviorTree = parser.parse(reader, this);

        this.informBehaviorTreeParsed();
    }

    void createConsole() {
        Console.addInstance("ai", new Vector2(20, Gdx.graphics.getHeight() / 2), 0, 9000);
    }

    public void doTurn() {
        behaviorTree.step();
    }

    /** INFORMING */

    void informAIInitialised() {
        Console.addLine("gameConsole", "AI was successfully initialised!", Console.LineType.INITIALISED);
    }

    void informBehaviorTreeParsed() {
        Console.addLine("ai", "AI Behavior tree was successfully parsed!", Console.LineType.SUCCESS);
    }
}
