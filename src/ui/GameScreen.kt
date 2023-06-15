package ui

import abstracts.AbstractGameItem.Companion.clearItems
import abstracts.AbstractGameItem.Companion.drawAllItems
import abstracts.AbstractScreen
import constants.GameConstants
import engine.ImageManager.displayLivesLeft
import items.Alien
import items.Alien.*
import items.Bullet
import items.Levels
import items.Player
import threads.GameUpdateThread
import java.awt.Graphics
import java.awt.geom.Point2D
import javax.swing.SwingUtilities

class GameScreen : AbstractScreen(), GameConstants {
    val gameUpdateThread: GameUpdateThread?
    val player: Player

    init {
        clearItems() // clear the game items from the previous game
        player = Player(LoginRegisterDialog.getCurrentUsername())
        gameUpdateThread = GameUpdateThread(this)
        player.fireBullets() // start shooting

        // This only draws the first level - need support for drawing more levels
        val LEVEL_ONE = Levels.LEVEL_ONE
        val aliens = Array(LEVEL_ONE.size) { arrayOfNulls<Alien>(LEVEL_ONE[0].size) }

        // Calculate total width of aliens and starting x position
        val totalAliensWidth = LEVEL_ONE[0].size * (GameConstants.ALIEN_WIDTH + GameConstants.ALIEN_PADDING)
        val startX = (GameConstants.SCREEN_WIDTH - totalAliensWidth) / 2
        for (i in LEVEL_ONE.indices) {
            for (j in LEVEL_ONE[i].indices) {
                var alienToCreate: Alien? = null // Initialize to null
                if (LEVEL_ONE[i][j] == 1) alienToCreate = RedAlien(false, player)
                if (LEVEL_ONE[i][j] == 2) alienToCreate = GreenAlien(true, player)
                if (LEVEL_ONE[i][j] == 3) alienToCreate = YellowAlien(true, player)
                if (LEVEL_ONE[i][j] == 4) alienToCreate = ExtraAlien(false, player)

                // Assign the created alien back to the array
                aliens[i][j] = alienToCreate

                // Assign initial position
                if (alienToCreate != null && alienToCreate.isValid()) {
                    val x = startX + j * (GameConstants.ALIEN_WIDTH + GameConstants.ALIEN_PADDING)
                    val y = i * (GameConstants.ALIEN_HEIGHT + GameConstants.ALIEN_PADDING)
                    alienToCreate.position = Point2D.Double(x.toDouble(), y.toDouble())
                }
            }
        }

//        drawAliensForLevels();
        gameUpdateThread.start()
        SwingUtilities.invokeLater { addKeyListener(player.handleUserInput()) }
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
    fun detectCollisions() {
        val player = player
        val gameOverAction = gameUpdateThread!!.gameOverAction
        for (alien in Alien.aliens) {
            if (alien != null && alien.isValid()) {
                // Alien exists, check collision
                if (alien.intersects(player)) {
                    player.livesLeft = player.livesLeft - 1
                    alien.livesLeft = alien.livesLeft - 1
                    player.setIsValid()
                    alien.setIsValid()
                    if (!player.isValid() && gameOverAction != null) {
                        gameOverAction.run()
                    }
                    return
                }
            }
        }
        for (bullet in Bullet.bullets) {
            // each bullet is responsible for its own collision detection.
            bullet.checkForCollisions(gameOverAction!!)
        }
    }

    fun stopGameThread() {
        gameUpdateThread?.stop()
    }

    override fun drawItems(g: Graphics?) {
        drawAllItems(g)
        displayLivesLeft(player, g!!)
        g.font = GameConstants.FONT_TEXT
        g.drawString(String.format("Score: %d", player.score), width - 75, 25)
        g.drawString(String.format("High score: %d", player.currentHighScore), width - 127, 35)
    }
}
