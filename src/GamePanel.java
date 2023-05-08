import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GamePanel extends JPanel {
    Player player = new Player();

    private BufferedImage playerAsset;

    private double playerX = getPlayerX();
    private double playerY = getPlayerY();

    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;

    public GamePanel() {
        setFocusable(true);
        requestFocusInWindow();

        try {
            playerAsset = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/player.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_W) moveUp = true;
                if (key == KeyEvent.VK_S) moveDown = true;
                if (key == KeyEvent.VK_A) moveLeft = true;
                if (key == KeyEvent.VK_D) moveRight = true;
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
        Player _player = getPlayer();
        int newX = getX();
        int newY = getY();

        if (moveUp && newY - GameConstants.PLAYER_TRAVEL_SPEED.getValue() >= getHeight() / 2) newY -= GameConstants.PLAYER_TRAVEL_SPEED.getValue();
        if (moveDown && newY + playerAsset.getHeight() + GameConstants.PLAYER_TRAVEL_SPEED.getValue() <= getHeight()) newY += GameConstants.PLAYER_TRAVEL_SPEED.getValue();
        if (moveLeft && newX - GameConstants.PLAYER_TRAVEL_SPEED.getValue() >= 0) newX -= GameConstants.PLAYER_TRAVEL_SPEED.getValue();
        if (moveRight && newX + playerAsset.getWidth() + GameConstants.PLAYER_TRAVEL_SPEED.getValue() <= getWidth()) newX += GameConstants.PLAYER_TRAVEL_SPEED.getValue();

        playerX = newX;
        playerY = newY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(playerAsset, (int) getPlayerX(), (int) getPlayerY(), null);
    }
}
