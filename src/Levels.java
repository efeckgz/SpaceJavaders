public class Levels {
    // A class to hold the game levels. Each level is a two dimensional array. The first dimension is the
    // rows at which the aliens are going to be spawned. In the second dimension, the aliens are represented
    // by int values. Each different value represent a different type of alien
    // (1 -> red, 2 -> green, 3 -> yellow, 4 -> extra, 0 -> no alien). This is how the levels for the original
    // Space Invaders game was designed.

    private static final int[][] level1 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
            {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4}
    };

    public static int[][] getLevel1() {
        return level1;
    }
}
