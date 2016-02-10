package com.turnbasedgame.game.Utilities;

import com.badlogic.gdx.math.Vector3;

import java.util.Scanner;

/**
 * Created by Boris on 10.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Scanning {
    public static Vector3 nextVector3(Scanner scanner) {
        Vector3 vector3 = new Vector3(0, 0, 0);

        if (scanner.hasNext("/v3")) {
            scanner.next("/v3");
            vector3.x = scanner.nextFloat();
            scanner.next(",");
            vector3.y = scanner.nextFloat();
            scanner.next(",");
            vector3.z = scanner.nextFloat();
            scanner.next("v3/");
        }

        return vector3;

    }
}
