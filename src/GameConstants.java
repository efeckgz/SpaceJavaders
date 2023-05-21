//public enum GameConstants {
//    SCREEN_WIDTH(919),
//    SCREEN_HEIGHT(687),
//    GAME_FPS(120),
//    PLAYER_TRAVEL_SPEED(10),
//    PLAYER_HP(100),
//    BULLET_WIDTH(6),
//    BULLET_HEIGHT(25),
//    BULLET_TRAVEL_SPEED(4),
//    BULLET_FIRE_FREQUENCY(500),
//    ALIEN_WIDTH(35),
//    ALIEN_HEIGHT(55),
//    ALIEN_PADDING(10),
//    LOGGED_IN(0); // 0 for false, 1 for true.
//
//    private final int value;
//
//    GameConstants(int value) {
//        this.value = value;
//    }
//
//    public int getValue() {
//        return value;
//    }
//}

public class GameConstants {
    // Window constants
    public static final int SCREEN_WIDTH = 919;
    public static final int SCREEN_HEIGHT = 687;
    public static final int GAME_FPS = 120;

    // Player constants
    public static final int PLAYER_TRAVEL_SPEED = 10;
    public static final int PLAYER_HP = 100;

    // Bullet constants
    public static final int BULLET_WIDTH = 6;
    public static final int BULLET_HEIGHT = 25;
    public static final int BULLET_TRAVEL_SPEED = 4;
    public static final int BULLET_FIRE_FREQUENCY = 500;

    // Alien constants
    public static final int ALIEN_WIDTH = 35;
    public static final int ALIEN_HEIGHT = 55;
    public static final int ALIEN_PADDING = 10;
    public static final int ALIEN_TRAVEL_SPEED = 2;

    // booleans
    public static boolean LOGGED_IN = false; // field not final because logged in status mis to change in the program

}