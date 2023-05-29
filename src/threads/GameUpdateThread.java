package threads;

import abstracts.GameItem;
import constants.GameConstants;
import models.Alien;
import models.Bullet;
import models.Player;
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

    private void detectCollisions() {
        Player player = gameScreen.getPlayer();

        for (Alien alien : Alien.getAliens()) {
            if (alien != null && alien.getIsAlive()) {
                // models.Alien exists, check collision
                if (alien.intersects(player)) {
                    if (Main.debug) System.err.print("ouch!\n");
                    player.setLivesLeft(player.getLivesLeft() - 1);
                    player.setIsAlive(() -> player.getLivesLeft() > 0); // kill the player if their lives are gone.
                    alien.setIsAlive(false);
                    if (!player.getIsAlive() && gameOverAction != null) gameOverAction.run();
                }

                for (Bullet bullet : Bullet.getBullets()) {
                    if (bullet.getIsAlive() && alien.intersects(bullet)) {
                        bullet.setIsAlive(false); // kill the bullet
                        alien.setIsAlive(false); // kill the alien
                        player.setScore(player.getScore() + 1); // increment score
                        // The maximum of the current high score and the score should be the high score
                        player.setCurrentHighScore(Math.max(player.getCurrentHighScore(), player.getScore()));
                    }
                }
            }
        }
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
                GameItem.updateAllPositions(deltaTime);
                detectCollisions();
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

            SwingUtilities.invokeLater(gameScreen::updateComponent); // render the game after every update has completed.
        }
    }
}
