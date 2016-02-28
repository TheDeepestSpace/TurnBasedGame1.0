package com.turnbasedgame.game.Utilities;

import com.turnbasedgame.game.Actors.Actors;

/**
 * Created by Boris on 11.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Game {
    static boolean aiTurn;

    public static void start() {
        informGameStarted();
        aiTurn = true;

        startTurn();
    }

    static void informGameStarted() {
        Console.addLine("gameConsole", "Game started!", Console.LineType.SUCCESS);
    }

    public static void finish() {
        informGameFinished();
    }

    static void informGameFinished() {
        Console.addLine("gameConsole", "Game finished!", Console.LineType.ERROR);
    }

    public static void startTurn() {
        if (aiTurn) {
            informAIStartedTurn();
            Actors.gameAI.doTurn();
        }
        else informUserStartedTurn();
    }

    static void informAIStartedTurn() {
        Console.addLine("gameConsole", "AI started turn!", Console.LineType.WARNING);
    }

    static void informUserStartedTurn() {
        Console.addLine("gameConsole", "User started turn!", Console.LineType.WARNING);
    }

    public static void finishTurn() {
        if (aiTurn) informAIFinishedTurn();
        else informUserFinishedTurn();

        goToNextTurn();
    }

    static void informAIFinishedTurn() {
        Console.addLine("gameConsole", "AI finished turn!", Console.LineType.WARNING);
    }

    static void informUserFinishedTurn() {
        Console.addLine("gameConsole", "User finished turn!", Console.LineType.WARNING);
    }

    static void goToNextTurn() {
        aiTurn = !aiTurn;

        Console.addLine("gameConsole", "----------------------------------------------", Console.LineType.ERROR);
        startTurn();
    }
}
