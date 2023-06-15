package threads;

import constants.GameConstants;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class UpdateScreensThread implements Runnable, GameConstants {
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final JFrame frame;

    public UpdateScreensThread(JFrame frame) {
        this.frame = frame;
    }

    public void start() {
        running.set(true);
        new Thread(this).start();
    }

    public void stop() {
        running.set(false);
    }

    @Override
    public void run() {
        long lastUpdateTime = System.currentTimeMillis();
        while (running.get()) {
            long now = System.currentTimeMillis();
            long deltaTime = now - lastUpdateTime;

            JPanel currentScreen = (JPanel) frame.getContentPane().getComponent(0);
            if (deltaTime >= GAME_UPDATE_RATE) {
                currentScreen.repaint();
            } else {
                try {
                    Thread.sleep(GAME_UPDATE_RATE - deltaTime);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }
    }
}
