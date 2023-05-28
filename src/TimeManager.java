import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.function.Supplier;

public class TimeManager {
    // The following method is a simple wrapper for the Swing Timer constructor.
    // There were many parts of this program in which I needed to start a timer,
    // so I decided to create a wrapper method for it, as I think writing
    // new Timer(delay, listener).start() everywhere didn't look nice.
    public static void startTimer(int delay, ActionListener listener) {
        new Timer(delay, listener).start();
    }

    // Overloaded version of this method accepts a condition as a parameter that determines weather the
    // Timer should be stopped.
    public static void startTimer(int delay, ActionListener listener, Supplier<Boolean> stopCondition) {
        new Timer(delay, e -> {
            if (stopCondition.get()) {
                ((Timer) e.getSource()).stop();
            } else {
                listener.actionPerformed(e);
            }
        }).start();
    }
}
