import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Iterator;

public class GamePanel extends JPanel implements GameConstants {
    private final Player player;
    private final Alien[][] aliens;
    private StarField starField;

    public GamePanel() {
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.BLACK);

        player = new Player();

        int[][] level1 = Levels.getLevel1();
        aliens = new Alien[level1.length][level1[0].length];
        for (int i = 0; i < level1.length; i++) {
            for (int j = 0; j < level1[i].length; j++) {
                Alien alienToCreate = null;  // Initialize to null
                switch (level1[i][j]) {
                    case 1:
                        alienToCreate = new Alien.RedAlien(false);
                        break;
                    case 2:
                        alienToCreate = new Alien.GreenAlien(false);
                        break;
                    case 3:
                        alienToCreate = new Alien.YellowAlien(false);
                        break;
                    case 4:
                        alienToCreate = new Alien.ExtraAlien(false);
                        break;
                    case 0:
                        break;
                }
                // Assign the created alien back to the array
                aliens[i][j] = alienToCreate;

                // Assign initial position
                if (alienToCreate != null) {
                    int x = j * (ALIEN_WIDTH + ALIEN_PADDING);
                    int y = i * (ALIEN_HEIGHT + ALIEN_PADDING);
                    alienToCreate.setPosition(new Point2D.Double(x, y));
                }
            }
        }

        SwingUtilities.invokeLater(() -> {
            starField = new StarField(getWidth(), getHeight());

            TimeManager.startTimer(1000 / GAME_FPS, e -> {
                starField.animate(); // Start the star field.
                player.updatePosition(); // update the players position.

                // Update the position of each bullet
                Iterator<Bullet> bulletIterator = Bullet.getBullets().iterator();
                while (bulletIterator.hasNext()) {
                    Bullet bullet = bulletIterator.next();
                    bullet.updatePosition();
                    if (!bullet.getIsAlive()) bulletIterator.remove(); // Remove the bullet that dies
                }

                // Update the position of each alien
                for (Alien[] row : aliens) {
                    for (Alien alien : row) {
                        if (alien != null) alien.updatePosition();
                    }
                }

                // update the component
                revalidate();
                repaint();
            });
        });

        // Handles the user input to control the player.
        addKeyListener(player.handleUserInput());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the star field
        starField.draw(g, 0, 0);

        // Draw the player
        Point2D.Double pos = player.getPosition();
        g.drawImage(player.getAsset(), (int) pos.getX(), (int) pos.getY(), null);

        // Draw player health
        g.setColor(Color.WHITE);
        g.setFont(FontManager.getFontText());
        g.drawString("Lives: ", 15, 30);
        if (player.getLivesLeft() > 0) {
            // The check is relevant in debug mode
            g.drawImage(ImageManager.loadLivesLeft(player), 70, 15, null);
        }

        // Draw each alien
        for (Alien[] row : aliens) {
            for (Alien alien : row) {
                if (alien != null) {
                    Point2D.Double alienPos = alien.getPosition();
                    g.drawImage(alien.getAsset(), (int) alienPos.getX(), (int) alienPos.getY(), null);
                }
            }
        }

        // Draw each bullet
        g.setColor(Color.WHITE);
        for (Bullet bullet : Bullet.getBullets()) {
            pos = bullet.getPosition();
            if (bullet.getIsAlive()) {
                g.fillRect((int) pos.getX(), (int) pos.getY(), BULLET_WIDTH, BULLET_HEIGHT);
            }
        }
    }
}
