import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {
    // declare game objects
    Player player = new Player();

    private double playerX = getPlayer().getPosition().getX();
    private double playerY = getPlayer().getPosition().getY();

    public GamePanel() {
        setFocusable(true);
        requestFocusInWindow();
        startGameLoop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect((int) getPlayerX(), (int) getPlayerY(), 50, 50);
    }

    public double getPlayerX() {
        return playerX;
    }

    public double getPlayerY() {
        return playerY;
    }

    public Player getPlayer() {
        return player;
    }

    public void startGameLoop() {
//        while (true) { // the condition for this loop will be changed
//            for (GameItem item : GameItem.getGameItems()) {
//                item.updatePosition();
//            }
//        }
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                Player _player = getPlayer();

                switch (key) {
                    case KeyEvent.VK_W:
                        playerY -= _player.getTravelSpeed();
                        break;
                    case KeyEvent.VK_S:
                        playerY += _player.getTravelSpeed();
                        break;
                    case KeyEvent.VK_A:
                        playerX -= _player.getTravelSpeed();
                        break;
                    case KeyEvent.VK_D:
                        playerX += _player.getTravelSpeed();
                        break;
                }

                repaint();
            }
        });
    }
}
