package com.turnbasedgame.game.Utilities;

import com.badlogic.gdx.math.Vector3;
import com.turnbasedgame.game.Actors.Grid.Grid;

/**
 * Created by Boris on 10.02.2016.
 * Project: TurnBasedGame1.0
 */
public class Geometry {
    public static Vector3 getSceneCoordinates(Vector3 gridCoordinates) {
        Vector3 out = new Vector3();

        if (Grid.size.x % 2 == 0) {
            out.x = gridCoordinates.x * Grid.tileSceneSize - Grid.size.x / 2 + (float) Grid.tileSceneSize / 2;
        }else {
            out.x = gridCoordinates.x * Grid.tileSceneSize - Grid.size.x / 2;
        }

        out.y = gridCoordinates.y * Grid.tileSceneSize;

        if (Grid.size.z % 2 == 0) {
            out.z = gridCoordinates.z * Grid.tileSceneSize - Grid.size.z / 2 + (float) Grid.tileSceneSize / 2;
        }else {
            out.z = gridCoordinates.z * Grid.tileSceneSize - Grid.size.z / 2;
        }

        return out;
    }

    public static void applyDisplacement(Vector3 coordinates, Vector3 displacement) {
        coordinates.x += displacement.x;
        coordinates.y += displacement.y;
        coordinates.z += displacement.z;
    }

    public static boolean inGridRange(Vector3 gridCoordinates1, Vector3 gridCoordinates2, int maxRange, int minRange) {
        float distance = gridCoordinates1.dst(gridCoordinates2);
        return distance <= maxRange && distance >= minRange;
    }
}
