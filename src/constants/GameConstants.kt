package constants

import engine.FontManager
import java.awt.geom.Point2D

interface GameConstants {
    companion object {
        // This interface provides all kinds of constant values necessary in the program. Every class
        // implement this interface either directly or via its super class so that there is no need to
        // make a call to the constants.GameConstants namespace all the time.
        // Window constants
        const val SCREEN_WIDTH = 919
        const val SCREEN_HEIGHT = 687
        private const val GAME_FPS = 60
        const val GAME_UPDATE_RATE = (1000 / GAME_FPS).toLong()
        const val WINDOW_TITLE = "Space Javaders: Bytecode Battle"

        // Player constants
        const val PLAYER_TRAVEL_SPEED = 0.8
        const val PLAYER_ASSET_PATH = "/Assets/player.png"
        const val PLAYER_LIVES_THREE_ASSET_PATH = "/Assets/livesThree.png"
        const val PLAYER_LIVES_TWO_ASSET_PATH = "/Assets/livesTwo.png"
        const val PLAYER_LIVES_ONE_ASSET_PATH = "/Assets/livesOne.png"

        @JvmField
        val PLAYER_STARTING_POSITION = Point2D.Double(420.0, 620.0)

        // Bullet constants
        const val BULLET_WIDTH = 6
        const val BULLET_HEIGHT = 25
        const val BULLET_TRAVEL_SPEED = 1
        const val BULLET_FIRE_FREQUENCY = 300

        // Alien constants
        const val ALIEN_WIDTH = 35
        const val ALIEN_HEIGHT = 55
        const val ALIEN_PADDING = 15
        const val ALIEN_TRAVEL_SPEED = 0.035
        const val RED_ALIEN_ASSET_PATH = "/Assets/red.png"
        const val GREEN_ALIEN_ASSET_PATH = "/Assets/green.png"
        const val YELLOW_ALIEN_ASSET_PATH = "/Assets/yellow.png"
        const val EXTRA_ALIEN_ASSET_PATH = "/Assets/extra.png"

        // Star field constants
        const val STAR_FIELD_STEP = 30
        const val STAR_FIELD_OFFSET = 7
        const val STAR_FIELD_SPEED = 0.2

        // Font
        const val FONT_PATH = "/Assets/upheavtt.ttf"
        const val FONT_TITLE_SIZE = 100f
        const val FONT_SUBTITLE_SIZE = 32f
        const val FONT_TEXT_SIZE = 16f

        @JvmField
        val FONT_TITLE = FontManager.getFontTitle()

        @JvmField
        val FONT_SUBTITLE = FontManager.getFontSubtitle()

        @JvmField
        val FONT_TEXT = FontManager.getFontText()
    }
}