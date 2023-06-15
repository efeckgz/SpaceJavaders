package abstracts

import constants.GameConstants
import threads.StarField
import java.awt.Color
import java.awt.Graphics
import java.awt.event.ActionEvent
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.image.BufferedImage
import javax.swing.JPanel
import javax.swing.Timer

abstract class AbstractScreen : JPanel(), GameConstants {
    // An abstract class that provides basic functionality that all the screens in the game need.
    // It initializes the star field, automatically updates itself and provides a method for drawing items.
    private var starField: StarField? = null // Every screen has the star field drawn and animated.
    private var timer: Timer? = null

    // A buffer to draw all items on to.
    // All the game objects will be drawn onto this buffer
    // and then this buffer will be drawn onto the screen.
    private var buffer: BufferedImage? = null

    init {
        // Constructor code inside this method. This pattern allows inheritors
        // to accept constructor parameters.
        initialize()
    }

    private fun initialize() {
        isFocusable = true
        requestFocusInWindow()
        background = Color.BLACK
        addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent) {
                super.componentResized(e)
                if (buffer == null) {
                    buffer = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
                }
                if (starField == null) {
                    starField = StarField(width, height)
                    starField!!.start()
                }
            }
        })
        timer = Timer(GameConstants.GAME_UPDATE_RATE.toInt()) { e: ActionEvent? ->
//            if (starField != null) starField.animate((float) GAME_UPDATE_RATE);
            updateComponent()
        }
    }

    fun startTimer() {
        timer!!.start()
    }

    fun stopTimer() {
        if (timer!!.isRunning) timer!!.stop()
    }

    fun updateComponent() {
        // revalidate();
        repaint()
    }

    // Override this method to do screen specific drawing
    // This gets called in JPanel.paintComponent().
    protected abstract fun drawItems(g: Graphics?)
    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        // These are sometimes null when the component first gets created.
        if (buffer == null || starField == null) return
        val bufferGraphics = buffer!!.createGraphics() // graphics for the buffer

        // Clear the buffer
        bufferGraphics.color = Color.BLACK
        bufferGraphics.fillRect(0, 0, buffer!!.width, buffer!!.height)
        starField!!.draw(bufferGraphics, 0, 0)
        drawItems(bufferGraphics)
        bufferGraphics.dispose()
        g.drawImage(buffer, 0, 0, null)
    }
}
