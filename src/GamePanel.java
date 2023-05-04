import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
    // declare game objects
    private BufferedImage img;
    public GamePanel() {
        try {
            img = ImageIO.read(getClass().getResource("/Assets/Game1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImg() {
        return img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Draw game objects
        // g.drawString("Welcome screen", 350, 250);

        BufferedImage _img = getImg();
        if (_img != null)
            g.drawImage(_img, 50, 50, null);
    }

    public void startGameLoop() {
        // Game loop
    }
}
