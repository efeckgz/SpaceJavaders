import javax.swing.*;
import java.awt.event.ActionListener;

public class TimeManager {
    // The following method is a simple wrapper for the Swing Timer constructor.
    // There were many parts of this program in which I needed to start a timer,
    // so I decided to create a wrapper method for it, as I think writing
    // new Timer(delay, listener).start() everywhere didn't look nice.
    public static void startTimer(int delay, ActionListener listener) {
        new Timer(delay, listener).start();
    }
}
