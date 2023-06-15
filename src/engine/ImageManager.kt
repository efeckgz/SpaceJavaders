package engine

import constants.GameConstants
import items.Player
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO

object ImageManager : GameConstants {
    // Class to make loading images easier.
    private var img: BufferedImage? = null

    @JvmStatic
    fun load(path: String?): BufferedImage? {
        try {
            img = ImageIO.read(Objects.requireNonNull(ImageManager::class.java.getResource(path)))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return img
    }

    // This method loads the appropriate image to display the player's remaining lives.
    // Only called from the paintComponent() method of GamePanel.
    @JvmStatic
    fun displayLivesLeft(p: Player, g: Graphics) {
        val path: String
        try {
            path = when (p.livesLeft) {
                3 -> GameConstants.PLAYER_LIVES_THREE_ASSET_PATH
                2 -> GameConstants.PLAYER_LIVES_TWO_ASSET_PATH
                1 -> GameConstants.PLAYER_LIVES_ONE_ASSET_PATH
                else -> throw IllegalStateException(String.format("Unexpected value for player remaining lives: %s", p.livesLeft))
            }
            val livesLeftImg = load(path)
            g.color = Color.WHITE
            g.font = GameConstants.FONT_TEXT
            g.drawString("Lives: ", 15, 30)
            g.drawImage(livesLeftImg, 70, 15, null)
        } catch (ignored: IllegalStateException) {
        }
    }
}
