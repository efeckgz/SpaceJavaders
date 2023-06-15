package items

object Levels {
    /* A class to hold the game levels. Each level is a two-dimensional array. The first dimension is the
     rows at which the aliens are going to be spawned. In the second dimension, the aliens are represented
     by int values. Each different value represent a different type of alien. This is how the levels for the
     original Space Invaders game was designed.

     (1 -> red, 2 -> green, 3 -> yellow, 4 -> extra, 0 -> no alien)*/
    @JvmField
    val LEVEL_ONE = arrayOf(intArrayOf(1, 1, 1, 1, 1, 1), intArrayOf(1, 1, 1, 1, 1, 1), intArrayOf(0, 3, 0, 0, 3, 0), intArrayOf(0, 0, 2, 2, 0, 0), intArrayOf(1, 1, 1, 1, 1, 1), intArrayOf(1, 1, 1, 1, 1, 1))
    val LEVEL_TWO = arrayOf(intArrayOf(1, 1, 1, 1, 1, 1), intArrayOf(1, 1, 1, 1, 1, 1), intArrayOf(0, 3, 0, 0, 3, 0), intArrayOf(0, 0, 2, 2, 0, 0), intArrayOf(1, 1, 1, 1, 1, 1), intArrayOf(1, 1, 1, 1, 1, 1))
    val LEVEL_THREE = arrayOf(intArrayOf(1, 1, 1, 1, 1, 1), intArrayOf(1, 1, 1, 1, 1, 1), intArrayOf(0, 3, 0, 0, 3, 0), intArrayOf(0, 0, 2, 2, 0, 0), intArrayOf(1, 1, 1, 1, 1, 1), intArrayOf(1, 1, 1, 1, 1, 1))
    val levels = ArrayList<Array<IntArray>>()

    init {
        levels.add(LEVEL_ONE)
        levels.add(LEVEL_TWO)
        levels.add(LEVEL_THREE)
    }
}
