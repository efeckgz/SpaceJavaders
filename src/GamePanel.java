import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
    Player player = new Player();

    // private BufferedImage playerAsset;
    private final BufferedImage starField;

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

        starField = new BufferedImage(
                GameConstants.SCREEN_WIDTH.getValue(),
                GameConstants.SCREEN_HEIGHT.getValue(),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics g = starField.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, starField.getWidth(), starField.getHeight());
        g.setColor(Color.WHITE);

        int step = 30;
        int offset = 7;
        for (int i = offset; i < starField.getHeight() - offset; i += step) {
            for (int j = offset; j < starField.getWidth() - offset; j += step) {
                g.fillOval(j, i, 3, 3);
            }
        }
        g.dispose();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
//                if (key == KeyEvent.VK_W) moveUp = true;
//                if (key == KeyEvent.VK_S) moveDown = true;
//                if (key == KeyEvent.VK_A) moveLeft = true;
//                if (key == KeyEvent.VK_D) moveRight = true;
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

        int delay = 1000 / 120; // Calculate the delay for 120 FPS
        Timer timer = new Timer(delay, e -> {
            updatePosition();
            repaint();
        });
        timer.start();
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
//        Player _player = getPlayer();
//        int newX = getX();
//        int newY = getY();
//
//        if (moveUp && newY - GameConstants.PLAYER_TRAVEL_SPEED.getValue() >= getHeight() / 2) newY -= GameConstants.PLAYER_TRAVEL_SPEED.getValue();
//        if (moveDown && newY + playerAsset.getHeight() + GameConstants.PLAYER_TRAVEL_SPEED.getValue() <= getHeight()) newY += GameConstants.PLAYER_TRAVEL_SPEED.getValue();
//        if (moveLeft && newX - GameConstants.PLAYER_TRAVEL_SPEED.getValue() >= 0) newX -= GameConstants.PLAYER_TRAVEL_SPEED.getValue();
//        if (moveRight && newX + playerAsset.getWidth() + GameConstants.PLAYER_TRAVEL_SPEED.getValue() <= getWidth()) newX += GameConstants.PLAYER_TRAVEL_SPEED.getValue();
//
//        playerX = newX;
//        playerY = newY;
        int speed = GameConstants.PLAYER_TRAVEL_SPEED.getValue();

        if (moveUp) playerY -= speed;
        if (moveDown) playerY += speed;
        if (moveLeft) playerX -= speed;
        if (moveRight) playerX += speed;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Player _player = getPlayer();

        super.paintComponent(g);
//        g.setColor(Color.WHITE);
//        for (int i = 30; i < GameConstants.SCREEN_HEIGHT.getValue(); i += 75) {
//            for (int j = 30; j < GameConstants.SCREEN_WIDTH.getValue(); j += 75) {
//                g.drawOval(i, j, 5, 5);
//            }
//        }
//
//        g.setColor(Color.BLACK);
        g.drawImage(starField, 0, 0, null);
        g.drawImage(_player.getAsset(), (int) getPlayerX(), (int) getPlayerY(), null);
    }
}
