import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class LivesLeftManager {
    private static BufferedImage heart;

    public static BufferedImage getLivesLeftImage(Player p) {
        try {
            heart = ImageIO.read(Objects.requireNonNull(LivesLeftManager.class.getResource("Assets/livesFull.png")));
            // some logic to get the right image according to players life
        } catch (IOException e) {
            e.printStackTrace();
        }

        return heart;
    }
}
