import java.awt.geom.Point2D;

public class Alien extends Character implements GameConstants {
    private final boolean isStrong;

    public Alien(boolean isStrong) {
        this.isStrong = isStrong;
    }

    public boolean getIsStrong() {
        return isStrong;
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
    public void updatePosition(float deltaTime) {
        Point2D.Double pos = getPosition();
        double newY = pos.getY() + getTravelSpeed() * deltaTime;

        setPosition(new Point2D.Double(pos.getX(), newY));
    }

    @Override
    boolean intersects(GameItem item) {
        return false;
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
