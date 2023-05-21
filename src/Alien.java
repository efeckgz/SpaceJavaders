import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Alien extends Character implements GameConstants {
    private final boolean isStrong;
    private BufferedImage asset;

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
        return ALIEN_TRAVEL_SPEED;
    }

    @Override
    public void updatePosition() {
        Point2D.Double pos = getPosition();
        double newY = pos.getY() + getTravelSpeed();  // Just move down

        setPosition(new Point2D.Double(pos.getX(), newY));
    }

    public static class RedAlien extends Alien {
        public RedAlien(boolean isStrong) {
            super(isStrong);
            setAsset(ImageManager.load(RED_ALIEN_ASSET_PATH));
        }
    }

    public static class GreenAlien extends Alien {
        public GreenAlien(boolean isStrong) {
            super(isStrong);
            setAsset(ImageManager.load(GREEN_ALIEN_ASSET_PATH));
        }
    }

    public static class YellowAlien extends Alien {
        public YellowAlien(boolean isStrong) {
            super(isStrong);
            setAsset(ImageManager.load(YELLOW_ALIEN_ASSET_PATH));
        }
    }

    public static class ExtraAlien extends Alien {
        public ExtraAlien(boolean isStrong) {
            super(isStrong);
            setAsset(ImageManager.load(EXTRA_ALIEN_ASSET_PATH));
        }
    }
}
