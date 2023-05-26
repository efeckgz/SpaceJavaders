import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Alien extends GameItem implements GameConstants {
    private static final ArrayList<Alien> aliens = new ArrayList<>();
    private final boolean isStrong;
    private boolean isAlive = true;

    public Alien(boolean isStrong) {
        super();
        this.isStrong = isStrong;
        aliens.add(this);
    }

    public static ArrayList<Alien> getAliens() {
        return aliens;
    }

    public boolean getIsAlive() {
        return this.isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
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
