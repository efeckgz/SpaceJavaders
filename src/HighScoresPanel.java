import javax.swing.*;
import java.awt.*;

public class HighScoresPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("High Scores screen", 350, 250);
    }
}
