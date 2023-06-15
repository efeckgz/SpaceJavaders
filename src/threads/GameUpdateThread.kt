package threads

import abstracts.AbstractGameItem.Companion.updateAllPositions
import constants.GameConstants
import ui.GameScreen
import java.util.concurrent.atomic.AtomicBoolean
import javax.swing.SwingUtilities

class GameUpdateThread(private val gameScreen: GameScreen) : Runnable, GameConstants {
    // AtomicBoolean was used to control the running state across threads safely.
    private val running = AtomicBoolean(false)

    @JvmField
    var gameOverAction: Runnable? = null // This runs when the player dies.

    fun start() {
        running.set(true)
        Thread(this).start()
    }

    fun stop() {
        running.set(false)
    }

    override fun run() {
        var lastUpdateTime = System.currentTimeMillis()
        var updateCount = 0
        while (running.get()) {
            val now = System.currentTimeMillis()
            val deltaTime = now - lastUpdateTime
            if (deltaTime >= GameConstants.GAME_UPDATE_RATE) {
                /* If the time between the last two updates is greater than or equal to the desired
                 update rate, it is time to update the game.*/
                updateAllPositions(deltaTime.toFloat())
                gameScreen.detectCollisions()
                updateCount += 1
                lastUpdateTime = now
            } else {
                /* If not, the game was updated recently enough so wait until it is time to update
                 again.*/
                try {
                    Thread.sleep(GameConstants.GAME_UPDATE_RATE - deltaTime)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

            // render the screen after every update has completed.
            SwingUtilities.invokeLater { gameScreen.updateComponent() }
        }
    }
}
