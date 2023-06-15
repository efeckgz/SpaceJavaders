package ui

import abstracts.AbstractScreen
import constants.GameConstants
import engine.ImageManager.load
import engine.TimeManager.startTimer
import ui.LoginRegisterDialog.Companion.currentUsername
import java.awt.Color
import java.awt.Graphics
import java.awt.event.ActionEvent
import java.awt.image.BufferedImage
import java.util.*

class StartScreen : AbstractScreen(), GameConstants {
    private val allAliens: BufferedImage
    private var titleColor1 = "#f14f50"
    private var titleColor2 = "#50d070"
    private var active = Color.WHITE
    private var inactive = Color.BLACK

    init {

        // synchronize title colors
        // handle user not logged in flashing
        startTimer(1000 / 3) { e: ActionEvent? ->
            cycleTitleColors()
            cycleLoggedInActive()
        }

        // load alien assets and add them to the array
        val redAlien = load(GameConstants.RED_ALIEN_ASSET_PATH)
        val greenAlien = load(GameConstants.GREEN_ALIEN_ASSET_PATH)
        val extraAlien = load(GameConstants.EXTRA_ALIEN_ASSET_PATH)
        val yellowALien = load(GameConstants.YELLOW_ALIEN_ASSET_PATH)
        val aliens = arrayOf(redAlien, greenAlien, extraAlien, yellowALien)

        // Use the aliens array to create a BufferedImage that contains all the aliens assets
        // with a horizontal distance of 60 pixels.

        // Calculate total width needed
        var totalWidth = (aliens.size - 1) * 30 // spacing
        for (alien in aliens) {
            totalWidth += alien!!.width // add width of each alien
        }

        // Create a new BufferedImage with the required width and a height equal to the tallest alien
        val maxAlienHeight = Arrays.stream(aliens).mapToInt { obj: BufferedImage? -> obj!!.height }.max().asInt
        allAliens = BufferedImage(totalWidth, maxAlienHeight, BufferedImage.TYPE_INT_ARGB)
        val g2 = allAliens.createGraphics()

        // Draw the aliens onto the new image
        var currentX = 0
        for (alien in aliens) {
            g2.drawImage(alien, currentX, 0, null)
            currentX += alien!!.width + 30 // move to next position
        }
        g2.dispose() // Always dispose of Graphics objects when you're done with them
    }

    // method to cycle the title colors - this is called in a timer
    private fun cycleTitleColors() {
        val temp = titleColor1
        titleColor1 = titleColor2
        titleColor2 = temp
    }

    private fun cycleLoggedInActive() {
        if (!LoginRegisterDialog.LOGGED_IN) {
            val temp = active
            active = inactive
            inactive = temp
        } else {
            active = Color.WHITE // set the active color to white if the user is logged in
        }
    }

    override fun drawItems(g: Graphics?) {
        // titles
        g!!.color = Color.decode(titleColor1)
        g.font = GameConstants.FONT_TITLE
        val spaceJavaders = "Space Javaders"
        val spaceJavadersWidth = g.fontMetrics.stringWidth(spaceJavaders)
        g.drawString(spaceJavaders, width / 2 - spaceJavadersWidth / 2, 300)
        g.color = Color.decode(titleColor2)
        g.font = GameConstants.FONT_SUBTITLE
        val bytecodeBattle = "Bytecode Battle"
        val bytecodeBattleWidth = g.fontMetrics.stringWidth(bytecodeBattle)
        g.drawString(bytecodeBattle, width / 2 - bytecodeBattleWidth / 2, 350)

        // draws the aliens
        g.drawImage(allAliens, width / 2 - allAliens.width / 2, 375, null)
        val loggedInMessage = if (LoginRegisterDialog.LOGGED_IN) String.format("Logged in as %s.", currentUsername) else "User not logged in!"
        val loggedInMessageWidth = g.fontMetrics.stringWidth(loggedInMessage)
        g.color = active
        g.drawString(loggedInMessage, width / 2 - loggedInMessageWidth / 2, 625)
    }
}
