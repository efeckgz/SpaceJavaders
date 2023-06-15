package items

import abstracts.AbstractGameItem
import constants.GameConstants
import engine.ImageManager.load
import engine.TimeManager.startTimer
import engine.UserManager.getHighScoreForUser
import main.Main
import java.awt.Graphics
import java.awt.event.ActionEvent
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

class Player(@JvmField val username: String) : AbstractGameItem() {
    @JvmField
    var currentHighScore: Int

    @JvmField
    var livesLeft = 3

    @JvmField
    var score = 0

    // Variables for player movement
    private var moveUp = false
    private var moveDown = false
    private var moveLeft = false
    private var moveRight = false

    init {
        currentHighScore = getHighScoreForUser(username)
        asset = load(GameConstants.PLAYER_ASSET_PATH)
        position = GameConstants.PLAYER_STARTING_POSITION // 420, 620
    }

    fun setMoveUp(moveUp: Boolean) {
        this.moveUp = moveUp
    }

    fun setMoveDown(moveDown: Boolean) {
        this.moveDown = moveDown
    }

    fun setMoveLeft(moveLeft: Boolean) {
        this.moveLeft = moveLeft
    }

    fun setMoveRight(moveRight: Boolean) {
        this.moveRight = moveRight
    }

    public override fun setIsValid() {
        isValid = livesLeft > 0
    }

    override fun setIsValid(isValid: Boolean) {
        // ignored
    }

    override val travelSpeed: Double
        get() = GameConstants.PLAYER_TRAVEL_SPEED

    public override fun updatePosition(deltaTime: Float) { // The position of player will be updated according to the user input
        val speed = travelSpeed
        if (moveUp && position.y - speed >= GameConstants.SCREEN_HEIGHT.toDouble() / 2) {
            position.y -= speed * deltaTime
        }
        if (moveDown && position.y + speed + asset!!.height <= GameConstants.SCREEN_HEIGHT - asset!!.height) {
            position.y += speed * deltaTime
        }
        if (moveLeft && position.x - speed >= 0) {
            position.x -= speed * deltaTime
        }
        if (moveRight && position.x + speed + asset!!.width <= GameConstants.SCREEN_WIDTH) {
            position.x += speed * deltaTime
        }
    }

    override fun draw(g: Graphics?) {
        g!!.drawImage(asset, position.getX().toInt(), position.getY().toInt(), null)
    }

    fun fireBullets() {
        startTimer(GameConstants.BULLET_FIRE_FREQUENCY, { e: ActionEvent? -> Bullet(this) }) { !isValid() }
    }

    // Investigate this
    private fun controlKeyPressedActionHandler(e: KeyEvent) {
        try {
            when (e.keyCode) {
                KeyEvent.VK_W -> setMoveUp(true)
                KeyEvent.VK_S -> setMoveDown(true)
                KeyEvent.VK_A -> setMoveLeft(true)
                KeyEvent.VK_D -> setMoveRight(true)
            }
        } catch (illegalStateException: IllegalStateException) {
            System.err.printf("%s", livesLeft)
            illegalStateException.printStackTrace()
        }
    }

    private fun controlKeyReleasedActionHandler(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_W -> setMoveUp(false)
            KeyEvent.VK_S -> setMoveDown(false)
            KeyEvent.VK_A -> setMoveLeft(false)
            KeyEvent.VK_D -> setMoveRight(false)
        }

        // Only run in debug mode
        if (Main.debug) {
            System.out.printf(
                    "Player location (x, y): %.2f, %.2f\n",
                    position.getX(),
                    position.getY()
            )
        }
    }

    fun handleUserInput(): KeyAdapter {
        return object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                super.keyPressed(e)
                controlKeyPressedActionHandler(e)
            }

            override fun keyReleased(e: KeyEvent) {
                super.keyReleased(e)
                controlKeyReleasedActionHandler(e)
            }
        }
    }
}
