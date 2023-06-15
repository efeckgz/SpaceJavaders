package items

import abstracts.AbstractGameItem
import constants.GameConstants
import engine.TimeManager.startTimer
import main.Main
import java.awt.Color
import java.awt.Graphics
import java.awt.event.ActionEvent
import java.awt.geom.Point2D
import java.awt.image.BufferedImage

class Bullet(private val player: Player) : AbstractGameItem() {
    init {

        // Place the bullet at the center of the players asset and just above it
        position = Point2D.Double(
                player.position.getX() + player.asset!!.width.toDouble() / 2 - GameConstants.BULLET_WIDTH.toDouble() / 2,
                player.position.getY() - player.asset!!.height
        )
        asset = assetSetter
        if (Main.debug) {
            startTimer(1000, { e: ActionEvent? ->
                if (isValid()) {
                    System.out.printf(
                            "models.Bullet %s location (x, y): %.2f, %.2f\n",
                            bullets.indexOf(this),
                            position.getX(),
                            position.getY()
                    )
                }
            }) { !player.isValid() }
        }
        bullets.add(this)
    }

    fun checkForCollisions(gameOverAction: Runnable) {
        if (isValid) {
            for (alien in Alien.aliens) {
                if (alien.isValid() && intersects(alien)) {
                    this.setIsValid(false) // Kill the bullet on impact
                    alien.livesLeft = alien.livesLeft - 1 // Decrease alien life
                    alien.setIsValid() // Kill the alien if it has no lives left

                    // Things to do if the alien is dead
                    if (!alien.isValid()) {
                        // increment player score
                        player.score = player.score + 1
                        player.currentHighScore = Math.max(player.currentHighScore, player.score)

                        // If all the aliens are dead, show the game over screen
                        if (Alien.aliens.stream().noneMatch { obj: Alien -> obj.isValid() }) {
                            gameOverAction.run()
                        }
                    }
                    return  // Do not keep checking if the bullet hits other aliens
                }
            }
        }
    }

    override fun setIsValid() {
        isValid = position.y > 0
    }

    public override fun setIsValid(isValid: Boolean) {
        this.isValid = isValid
    }

    override val travelSpeed: Double
        get() = GameConstants.BULLET_TRAVEL_SPEED.toDouble()

    public override fun updatePosition(deltaTime: Float) {
        position.y -= travelSpeed * deltaTime
        setIsValid()
    }

    override fun draw(g: Graphics?) {
        if (isValid()) {
            g!!.drawImage(asset, position.getX().toInt(), position.getY().toInt(), null)
        }
    }

    companion object {
        @JvmField
        val bullets = ArrayList<Bullet>() // A thread safe ArrayList
        private val assetSetter: BufferedImage? = null

        init {
            // Set the asset here
            assetSetter = BufferedImage(GameConstants.BULLET_WIDTH, GameConstants.BULLET_HEIGHT, BufferedImage.TYPE_INT_RGB)
            val g = assetSetter.createGraphics()
            g.color = Color.WHITE
            g.fillRect(0, 0, assetSetter.width, assetSetter.height)
            g.dispose()
        }
    }
}
