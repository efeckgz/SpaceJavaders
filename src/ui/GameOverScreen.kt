package ui

import abstracts.AbstractScreen
import constants.GameConstants
import items.Player
import java.awt.Color
import java.awt.Graphics

class GameOverScreen(private val player: Player) : AbstractScreen(), GameConstants {
    override fun drawItems(g: Graphics?) {
        // Draw the game over text and the player score
        g!!.color = Color.WHITE
        g.font = GameConstants.FONT_TITLE
        val gameOver = "Game Over!"
        val gameOverWidth = g.fontMetrics.stringWidth(gameOver)
        g.drawString(gameOver, width / 2 - gameOverWidth / 2, 300)
        g.font = GameConstants.FONT_SUBTITLE
        val score = String.format("Score: %d", player.score)
        val scoreWidth = g.fontMetrics.stringWidth(score)
        g.drawString(score, width / 2 - scoreWidth / 2, 350)
    }
}
