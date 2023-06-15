package ui;

import abstracts.AbstractGameItem;
import abstracts.AbstractScreen;
import constants.GameConstants;
import engine.ImageManager;
import items.Alien;
import items.Bullet;
import items.Levels;
import items.Player;
import threads.GameUpdateThread;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class GameScreen extends AbstractScreen implements GameConstants {
    private final GameUpdateThread gameUpdateThread;
    private final Player player;

    public GameScreen() {
        super();

        AbstractGameItem.clearItems(); // clear the game items from the previous game

        player = new Player(LoginRegisterDialog.getCurrentUsername());
        gameUpdateThread = new GameUpdateThread(this);

        player.fireBullets(); // start shooting

        // This only draws the first level - need support for drawing more levels
        int[][] LEVEL_ONE = Levels.LEVEL_ONE;
        Alien[][] aliens = new Alien[LEVEL_ONE.length][LEVEL_ONE[0].length];

        // Calculate total width of aliens and starting x position
        int totalAliensWidth = LEVEL_ONE[0].length * (ALIEN_WIDTH + ALIEN_PADDING);
        int startX = (SCREEN_WIDTH - totalAliensWidth) / 2;

        for (int i = 0; i < LEVEL_ONE.length; i++) {
            for (int j = 0; j < LEVEL_ONE[i].length; j++) {
                Alien alienToCreate = null;  // Initialize to null
                if (LEVEL_ONE[i][j] == 1) alienToCreate = new Alien.RedAlien(false, player);
                if (LEVEL_ONE[i][j] == 2) alienToCreate = new Alien.GreenAlien(true, player);
                if (LEVEL_ONE[i][j] == 3) alienToCreate = new Alien.YellowAlien(true, player);
                if (LEVEL_ONE[i][j] == 4) alienToCreate = new Alien.ExtraAlien(false, player);

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

//        drawAliensForLevels();

        gameUpdateThread.start();
        SwingUtilities.invokeLater(() -> addKeyListener(player.handleUserInput()));
    }

//    private void drawAliensForLevels() {
//        // Levels
//        Iterator<int[][]> levelIterator = Levels.getLevels().iterator();
//        AtomicInteger levelsDrawn = new AtomicInteger();
//
//        TimeManager.startTimer(3000, e -> {
//            if (levelIterator.hasNext()) {
//                int[][] level = levelIterator.next();
//                aliens = new Alien[level.length][level[0].length];
//
//                // Calculate total width of aliens and starting x position
//                int totalAliensWidth = level[0].length * (ALIEN_WIDTH + ALIEN_PADDING);
//                int startX = (SCREEN_WIDTH - totalAliensWidth) / 2;
//
//                for (int i = 0; i < level.length; i++) {
//                    for (int j = 0; j < level[i].length; j++) {
//                        Alien alienToCreate = null;  // Initialize to null
//                        if (level[i][j] == 1) alienToCreate = new Alien.RedAlien(false, player);
//                        if (level[i][j] == 2) alienToCreate = new Alien.GreenAlien(true, player);
//                        if (level[i][j] == 3) alienToCreate = new Alien.YellowAlien(true, player);
//                        if (level[i][j] == 4) alienToCreate = new Alien.ExtraAlien(false, player);
//
//                        // Assign the created alien back to the array
//                        aliens[i][j] = alienToCreate;
//
//                        // Assign initial position
//                        if (alienToCreate != null && alienToCreate.isValid()) {
//                            int x = startX + j * (ALIEN_WIDTH + ALIEN_PADDING);
//                            int y = i * (ALIEN_HEIGHT + ALIEN_PADDING);
//                            alienToCreate.setPosition(new Point2D.Double(x, y));
//                        }
//                    }
//                }
//
//                levelsDrawn.getAndIncrement();
//            }
//        }, () -> levelsDrawn.get() == 3);
//
//
////        for (int[][] level : Levels.getLevels()) {
//////            aliens = new Alien[level.length][level[0].length];
//////
//////            // Calculate total width of aliens and starting x position
//////            int totalAliensWidth = level[0].length * (ALIEN_WIDTH + ALIEN_PADDING);
//////            int startX = (SCREEN_WIDTH - totalAliensWidth) / 2;
//////
//////            for (int i = 0; i < level.length; i++) {
//////                for (int j = 0; j < level[i].length; j++) {
//////                    Alien alienToCreate = null;  // Initialize to null
//////                    if (level[i][j] == 1) alienToCreate = new Alien.RedAlien(false);
//////                    if (level[i][j] == 2) alienToCreate = new Alien.GreenAlien(true);
//////                    if (level[i][j] == 3) alienToCreate = new Alien.YellowAlien(true);
//////                    if (level[i][j] == 4) alienToCreate = new Alien.ExtraAlien(false);
//////
//////                    // Assign the created alien back to the array
//////                    aliens[i][j] = alienToCreate;
//////
//////                    // Assign initial position
//////                    if (alienToCreate != null && alienToCreate.isValid()) {
//////                        int x = startX + j * (ALIEN_WIDTH + ALIEN_PADDING);
//////                        int y = i * (ALIEN_HEIGHT + ALIEN_PADDING);
//////                        alienToCreate.setPosition(new Point2D.Double(x, y));
//////                    }
//////                }
//////            }
////        }
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
            // each bullet is responsible for its own collision detection.
            bullet.checkForCollisions(gameOverAction);
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
        AbstractGameItem.drawAllItems(g);
        ImageManager.displayLivesLeft(player, g);

        g.setFont(FONT_TEXT);
        g.drawString(String.format("Score: %d", player.getScore()), getWidth() - 75, 25);
        g.drawString(String.format("High score: %d", player.getCurrentHighScore()), getWidth() - 127, 35);
    }
}
