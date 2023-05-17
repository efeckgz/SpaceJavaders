import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Iterator;

public class GamePanel extends JPanel {
    private final Player player;
    private StarField starField;

    private final Alien[][] aliens;

    public GamePanel() {
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.BLACK);

        player = new Player(this);
        player.setPosition(new Point2D.Double(420, 620));

        int[][] level1 = Levels.getLevel1();
        aliens = new Alien[level1.length][level1[0].length];
        for (int i = 0; i < level1.length; i++) {
            for (int j = 0; j < level1[i].length; j++) {
                switch (level1[i][j]) {
                    case 1:
                        aliens[i][j] = new Alien.RedAlien(false);
                        break;
                    case 2:
                        aliens[i][j] = new Alien.GreenAlien(false);
                        break;
                    case 3:
                        aliens[i][j] = new Alien.YellowAlien(false);
                        break;
                    case 4:
                        aliens[i][j] = new Alien.ExtraAlien(false);
                        break;
                }
            }
        }

        SwingUtilities.invokeLater(() -> {
            starField = new StarField(getWidth(), getHeight());

            TimeManager.startTimer(1000 / GameConstants.GAME_FPS.getValue(), e -> {
                starField.animate(); // Start the star field.
                player.updatePosition(); // update the players position.

                // Update the position of each bullet
                Iterator<Bullet> bulletIterator = Bullet.getBullets().iterator();
                while (bulletIterator.hasNext()) {
                    Bullet bullet = bulletIterator.next();
                    bullet.updatePosition();
                    if (!bullet.getIsAlive()) bulletIterator.remove();
                }

                // Update the position of each alien
                for (Alien[] row : aliens) {
                    for (Alien alien : row) {
                        if (alien != null) {
                            alien.updatePosition();
                        }
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
        for (int i = 0; i < aliens.length; i++) {
            for (int j = 0; j < aliens[i].length; j++) {
                Alien alien = aliens[i][j];
                if (alien != null) {
                    // Calculate position based on grid index
                    int x = j * (GameConstants.ALIEN_WIDTH.getValue() + GameConstants.ALIEN_PADDING.getValue());
                    int y = i * (GameConstants.ALIEN_HEIGHT.getValue() + GameConstants.ALIEN_PADDING.getValue());
                    g.drawImage(alien.getAsset(), x, y, null);
                }
            }
        }

        // Draw each bullet
        g.setColor(Color.WHITE);
        for (Bullet bullet : Bullet.getBullets()) {
            pos = bullet.getPosition();
            if (bullet.getIsAlive()) {
                g.fillRect(
                        (int) pos.getX(),
                        (int) pos.getY(),
                        GameConstants.BULLET_WIDTH.getValue(),
                        GameConstants.BULLET_HEIGHT.getValue()
                );
            }
        }
    }
}
