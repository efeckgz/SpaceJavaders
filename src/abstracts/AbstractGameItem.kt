package abstracts

import constants.GameConstants
import java.awt.Graphics
import java.awt.geom.Point2D
import java.awt.image.BufferedImage
import java.util.concurrent.CopyOnWriteArrayList

abstract class AbstractGameItem : GameConstants {
    // A Point2D instance holds the position of each GameItem. This one here uses the double data type.
    @JvmField
    var position = Point2D.Double()

    // The asset of the item
    var asset: BufferedImage? = null
        protected set

    @JvmField
    protected var isValid // boolean value to check if the item should be drawn and checked for collision
            = true

    init {
        items.add(this)
    }

    fun isValid(): Boolean {
        return isValid
    }

    protected abstract fun setIsValid()
    protected abstract fun setIsValid(isValid: Boolean)
    abstract val travelSpeed: Double

    // Calculate the changes in items position and update it accordingly using setPosition.
    protected abstract fun updatePosition(deltaTime: Float)

    // Method for collision detection.
    fun intersects(other: AbstractGameItem): Boolean {
        val x1 = position.x
        val y1 = position.y
        val w1 = asset!!.width.toDouble()
        val h1 = asset!!.height.toDouble()
        val x2 = other.position.x
        val y2 = other.position.y
        val w2 = other.asset!!.width.toDouble()
        val h2 = other.asset!!.height.toDouble()

        // Check if bounding boxes overlap
        return x1 < x2 + w2 && x2 < x1 + w1 && y1 < y2 + h2 && y2 < y1 + h1
    }

    protected abstract fun draw(g: Graphics?)

    companion object {
        /* Every game object must extend this abstract class. The abstract class includes the position of the item
     and methods to update it. This abstract class may get bigger in the future.*/
        /* CopyOnWriteArrayList that holds all the game items in it. Loop over this ArrayList and run
     updatePosition() and draw() methods on everything.
     This is a special type of ArrayList that achieves thread safety, at the cost of system resources
     by making a copy of the ArrayList when dealing with mutative operations such as adding and removing.*/
        val items = CopyOnWriteArrayList<AbstractGameItem>()

        @JvmStatic
        fun updateAllPositions(deltaTime: Float) {
            for (item in items) {
                if (item.isValid()) {
                    item.updatePosition(deltaTime)
                }
            }
        }

        @JvmStatic
        fun drawAllItems(g: Graphics?) {
            for (item in items) item.draw(g)
        }

        @JvmStatic
        fun clearItems() {
            items.clear()
        }
    }
}
