package ui;

import abstracts.GameItem;
import abstracts.Screen;
import constants.GameConstants;
import models.Alien;
import models.Bullet;
import models.Levels;
import models.Player;
import threads.GameUpdateThread;
import utils.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class GameScreen extends Screen implements GameConstants {
    private final Alien[][] aliens;
    private final GameUpdateThread gameUpdateThread;
    private final Player player;

    public GameScreen() {
        super();

        GameItem.clearItems(); // clear the game items from the previous game

        player = new Player(LoginRegisterDialog.getCurrentUsername());
        gameUpdateThread = new GameUpdateThread(this);

        player.fireBullets(); // start shooting

        // models.Levels
        int[][] level1 = Levels.LEVEL_ONE;
        aliens = new Alien[level1.length][level1[0].length];
        for (int i = 0; i < level1.length; i++) {
            for (int j = 0; j < level1[i].length; j++) {
                Alien alienToCreate = null;  // Initialize to null
                if (level1[i][j] == 1) alienToCreate = new Alien.RedAlien(false);
                if (level1[i][j] == 2) alienToCreate = new Alien.GreenAlien(false);
                if (level1[i][j] == 3) alienToCreate = new Alien.YellowAlien(false);
                if (level1[i][j] == 4) alienToCreate = new Alien.ExtraAlien(false);

                // Assign the created alien back to the array
                aliens[i][j] = alienToCreate;

                // Assign initial position
                if (alienToCreate != null && alienToCreate.getIsAlive()) {
                    int x = j * (GameConstants.ALIEN_WIDTH + GameConstants.ALIEN_PADDING);
                    int y = i * (GameConstants.ALIEN_HEIGHT + GameConstants.ALIEN_PADDING);
                    alienToCreate.setPosition(new Point2D.Double(x, y));
                }
            }
        }

        gameUpdateThread.start();
        SwingUtilities.invokeLater(() -> addKeyListener(player.handleUserInput()));
    }

    public void detectCollisions() {
        Player player = getPlayer();
        Runnable gameOverAction = gameUpdateThread.getGameOverAction();

        for (Alien alien : Alien.getAliens()) {
            if (alien != null && alien.getIsAlive()) {
                // models.Alien exists, check collision
                if (alien.intersects(player)) {
                    player.setLivesLeft(player.getLivesLeft() - 1);
                    player.setIsAlive(player.getLivesLeft() > 0); // kill the player if their lives are gone.
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

    public Player getPlayer() {
        return player;
    }

    public GameUpdateThread getGameUpdateThread() {
        return gameUpdateThread;
    }

    public void stopGameThread() {
        if (gameUpdateThread != null) gameUpdateThread.stop();
    }

    @Override
    protected void drawItems(Graphics g) {
        GameItem.drawAllItems(g);
        ImageManager.displayLivesLeft(player, g);
//
//        LivesLeftImage livesImage = new LivesLeftImage(getPlayer());
//        g.drawImage(livesImage, 0, 0, null);


        g.setFont(FONT_TEXT);
        g.drawString(String.format("Score: %d", player.getScore()), getWidth() - 75, 25);
        g.drawString(String.format("High score: %d", player.getCurrentHighScore()), getWidth() - 118, 35);
    }
}
