import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {
    private final Player player;

    private StarField starField;
    private Timer timer;

    private double playerX = getPlayerX();
    private double playerY = getPlayerY();

    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;

    public GamePanel() {
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.BLACK);

        player = new Player(this);

        SwingUtilities.invokeLater(() -> {
            starField = new StarField(getWidth(), getHeight());

            int delay = 1000 / 120; // Calculate the delay for 120 FPS
            Timer timer = new Timer(delay, e -> {
                starField.animate();
                updatePosition();
                repaint();
            });
            timer.start();
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Player _player = getPlayer();

                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_W:
                        if (playerY == (double) GameConstants.SCREEN_HEIGHT.getValue() / 2) {
                            playerY = (double) GameConstants.SCREEN_HEIGHT.getValue() / 2;
                        } else {
                            moveUp = true;
                        }
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
                        _player.fireBullet();
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

    public Player getPlayer() {
        return player;
    }

    public double getPlayerX() {
        return playerX;
    }

    public double getPlayerY() {
        return playerY;
    }

    private void updatePosition() {
        int speed = GameConstants.PLAYER_TRAVEL_SPEED.getValue();

        if (moveUp) playerY -= speed;
        if (moveDown) playerY += speed;
        if (moveLeft) playerX -= speed;
        if (moveRight) playerX += speed;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Player _player = getPlayer();
        starField.draw(g, 0, 0);
        g.drawImage(_player.getAsset(), (int) getPlayerX(), (int) getPlayerY(), null);
    }
}
