import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements GameConstants {
    private final Player player;
    private final GameUpdateThread gameUpdateThread;
    //    private final Alien[][] aliens;
    private StarField starField;

    public GamePanel() {
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.BLACK);

        player = new Player();
//        starField = new StarField(SCREEN_WIDTH, SCREEN_HEIGHT);
        gameUpdateThread = new GameUpdateThread(this);
        gameUpdateThread.start();
        boolean canInitStarField = getWidth() != 0 && getHeight() != 0;

//        addComponentListener(new ComponentAdapter() {
//            // Initialize StarField everytime the component gets resized. This way the getWidth()
//            // and getHeight() methods have a nonzero value
//
//            @Override
//            public void componentResized(ComponentEvent e) {
//                super.componentResized(e);
//                starField = new StarField(getWidth(), getHeight());
//            }
//        });

        SwingUtilities.invokeLater(() -> addKeyListener(player.handleUserInput()));
    }

//    @Override
//    public void addNotify() {
//        super.addNotify();
//        starField = new StarField(SCREEN_WIDTH, SCREEN_HEIGHT);
//    }

    //    public GamePanel() {
//        setFocusable(true);
//        requestFocusInWindow();
//        setBackground(Color.BLACK);
//
//        player = new Player();
//
//        int[][] level1 = Levels.getLevel1();
//        aliens = new Alien[level1.length][level1[0].length];
//        for (int i = 0; i < level1.length; i++) {
//            for (int j = 0; j < level1[i].length; j++) {
//                Alien alienToCreate = null;  // Initialize to null
//                switch (level1[i][j]) {
//                    case 1:
//                        alienToCreate = new Alien.RedAlien(false);
//                        break;
//                    case 2:
//                        alienToCreate = new Alien.GreenAlien(false);
//                        break;
//                    case 3:
//                        alienToCreate = new Alien.YellowAlien(false);
//                        break;
//                    case 4:
//                        alienToCreate = new Alien.ExtraAlien(false);
//                        break;
//                    case 0:
//                        break;
//                }
//                // Assign the created alien back to the array
//                aliens[i][j] = alienToCreate;
//
//                // Assign initial position
//                if (alienToCreate != null) {
//                    int x = j * (ALIEN_WIDTH + ALIEN_PADDING);
//                    int y = i * (ALIEN_HEIGHT + ALIEN_PADDING);
//                    alienToCreate.setPosition(new Point2D.Double(x, y));
//                }
//            }
//        }
//
//        SwingUtilities.invokeLater(() -> {
//            starField = new StarField(getWidth(), getHeight());
//
//            TimeManager.startTimer(1000 / GAME_FPS, e -> {
//                float deltaTime = TimeManager.getDeltaTime();
//                System.out.printf("Delta time: %f\n", deltaTime);
//
//                starField.animate(deltaTime); // Start the star field.
//                player.updatePosition(deltaTime); // update the players position.
//
//                // Update the position of each bullet
//                Iterator<Bullet> bulletIterator = Bullet.getBullets().iterator();
//                while (bulletIterator.hasNext()) {
//                    Bullet bullet = bulletIterator.next();
//                    bullet.updatePosition(deltaTime);
//                    if (!bullet.getIsAlive()) bulletIterator.remove(); // Remove the bullet that dies
//                }
//
//                // Update the position of each alien
//                for (Alien[] row : aliens) {
//                    for (Alien alien : row) {
//                        if (alien != null) alien.updatePosition(deltaTime);
//                    }
//                }
//
//                // update the component
//                revalidate();
//                repaint();
//            });
//        });
//
//        // Handles the user input to control the player.
//        addKeyListener(player.handleUserInput());
//    }

    public void updateGame(long deltaTime) {
        player.updatePosition(deltaTime);
        starField.animate(deltaTime);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

//        starField.draw(g, 0, 0);
        player.draw(g);
        ImageManager.displayLivesLeft(player, g);
//
//        // Draw each alien
//        for (Alien[] row : aliens) {
//            for (Alien alien : row) {
//                if (alien != null) {
//                    alien.draw(g);
//                }
//            }
//        }

        // Draw each bullet
        g.setColor(Color.WHITE);
        for (Bullet bullet : Bullet.getBullets()) {
            bullet.draw(g);
        }
    }
}
