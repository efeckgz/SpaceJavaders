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
                starField.animate(); // Start the star field.
                player.updatePosition(); // update the players position.

                // update the component
                revalidate();
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
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        player.setMoveUp(true);
                        break;
                    case KeyEvent.VK_S:
                        player.setMoveDown(true);
                        break;
                    case KeyEvent.VK_A:
                        player.setMoveLeft(true);
                        break;
                    case KeyEvent.VK_D:
                        player.setMoveRight(true);
                        break;
                    case KeyEvent.VK_SPACE:
                        // _player.fireBullet();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        player.setMoveUp(false);
                        break;
                    case KeyEvent.VK_S:
                        player.setMoveDown(false);
                        break;
                    case KeyEvent.VK_A:
                        player.setMoveLeft(false);
                        break;
                    case KeyEvent.VK_D:
                        player.setMoveRight(false);
                        break;
                }
            }
        });
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
