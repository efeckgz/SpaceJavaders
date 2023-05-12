import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Character {
    private int livesLeft = 3;
    private BufferedImage asset;

    // The parent of this component should be GamePanel
    // This is passed to the bullet object.
    private final JPanel parent;

    public Player(JPanel parent) {
        setPosition(new Point2D.Double(
                (double) GameConstants.SCREEN_WIDTH.getValue() / 2,
                -GameConstants.SCREEN_HEIGHT.getValue()
        ));

        this.parent = parent;

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
        Bullet bullet = new Bullet(this, parent);
        // parent.repaint();
    }
}
