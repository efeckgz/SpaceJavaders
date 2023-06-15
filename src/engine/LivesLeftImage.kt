package engine

import constants.GameConstants
import engine.ImageManager.load
import items.Player
import java.awt.Color
import java.awt.image.BufferedImage

class LivesLeftImage(player: Player) : BufferedImage(1, 1, TYPE_INT_ARGB), GameConstants {
    // WORK IN PROGRESS
    init {
        val text = "Lives: "
        val g = createGraphics()
        g.font = GameConstants.FONT_TEXT
        val fm = g.fontMetrics
        val textWidth = fm.stringWidth(text)
        val textHeight = fm.height
        var lives: BufferedImage? = null
        if (player.livesLeft == 3) lives = load(GameConstants.PLAYER_LIVES_THREE_ASSET_PATH)
        if (player.livesLeft == 2) lives = load(GameConstants.PLAYER_LIVES_TWO_ASSET_PATH)
        if (player.livesLeft == 1) lives = load(GameConstants.PLAYER_LIVES_ONE_ASSET_PATH)
        if (lives != null) {
            val width = textWidth + 55 + lives.width
            val height = Math.max(textHeight, lives.height)

            // Now create the actual size of BufferedImage
            val actualSizeImage = BufferedImage(width, height, TYPE_INT_ARGB)
            val g2 = actualSizeImage.createGraphics()
            g2.color = Color.WHITE
            g2.font = GameConstants.FONT_TEXT
            g2.drawString(text, 0, fm.ascent)
            g2.drawImage(lives, textWidth + 55, 0, null)
            g.dispose()
            g2.dispose()
            data = actualSizeImage.data // Copy the data from the new image
        }
    }
}
