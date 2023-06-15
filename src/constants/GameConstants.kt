package constants;

import engine.FontManager;

import java.awt.*;
import java.awt.geom.Point2D;

public interface GameConstants {
    // This interface provides all kinds of constant values necessary in the program. Every class
    // implement this interface either directly or via its super class so that there is no need to
    // make a call to the constants.GameConstants namespace all the time.

    // Window constants
    int SCREEN_WIDTH = 919;
    int SCREEN_HEIGHT = 687;
    int GAME_FPS = 60;
    long GAME_UPDATE_RATE = 1000 / GAME_FPS;
    String WINDOW_TITLE = "Space Javaders: Bytecode Battle";

    // Player constants
    double PLAYER_TRAVEL_SPEED = 0.8;
    String PLAYER_ASSET_PATH = "/Assets/player.png";
    String PLAYER_LIVES_THREE_ASSET_PATH = "/Assets/livesThree.png";
    String PLAYER_LIVES_TWO_ASSET_PATH = "/Assets/livesTwo.png";
    String PLAYER_LIVES_ONE_ASSET_PATH = "/Assets/livesOne.png";
    Point2D.Double PLAYER_STARTING_POSITION = new Point2D.Double(420, 620);

    // Bullet constants
    int BULLET_WIDTH = 6;
    int BULLET_HEIGHT = 25;
    int BULLET_TRAVEL_SPEED = 1;
    int BULLET_FIRE_FREQUENCY = 300;

    // Alien constants
    int ALIEN_WIDTH = 35;
    int ALIEN_HEIGHT = 55;
    int ALIEN_PADDING = 15;
    double ALIEN_TRAVEL_SPEED = 0.035;
    String RED_ALIEN_ASSET_PATH = "/Assets/red.png";
    String GREEN_ALIEN_ASSET_PATH = "/Assets/green.png";
    String YELLOW_ALIEN_ASSET_PATH = "/Assets/yellow.png";
    String EXTRA_ALIEN_ASSET_PATH = "/Assets/extra.png";

    // Star field constants
    int STAR_FIELD_STEP = 30;
    int STAR_FIELD_OFFSET = 7;
    double STAR_FIELD_SPEED = 0.2;

    // Font
    String FONT_PATH = "/Assets/upheavtt.ttf";
    float FONT_TITLE_SIZE = 100;
    float FONT_SUBTITLE_SIZE = 32;
    float FONT_TEXT_SIZE = 16;
    Font FONT_TITLE = FontManager.getFontTitle();
    Font FONT_SUBTITLE = FontManager.getFontSubtitle();
    Font FONT_TEXT = FontManager.getFontText();
}