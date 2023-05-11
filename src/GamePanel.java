import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GamePanel extends JPanel {
    Player player = new Player();

    private final BufferedImage starField1;
    private BufferedImage starField2;

    private int starField1Y = 0;
    private int starField2Y = -GameConstants.SCREEN_HEIGHT.getValue();

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

        starField1 = new BufferedImage(
                GameConstants.SCREEN_WIDTH.getValue(),
                GameConstants.SCREEN_HEIGHT.getValue(),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics g1 = starField1.getGraphics();
        g1.setColor(Color.BLACK);
        g1.fillRect(0, 0, starField1.getWidth(), starField1.getHeight());
        g1.setColor(Color.WHITE);

        int step = 30;
        int offset = 7;
        Random random = new Random();
        for (int i = offset; i < starField1.getHeight() - offset; i += step) {
            for (int j = offset; j < starField1.getWidth() - offset; j += step) {
                if (random.nextFloat() < 0.3) g1.fillOval(j, i, 3, 3);
            }
        }
        g1.dispose();

        starField2 = new BufferedImage(
                GameConstants.SCREEN_WIDTH.getValue(),
                GameConstants.SCREEN_HEIGHT.getValue(),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics g2 = starField2.getGraphics();
        g2.drawImage(starField1, 0, 0, null);
        g2.dispose();

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

        g.drawImage(starField1, 0, starField1Y, this);
        g.drawImage(starField2, 0, starField2Y, this);

        starField1Y += GameConstants.PLAYER_TRAVEL_SPEED.getValue() / 2;
        starField2Y += GameConstants.PLAYER_TRAVEL_SPEED.getValue() / 2;

        if (starField1Y >= getHeight()) starField1Y = -getHeight();
        if (starField2Y >= getHeight()) starField2Y = -getHeight();

        g.drawImage(_player.getAsset(), (int) getPlayerX(), (int) getPlayerY(), null);
    }
}
