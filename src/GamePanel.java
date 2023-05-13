import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

public class GamePanel extends JPanel {
    private final Player player;

    private StarField starField;
    private Timer timer;

//    private double playerX = getPlayerX();
//    private double playerY = getPlayerY();

    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;

    public GamePanel() {
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.BLACK);

        player = new Player(this);
        player.setPosition(new Point2D.Double(420, 620));

        SwingUtilities.invokeLater(() -> {
            starField = new StarField(getWidth(), getHeight());

            int delay = 1000 / 120; // Calculate the delay for 120 FPS
            new Timer(delay, e -> {
                starField.animate();
                updatePosition();

                repaint();
            }).start();

            // Only run in debug mode
            if (Main.debug) {
                new Timer(1000, e -> {
                    System.out.printf(
                            "Player location (x, y): %f, %f\n",
                            player.getPosition().getX(),
                            player.getPosition().getY()
                    );
                }).start();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_W:
//                        if (playerY == (double) GameConstants.SCREEN_HEIGHT.getValue() / 2) {
//                            playerY = (double) GameConstants.SCREEN_HEIGHT.getValue() / 2;
//                        } else {
//                            moveUp = true;
//                        }
                        moveUp = true;
                        break;
                    case KeyEvent.VK_S:
                        moveDown = true;
                        break;
                    case KeyEvent.VK_A:
                        moveLeft = true;
                        break;
                    case KeyEvent.VK_D:
                        moveRight = true;
                        break;
                    case KeyEvent.VK_SPACE:
                        // _player.fireBullet();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_W) moveUp = false;
                if (key == KeyEvent.VK_S) moveDown = false;
                if (key == KeyEvent.VK_A) moveLeft = false;
                if (key == KeyEvent.VK_D) moveRight = false;
            }
        });
    }

    private void updatePosition() {
        int speed = GameConstants.PLAYER_TRAVEL_SPEED.getValue();

        Point2D.Double pos = player.getPosition();
        double playerX = pos.getX();
        double playerY = pos.getY();

        if (moveUp) playerY -= speed;
        if (moveDown) playerY += speed;
        if (moveLeft) playerX -= speed;
        if (moveRight) playerX += speed;

        player.setPosition(new Point2D.Double(playerX, playerY));

        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        starField.draw(g, 0, 0);
        Point2D.Double pos = player.getPosition();
        g.drawImage(player.getAsset(), (int) pos.getX(), (int) pos.getY(), null);

        // Draw each bullet
        g.setColor(Color.WHITE);
        for (Bullet bullet : Bullet.getBullets()) {
            pos = bullet.getPosition();
            g.fillRect((int) pos.getX(), (int) pos.getY(), 5, 25);
        }
    }
}
