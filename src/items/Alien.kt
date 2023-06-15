package items

import abstracts.AbstractGameItem
import constants.GameConstants
import engine.ImageManager.load
import java.awt.Graphics
import java.awt.geom.Point2D

open class Alien(val isStrong: Boolean, private val player: Player) : AbstractGameItem(), GameConstants {
    @JvmField
    var livesLeft: Int

    init {
        livesLeft = if (isStrong) 2 else 1 // this does not work for some reason
        aliens.add(this)
    }

    override val travelSpeed: Double
        get() = GameConstants.ALIEN_TRAVEL_SPEED

    public override fun updatePosition(deltaTime: Float) {
        val pos = position
        val newY = pos.getY() + travelSpeed * deltaTime
        position = Point2D.Double(pos.getX(), newY)

//        if (newY >= SCREEN_HEIGHT) {
//            player.setLivesLeft(player.getLivesLeft() - 1);
//            setIsValid(false);
//            System.out.printf("Yes: %s\n", this.getClass().getName());
//        }
    }

    override fun draw(g: Graphics?) {
        if (isValid()) {
            g!!.drawImage(asset, position.getX().toInt(), position.getY().toInt(), null)
        }
    }

    public override fun setIsValid() {
        isValid = livesLeft > 0
    }

    override fun setIsValid(isValid: Boolean) {
        // ignored
    }

    class RedAlien(isStrong: Boolean, player: Player) : Alien(isStrong, player) {
        init {
            asset = load(GameConstants.RED_ALIEN_ASSET_PATH)
        }
    }

    class GreenAlien(isStrong: Boolean, player: Player) : Alien(isStrong, player) {
        init {
            asset = load(GameConstants.GREEN_ALIEN_ASSET_PATH)
        }
    }

    class YellowAlien(isStrong: Boolean, player: Player) : Alien(isStrong, player) {
        init {
            asset = load(GameConstants.YELLOW_ALIEN_ASSET_PATH)
        }
    }

    class ExtraAlien(isStrong: Boolean, player: Player) : Alien(isStrong, player) {
        init {
            asset = load(GameConstants.EXTRA_ALIEN_ASSET_PATH)
        }
    }

    companion object {
        @JvmField
        val aliens = ArrayList<Alien>()
    }
}
