import java.awt.geom.Point2D;

public enum GameConstants {
    SCREEN_WIDTH(919),
    SCREEN_HEIGHT(687),
    PLAYER_WIDTH(50),
    PLAYER_HEIGHT(50),
    PLAYER_TRAVEL_SPEED(10),
    BULLET_TRAVEL_SPEED(5);

    private final int value;

    GameConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
