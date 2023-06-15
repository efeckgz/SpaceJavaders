package engine

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.function.Supplier
import javax.swing.Timer

object TimeManager {
    // The following method is a simple wrapper for the Swing Timer constructor.
    // There were many parts of this program in which I needed to start a timer,
    // so I decided to create a wrapper method for it, as I think writing
    // new Timer(delay, listener).start() everywhere didn't look nice.
    @JvmStatic
    fun startTimer(delay: Int, listener: ActionListener?) {
        Timer(delay, listener).start()
    }

    // Overloaded version of this method accepts a condition as a parameter that determines weather the
    // Timer should be stopped.
    @JvmStatic
    fun startTimer(delay: Int, listener: ActionListener, stopCondition: Supplier<Boolean>) {
        Timer(delay) { e: ActionEvent ->
            if (stopCondition.get()) {
                (e.source as Timer).stop()
            } else {
                listener.actionPerformed(e)
            }
        }.start()
    }
}
