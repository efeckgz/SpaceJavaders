import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Player extends Character {
    // The parent of this component should be GamePanel
    // This is passed to the bullet object.
    private int livesLeft = 3;
    // Variables for player movement
    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;

    public Player() {
        super();
        setAsset(ImageManager.load(PLAYER_ASSET_PATH));
        setPosition(PLAYER_STARTING_POSITION); // 420, 620

        // Fire bullets automatically
        TimeManager.startTimer(BULLET_FIRE_FREQUENCY, e -> fireBullet());
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
        return PLAYER_TRAVEL_SPEED /* TimeManager.getDeltaTime()*/;
    }

    @Override
    public void updatePosition(float deltaTime) { // The position of player will be updated according to the user input
        double speed = getTravelSpeed();

        if (moveUp && getPosition().y - speed >= (double) SCREEN_HEIGHT / 2) {
            getPosition().y -= speed * deltaTime;
        }

        if (moveDown && getPosition().y + speed + asset.getHeight() <= SCREEN_HEIGHT - this.getAsset().getHeight()) {
            getPosition().y += speed * deltaTime;
        }

        if (moveLeft && getPosition().x - speed >= 0) {
            getPosition().x -= speed * deltaTime;
        }

        if (moveRight && getPosition().x + speed + asset.getWidth() <= SCREEN_WIDTH) {
            getPosition().x += speed * deltaTime;
        }
    }

    @Override
    boolean intersects(GameItem item) {
        return false;
    }

    @Override
    public int getHp() {
        return PLAYER_HP; // research this
    }

    @Override
    public boolean getIsEnemy() {
        return false;
    }

    public void resetHp() {
        hp = PLAYER_HP;
    }

    public int getLivesLeft() {
        return livesLeft;
    }

    public void setLivesLeft(int livesLeft) {
        this.livesLeft = livesLeft;
    }

    public void fireBullet() {
        new Bullet(this);
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
