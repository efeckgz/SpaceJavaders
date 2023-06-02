package threads;

import abstracts.GameItem;
import constants.GameConstants;
import ui.GameScreen;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameUpdateThread implements Runnable, GameConstants {
    private final GameScreen gameScreen;
    // AtomicBoolean was used to control the running state across threads safely.
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Runnable gameOverAction; // This runs when the player dies.

    public GameUpdateThread(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void start() {
        running.set(true);
        new Thread(this).start();
    }

    public Runnable getGameOverAction() {
        return gameOverAction;
    }

    public void setGameOverAction(Runnable gameOverAction) {
        this.gameOverAction = gameOverAction;
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
                /* If the time between the last two updates is greater than or equal to the desired
                 update rate, it is time to update the game.*/
                GameItem.updateAllPositions(deltaTime);
                gameScreen.detectCollisions();
                lastUpdateTime = now;
            } else {
                /* If not, the game was updated recently enough so wait until it is time to update
                 again.*/
                try {
                    Thread.sleep(GAME_UPDATE_RATE - deltaTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // render the screen after every update has completed.
            SwingUtilities.invokeLater(gameScreen::updateComponent);
        }
    }
}
