import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class GamePanel extends JPanel {
    // declare game objects
    private BufferedImage img;
    public GamePanel() {
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/Game1.png")));
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
        if (_img != null) g.drawImage(_img, 0, 0, null);
    }

    public void startGameLoop() {
        while (true) { // the condition for this loop will be changed
            for (GameItem item : GameItem.getGameItems()) {
                item.updatePosition();
            }
        }
    }
}
