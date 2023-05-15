import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Alien extends Character {
    private final boolean isStrong;
    private BufferedImage asset;

    private static boolean movingRight = true;

    public Alien(boolean isStrong) {
        this.isStrong = isStrong;
    }

    public boolean getIsStrong() {
        return isStrong;
    }

    public BufferedImage getAsset() {
        return asset;
    }

    public void setAsset(BufferedImage asset) {
        this.asset = asset;
    }

    @Override
    public int getHp() {
        return getIsStrong() ? 20 : 10;
    }

    @Override
    public boolean getIsEnemy() {
        return true;
    }

    @Override
    public double getTravelSpeed() {
        return 5;
    }

    @Override
    public void updatePosition() {
        Point2D.Double pos = getPosition();
        double newX = pos.getX() + (movingRight ? getTravelSpeed() : -getTravelSpeed());
        double newY = pos.getY();

        // Check if the group of aliens needs to move down and switch direction
        if (movingRight && newX > GameConstants.SCREEN_WIDTH.getValue() - getWidth()) {
            movingRight = false;
            newY += getHeight(); // move down by the height of an alien
        } else if (!movingRight && newX < 0) {
            movingRight = true;
            newY += getHeight(); // move down by the height of an alien
        }

        setPosition(new Point2D.Double(newX, newY));
    }

    public static class RedAlien extends Alien {
        public RedAlien(boolean isStrong) {
            super(isStrong);
            setAsset(ImageManager.load("Assets/red.png"));
        }
    }

    public static class GreenAlien extends Alien {
        public GreenAlien(boolean isStrong) {
            super(isStrong);
            setAsset(ImageManager.load("Assets/green.png"));
        }
    }

    public static class YellowAlien extends Alien {
        public YellowAlien(boolean isStrong) {
            super(isStrong);
            setAsset(ImageManager.load("Assets/yellow.png"));
        }
    }

    public static class ExtraAlien extends Alien {
        public ExtraAlien(boolean isStrong) {
            super(isStrong);
            setAsset(ImageManager.load("Assets/extra.png"));
        }
    }
}
