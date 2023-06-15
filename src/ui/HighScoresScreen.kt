package ui

import abstracts.AbstractScreen
import constants.GameConstants
import engine.UserManager.forEach
import engine.UserManager.userCount
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer

class HighScoresScreen : AbstractScreen(), GameConstants {
    private var highScoresImg: BufferedImage? = null

    init {
        if (userCount > 0) {
            highScoresImg = createHighScoresImage()
        } else {
            highScoresImg = null
        }
    }

    private fun createHighScoresImage(): BufferedImage {
        val imageHeight = userCount * 60
        val img = BufferedImage(GameConstants.SCREEN_WIDTH, imageHeight, BufferedImage.TYPE_INT_ARGB)
        val g = img.createGraphics()
        g.font = GameConstants.FONT_SUBTITLE
        val index = AtomicInteger(0)
        forEach(true, Consumer { user: Array<String?> ->
            val username = user[0]
            val userHighScoreStr = user[2]
            val usernameHighScorePairing = String.format("%s %s", username, userHighScoreStr)
            val usernameHighScorePairingWidth = g.fontMetrics.stringWidth(usernameHighScorePairing)
            g.drawString(usernameHighScorePairing, GameConstants.SCREEN_WIDTH / 2 - usernameHighScorePairingWidth / 2, (index.getAndIncrement() + 1) * 60)
        })
        g.dispose()
        return img
    }

    override fun drawItems(g: Graphics?) {
        g!!.color = Color.WHITE
        g.font = GameConstants.FONT_TITLE
        val highScores = "High Scores"
        val highScoresWidth = g.fontMetrics.stringWidth(highScores)
        g.drawString(highScores, width / 2 - highScoresWidth / 2, 150)
        if (highScoresImg != null) {
            val x = (width - highScoresImg.width) / 2
            val y = (height - highScoresImg.height) / 2
            g.drawImage(highScoresImg, x, y, null)
        } else {
            g.font = GameConstants.FONT_SUBTITLE
            val noHighScores = "No high scores yet!"
            val noHighScoresWidth = g.fontMetrics.stringWidth(noHighScores)
            g.drawString(noHighScores, width / 2 - noHighScoresWidth / 2, height / 2)
        }
    }
}
