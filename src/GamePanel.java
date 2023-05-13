import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class GamePanel extends JPanel {
    private final Player player;
    private StarField starField;

    public GamePanel() {
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.BLACK);

        player = new Player(this);
        player.setPosition(new Point2D.Double(420, 620));

        SwingUtilities.invokeLater(() -> {
            starField = new StarField(getWidth(), getHeight());

            new Timer(1000 / 120, e -> {
                starField.animate(); // Start the star field.
                player.updatePosition(); // update the players position.
                for (Bullet bullet : Bullet.getBullets()) bullet.updatePosition();

                // update the component
                revalidate();
                repaint();
            }).start();
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

        // Draw each bullet
        g.setColor(Color.WHITE);
        for (Bullet bullet : Bullet.getBullets()) {
            pos = bullet.getPosition();
            if (bullet.getIsAlive()) g.fillRect((int) pos.getX(), (int) pos.getY(), 5, 25);
        }
    }
}
