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

    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;

    public Player(JPanel parent) {
        this.parent = parent;

        try {
            asset = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/player.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPosition(new Point2D.Double(
                (double) GameConstants.SCREEN_WIDTH.getValue() / 2 - (double) asset.getWidth() / 2,
                GameConstants.SCREEN_HEIGHT.getValue() - this.getAsset().getHeight()
        ));

        if (isDead()) {
            this.livesLeft -= 1;
            resetHp();
        }
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    @Override
    public double getTravelSpeed() {
        return GameConstants.PLAYER_TRAVEL_SPEED.getValue(); // Arbitrary value
    }

    @Override
    public void updatePosition() {
        // The position of player will be updated according to the user input
        int speed = GameConstants.PLAYER_TRAVEL_SPEED.getValue();

//        Point2D.Double pos = getPosition();
//        double playerX = pos.getX();
//        double playerY = pos.getY();

        if (moveUp) getPosition().y -= speed;
        if (moveDown) getPosition().y += speed;
        if (moveLeft) getPosition().x -= speed;
        if (moveRight) getPosition().x += speed;

//        setPosition(new Point2D.Double(playerX, playerY));

//        revalidate();
//        repaint();
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

//    public void fireBullet() {
//        // fire bullets here
//        parent.fireBullet();
//        // parent.repaint();
//    }
}
