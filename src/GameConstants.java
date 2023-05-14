public enum GameConstants {
    SCREEN_WIDTH(919),
    SCREEN_HEIGHT(687),
    GAME_FPS(120),
    PLAYER_TRAVEL_SPEED(10),
    PLAYER_HP(100),
    BULLET_WIDTH(6),
    BULLET_HEIGHT(25),
    BULLET_TRAVEL_SPEED(4),
    BULLET_FIRE_FREQUENCY(500),
    ALIEN_WIDTH(35),
    ALIEN_HEIGHT(55),
    ALIEN_PADDING(70),
    LOGGED_IN(0); // 0 for false, 1 for true.

    private final int value;

    GameConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
