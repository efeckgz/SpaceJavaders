package items;

import abstracts.AbstractGameItem;
import constants.GameConstants;
import engine.ImageManager;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Alien extends AbstractGameItem implements GameConstants {
    private static final ArrayList<Alien> aliens = new ArrayList<>();
    private final boolean isStrong;
    private final Player player;
    private int livesLeft;

    public Alien(boolean isStrong, Player player) {
        super();

        this.isStrong = isStrong;
        this.player = player;

        livesLeft = isStrong ? 2 : 1; // this does not work for some reason
        aliens.add(this);
    }

    public static ArrayList<Alien> getAliens() {
        return aliens;
    }

    public boolean getIsStrong() {
        return isStrong;
    }

    public int getLivesLeft() {
        return livesLeft;
    }

    public void setLivesLeft(int livesLeft) {
        this.livesLeft = livesLeft;
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

//        if (newY >= SCREEN_HEIGHT) {
//            player.setLivesLeft(player.getLivesLeft() - 1);
//            setIsValid(false);
//            System.out.printf("Yes: %s\n", this.getClass().getName());
//        }
    }

    @Override
    protected void draw(Graphics g) {
        if (isValid()) {
            g.drawImage(getAsset(), (int) getPosition().getX(), (int) getPosition().getY(), null);
        }
    }

    @Override
    public void setIsValid() {
        this.isValid = livesLeft > 0;
    }

    @Override
    protected void setIsValid(boolean isValid) {
        // ignored
    }

    public static class RedAlien extends Alien {
        public RedAlien(boolean isStrong, Player player) {
            super(isStrong, player);
            setAsset(ImageManager.load(RED_ALIEN_ASSET_PATH));
        }
    }

    public static class GreenAlien extends Alien {
        public GreenAlien(boolean isStrong, Player player) {
            super(isStrong, player);
            setAsset(ImageManager.load(GREEN_ALIEN_ASSET_PATH));
        }
    }

    public static class YellowAlien extends Alien {
        public YellowAlien(boolean isStrong, Player player) {
            super(isStrong, player);
            setAsset(ImageManager.load(YELLOW_ALIEN_ASSET_PATH));
        }
    }

    public static class ExtraAlien extends Alien {
        public ExtraAlien(boolean isStrong, Player player) {
            super(isStrong, player);
            setAsset(ImageManager.load(EXTRA_ALIEN_ASSET_PATH));
        }
    }
}
