import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageManager {
    // Class to make loading images easier.
    private static BufferedImage img;
    private static BufferedImage livesLeftImg;

    public static BufferedImage load(String path) {
        try {
            img = ImageIO.read(Objects.requireNonNull(ImageManager.class.getResource(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }

    // This method loads the appropriate image to display the player's remaining lives.
    // Only called from the paintComponent() method of GamePanel.
    public static BufferedImage loadLivesLeft(Player p) {
        String path;
        switch (p.getLivesLeft()) {
            case 3:
                path = "Assets/livesThree.png";
                break;
            case 2:
                path = "Assets/livesTwo.png";
                break;
            case 1:
                path = "Assets/livesOne.png";
                break;
            default:
                // Unreachable - only there to silence the error.
                path = "";
                break;
        }

        try {
            livesLeftImg = ImageIO.read(Objects.requireNonNull(ImageManager.class.getResource(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return livesLeftImg;
    }
}
