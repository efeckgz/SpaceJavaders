import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;

public class GamePanel extends JPanel implements GameConstants {
    private final Player player;
    private final Alien[][] aliens;
    private StarField starField;

    public GamePanel() {
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.BLACK);

        player = new Player();
        GameUpdateThread gameUpdateThread = new GameUpdateThread(this);

        addComponentListener(new ComponentAdapter() {
            // Creation of a StarField object depends on the panels width and height, however they need to be
            // nonzero. When the component gets created, it gets "resized" from 0x0 to the correct width and
            // height. That is why the star field is initialized in the componentResized() method.
            @Override
            public void componentResized(ComponentEvent e) {
                // The null check prevents the star field from re-initializing if the panel gets resized again.
                // The panel is of fixed size in this case, but it is a good check to have.
                if (starField == null) {
                    starField = new StarField(getWidth(), getHeight());
                    starField.start(); // Start the StarField thread
                }
            }
        });

//        WORK IN PROGRESS!!!
//        addComponentListener(StarField.starFieldComponentListener(this));

        // Levels
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

        gameUpdateThread.start();
        SwingUtilities.invokeLater(() -> addKeyListener(player.handleUserInput()));
    }

    public void updateGame(long deltaTime) {
        GameItem.updateAllPositions(deltaTime); // updates all the game items at once
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        starField.draw(g, 0, 0);
        player.draw(g);
        ImageManager.displayLivesLeft(player, g);

        // Draw each alien
        for (Alien[] row : aliens) {
            for (Alien alien : row) {
                if (alien != null) {
                    alien.draw(g);
                }
            }
        }

        // Draw each bullet
        g.setColor(Color.WHITE);
        for (Bullet bullet : Bullet.getBullets()) bullet.draw(g);
    }
}
