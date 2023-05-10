import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Character {
    private int livesLeft = 3;
    private BufferedImage asset;

    public Player() {
        setPosition(new Point2D.Double(919.0 / 2.0, 0));

        try {
            asset = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/player.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isDead()) {
            this.livesLeft -= 1;
            resetHp();
        }
    }

    @Override
    public double getTravelSpeed() {
        return GameConstants.PLAYER_TRAVEL_SPEED.getValue(); // Arbitrary value
    }

    @Override
    public void updatePosition() {
        // The position of player will be updated according to the user input.
    }

    @Override
    public int getHp() {
        return 100; // research this
    }

    @Override
    public boolean getIsEnemy() {
        return false;
    }

    public void resetHp() {
        hp = 100;
    }

    public int getLivesLeft() {
        return livesLeft;
    }

    public BufferedImage getAsset() {
        return asset;
    }

    public void fireBullet() {
        // fire bullets here
        Bullet bullet = new Bullet();
    }
}
