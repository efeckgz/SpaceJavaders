import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    // declare game objects

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Draw game objects
        g.drawString("Welcome screen", 350, 250);
    }

    public void startGameLoop() {
        // Game loop
    }
}
