package threads

import constants.GameConstants
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class StarField(width: Int, height: Int) : BufferedImage(width, height, TYPE_INT_RGB), Runnable, GameConstants {
    private val running = AtomicBoolean(false)
    private var yOffset: Int

    init {
        createStarField()
        yOffset = getHeight()
    }

    fun start() {
        running.set(true)
        Thread(this).start()
    }

    private fun createStarField() {
        val g = createGraphics()
        g.color = Color.BLACK
        g.fillRect(0, 0, width, height)
        g.color = Color.WHITE
        val random = Random()
        var i = GameConstants.STAR_FIELD_OFFSET
        while (i < width - GameConstants.STAR_FIELD_OFFSET) {
            var j = GameConstants.STAR_FIELD_STEP
            while (j < height - GameConstants.STAR_FIELD_OFFSET) {
                if (random.nextFloat() < 0.3) g.fillOval(i, j, 3, 3)
                j += GameConstants.STAR_FIELD_STEP
            }
            i += GameConstants.STAR_FIELD_STEP
        }
        g.dispose()
    }

    fun animate(deltaTime: Float) {
        yOffset = (yOffset - GameConstants.STAR_FIELD_SPEED * deltaTime).toInt()
        if (yOffset < 0) yOffset = height
    }

    fun draw(g: Graphics, x: Int, y: Int) {
        val firstPartHeight = height - yOffset
        val secondPartHeight = yOffset

        // Draw the first part
        g.drawImage(
                this,
                x,  // destination rectangle x1
                y,  // destination rectangle y1
                width,  // destination rectangle x2
                y + firstPartHeight,  // destination rectangle y2
                0,  // source rectangle x1
                secondPartHeight,  // source rectangle y1
                width,  // source rectangle x2
                height,  // source rectangle y2
                null
        )

        // Draw the second part
        g.drawImage(
                this,
                x,  // destination rectangle x1
                y + firstPartHeight,  // destination rectangle y1
                width,  // destination rectangle x2
                y + height,  // destination rectangle y2
                0,  // source rectangle x1
                0,  // source rectangle y1
                width,  // source rectangle x2
                yOffset,  // source rectangle y2
                null
        )
    }

    override fun run() {
        var lastUpdateTime = System.currentTimeMillis()
        while (running.get()) {
            val now = System.currentTimeMillis()
            val deltaTime = now - lastUpdateTime
            animate(deltaTime.toFloat())
            lastUpdateTime = now
            try {
                Thread.sleep(GameConstants.GAME_UPDATE_RATE)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}
