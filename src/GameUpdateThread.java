import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameUpdateThread implements Runnable, GameConstants {
    private final GamePanel gamePanel;

    // AtomicBoolean was used to control the running state across threads safely.
    private final AtomicBoolean running = new AtomicBoolean(false);

    public GameUpdateThread(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
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

            if (deltaTime >= GAME_UPDATE_RATE) {
                // If the time between the last two updates is greater than or equal to the desired
                // update rate, it is time to update the game.
//                gamePanel.updateGame(deltaTime);
                GameItem.updateAllPositions(deltaTime);
                lastUpdateTime = now;
            } else {
                // If not, the game was updated recently enough so wait until it is time to update
                // again.
                try {
                    Thread.sleep(GAME_UPDATE_RATE - deltaTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            SwingUtilities.invokeLater(gamePanel::repaint);
        }
    }
}
