public enum GameConstants {
    SCREEN_WIDTH(919),
    SCREEN_HEIGHT(687),
    PLAYER_TRAVEL_SPEED(1000),
    BULLET_TRAVEL_SPEED(300);

    private final int value;

    GameConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
