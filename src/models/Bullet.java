package models;

import abstracts.GameItem;
import utils.TimeManager;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Bullet extends GameItem {
    private static final ArrayList<Bullet> bullets = new ArrayList<>();
    private boolean isAlive = true;

    public Bullet(Player player) {
        super();

        // Place the bullet at the center of the players asset and just above it
        setPosition(new Point2D.Double(
                (player.getPosition().getX() + (double) player.getAsset().getWidth() / 2) - (double) BULLET_WIDTH / 2,
                player.getPosition().getY() - player.getAsset().getHeight()
        ));

        if (Main.debug) {
            TimeManager.startTimer(1000, e -> {
                if (getIsAlive()) {
                    System.out.printf(
                            "models.Bullet %s location (x, y): %.2f, %.2f\n",
                            Bullet.getBullets().indexOf(this),
                            getPosition().getX(),
                            getPosition().getY()
                    );
                }
            }, () -> !player.getIsAlive());
        }

        bullets.add(this);
    }

    public static ArrayList<Bullet> getBullets() {
        return bullets;
    }

    @Override
    public double getTravelSpeed() {
        return BULLET_TRAVEL_SPEED;
    }

    @Override
    public void updatePosition(float deltaTime) {
        getPosition().y -= getTravelSpeed() * deltaTime;
        setIsAlive(getPosition().y > 0);
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
}

