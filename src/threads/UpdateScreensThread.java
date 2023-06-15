package threads;

import java.util.concurrent.atomic.AtomicBoolean;

public class UpdateScreensThread implements Runnable {
    private final AtomicBoolean running = new AtomicBoolean(false);

    public void start() {
        running.set(true);
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (running.get()) {
            
        }
    }
}
