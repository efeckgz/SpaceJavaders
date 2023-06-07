package models;

import abstracts.AbstractGameItem;
import constants.GameConstants;
import utils.ImageManager;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Alien extends AbstractGameItem implements GameConstants {
    private static final ArrayList<Alien> aliens = new ArrayList<>();
    private final boolean isStrong;
    private int livesLeft;

    public Alien(boolean isStrong) {
        super();
        this.isStrong = isStrong;
        livesLeft = isStrong ? 2 : 1;
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
