import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Player extends Character {
    private final BufferedImage asset;
    // The parent of this component should be GamePanel
    // This is passed to the bullet object.
    private final JPanel parent;
    private int livesLeft = 3;
    // Variables for player movement
    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;

    // Variable for bullet firing logic
    private boolean bulletFired = false;

    public Player(JPanel parent) {
        this.parent = parent;
        asset = ImageManager.load(GameConstants.PLAYER_ASSET_PATH);

        setPosition(new Point2D.Double(
                (double) GameConstants.SCREEN_WIDTH / 2 - (double) asset.getWidth() / 2,
                GameConstants.SCREEN_HEIGHT - this.getAsset().getHeight()
        ));

        // Move this out of herej
        if (isDead()) {
            this.livesLeft -= 1;
            resetHp();
        }

        // Fire bullets automatically
        TimeManager.startTimer(GameConstants.BULLET_FIRE_FREQUENCY, e -> fireBullet());
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

    public void setBulletFired(boolean bulletFired) {
        this.bulletFired = bulletFired;
    }

    @Override
    public double getTravelSpeed() {
        return GameConstants.PLAYER_TRAVEL_SPEED /* TimeManager.getDeltaTime()*/;
    }

    @Override
    public void updatePosition() { // The position of player will be updated according to the user input
        double speed = getTravelSpeed();

        if (moveUp && getPosition().y - speed >= (double) GameConstants.SCREEN_HEIGHT / 2) {
            getPosition().y -= speed;
        }

        if (moveDown && getPosition().y + speed + asset.getHeight() <= GameConstants.SCREEN_HEIGHT - this.getAsset().getHeight()) {
            getPosition().y += speed;
        }

        if (moveLeft && getPosition().x - speed >= 0) {
            getPosition().x -= speed;
        }

        if (moveRight && getPosition().x + speed + asset.getWidth() <= GameConstants.SCREEN_WIDTH) {
            getPosition().x += speed;
        }
    }


    @Override
    public int getHp() {
        return GameConstants.PLAYER_HP; // research this
    }

    @Override
    public boolean getIsEnemy() {
        return false;
    }

    public void resetHp() {
        hp = GameConstants.PLAYER_HP;
    }

    public int getLivesLeft() {
        return livesLeft;
    }

    public void setLivesLeft(int livesLeft) {
        this.livesLeft = livesLeft;
    }

    public BufferedImage getAsset() {
        return asset;
    }

    public void fireBullet() {
        // fire bullets here
        Bullet bullet = new Bullet(this);
        setBulletFired(true);
    }

    private void controlKeyPressedActionHandler(KeyEvent e) {
        try {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    setMoveUp(true);
                    break;
                case KeyEvent.VK_S:
                    setMoveDown(true);
                    break;
                case KeyEvent.VK_A:
                    setMoveLeft(true);
                    break;
                case KeyEvent.VK_D:
                    setMoveRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    if (Main.debug) setLivesLeft(getLivesLeft() - 1);
            }
        } catch (IllegalStateException illegalStateException) {
            System.err.printf("%s", getLivesLeft());
        }
    }

    private void controlKeyReleasedActionHandler(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                setMoveUp(false);
                break;
            case KeyEvent.VK_S:
                setMoveDown(false);
                break;
            case KeyEvent.VK_A:
                setMoveLeft(false);
                break;
            case KeyEvent.VK_D:
                setMoveRight(false);
                break;
        }

        // Only run in debug mode
        if (Main.debug) {
            System.out.printf(
                    "Player location (x, y): %.2f, %.2f\n",
                    getPosition().getX(),
                    getPosition().getY()
            );
        }
    }

    public KeyAdapter handleUserInput() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                controlKeyPressedActionHandler(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                controlKeyReleasedActionHandler(e);
            }
        };
    }
}
