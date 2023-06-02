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
    private final GameUpdateThread gameUpdateThread;
    private final Player player;
    private Alien[][] aliens;

    public GameScreen() {
        super();

        GameItem.clearItems(); // clear the game items from the previous game

        player = new Player(LoginRegisterDialog.getCurrentUsername());
        gameUpdateThread = new GameUpdateThread(this);

        player.fireBullets(); // start shooting

        // models.Levels
        int[][] level1 = Levels.LEVEL_ONE;
        aliens = new Alien[level1.length][level1[0].length];

        // Calculate total width of aliens and starting x position
        int totalAliensWidth = level1[0].length * (ALIEN_WIDTH + ALIEN_PADDING);
        int startX = (SCREEN_WIDTH - totalAliensWidth) / 2;

        for (int i = 0; i < level1.length; i++) {
            for (int j = 0; j < level1[i].length; j++) {
                Alien alienToCreate = null;  // Initialize to null
                if (level1[i][j] == 1) alienToCreate = new Alien.RedAlien(false);
                if (level1[i][j] == 2) alienToCreate = new Alien.GreenAlien(true);
                if (level1[i][j] == 3) alienToCreate = new Alien.YellowAlien(true);
                if (level1[i][j] == 4) alienToCreate = new Alien.ExtraAlien(false);

                // Assign the created alien back to the array
                aliens[i][j] = alienToCreate;

                // Assign initial position
                if (alienToCreate != null && alienToCreate.isValid()) {
                    int x = startX + j * (ALIEN_WIDTH + ALIEN_PADDING);
                    int y = i * (ALIEN_HEIGHT + ALIEN_PADDING);
                    alienToCreate.setPosition(new Point2D.Double(x, y));
                }
            }
        }

        gameUpdateThread.start();
        SwingUtilities.invokeLater(() -> addKeyListener(player.handleUserInput()));
    }

    public void createAliens() {
        // models.Levels
        int[][] level1 = Levels.LEVEL_ONE;
        aliens = new Alien[level1.length][level1[0].length];

        // Calculate total width of aliens and starting x position
        int totalAliensWidth = level1[0].length * (ALIEN_WIDTH + ALIEN_PADDING);
        int startX = (getWidth() - totalAliensWidth) / 2;

        for (int i = 0; i < level1.length; i++) {
            for (int j = 0; j < level1[i].length; j++) {
                Alien alienToCreate = null;  // Initialize to null
                if (level1[i][j] == 1) alienToCreate = new Alien.RedAlien(false);
                if (level1[i][j] == 2) alienToCreate = new Alien.GreenAlien(true);
                if (level1[i][j] == 3) alienToCreate = new Alien.YellowAlien(true);
                if (level1[i][j] == 4) alienToCreate = new Alien.ExtraAlien(false);

                // Assign the created alien back to the array
                aliens[i][j] = alienToCreate;

                // Assign initial position
                if (alienToCreate != null && alienToCreate.isValid()) {
                    int x = startX + j * (ALIEN_WIDTH + ALIEN_PADDING);
                    int y = i * (ALIEN_HEIGHT + ALIEN_PADDING);
                    alienToCreate.setPosition(new Point2D.Double(x, y));
                }
            }
        }
    }


//    public GameScreen() {
//        super();
//
//        GameItem.clearItems(); // clear the game items from the previous game
//
//        player = new Player(LoginRegisterDialog.getCurrentUsername());
//        gameUpdateThread = new GameUpdateThread(this);
//
//        player.fireBullets(); // start shooting
//
//        // models.Levels
//        int[][] level1 = Levels.LEVEL_ONE;
//        aliens = new Alien[level1.length][level1[0].length];
//        for (int i = 0; i < level1.length; i++) {
//            for (int j = 0; j < level1[i].length; j++) {
//                Alien alienToCreate = null;  // Initialize to null
//                if (level1[i][j] == 1) alienToCreate = new Alien.RedAlien(false);
//                if (level1[i][j] == 2) alienToCreate = new Alien.GreenAlien(true);
//                if (level1[i][j] == 3) alienToCreate = new Alien.YellowAlien(true);
//                if (level1[i][j] == 4) alienToCreate = new Alien.ExtraAlien(false);
//
//                // Assign the created alien back to the array
//                aliens[i][j] = alienToCreate;
//
//                // Assign initial position
//                if (alienToCreate != null && alienToCreate.isValid()) {
//                    int x = j * (ALIEN_WIDTH + ALIEN_PADDING);
//                    int y = i * (ALIEN_HEIGHT + ALIEN_PADDING);
//                    alienToCreate.setPosition(new Point2D.Double(x, y));
//                }
//            }
//        }
//
//        gameUpdateThread.start();
//        SwingUtilities.invokeLater(() -> addKeyListener(player.handleUserInput()));
//    }

    public void detectCollisions() {
        Player player = getPlayer();
        Runnable gameOverAction = gameUpdateThread.getGameOverAction();

        for (Alien alien : Alien.getAliens()) {
            if (alien != null && alien.isValid()) {
                // Alien exists, check collision
                if (alien.intersects(player)) {
                    player.setLivesLeft(player.getLivesLeft() - 1);
                    alien.setLivesLeft(alien.getLivesLeft() - 1);

                    player.setIsValid();
                    alien.setIsValid();

                    if (!player.isValid() && gameOverAction != null) {
                        gameOverAction.run();
                    }

                    return;
                }
            }
        }

        for (Bullet bullet : Bullet.getBullets()) {
            if (bullet.isValid()) {
                bullet.checkForCollisions();
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
