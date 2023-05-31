package models;

public class Levels {
    // A class to hold the game levels. Each level is a two-dimensional array. The first dimension is the
    // rows at which the aliens are going to be spawned. In the second dimension, the aliens are represented
    // by int values. Each different value represent a different type of alien. This is how the levels for the
    // original Space Invaders game was designed.

    // (1 -> red, 2 -> green, 3 -> yellow, 4 -> extra, 0 -> no alien)

    public static final int[][] LEVEL_ONE = {
            {1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1},
            {0, 3, 0, 0, 3, 0},
            {0, 0, 2, 2, 0, 0},
            {1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1},
    };
}
