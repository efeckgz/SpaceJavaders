import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageManager implements GameConstants {
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
        try {
            switch (p.getLivesLeft()) {
                case 3:
                    path = PLAYER_LIVES_THREE_ASSET_PATH;
                    break;
                case 2:
                    path = PLAYER_LIVES_TWO_ASSET_PATH;
                    break;
                case 1:
                    path = PLAYER_LIVES_ONE_ASSET_PATH;
                    break;
                default:
                    throw new IllegalStateException(String.format("Unexpected value for player remaining lives: %s", p.getLivesLeft()));

            }

            livesLeftImg = ImageManager.load(path);
        } catch (IllegalStateException illegalStateException) {
            illegalStateException.printStackTrace();
        }
        return livesLeftImg;
    }
}
