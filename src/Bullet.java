import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Bullet extends GameItem {
    private final Player player;
    private boolean isAlive;
    private static final ArrayList<Bullet> bullets = new ArrayList<>();

    public Bullet(Player player) {
        this.player = player;
        this.isAlive = true;

        setPosition(new Point2D.Double(
                (player.getPosition().getX() + (double) player.getAsset().getWidth() / 2) - 2,
                player.getPosition().getY() - 5
        ));

        if (Main.debug) {
            new Timer(1000, e -> {
                if (getIsAlive()) {
                    System.out.printf(
                            "Bullet %s location (x, y): %.2f, %.2f\n",
                            Bullet.getBullets().indexOf(this),
                            getPosition().getX(),
                            getPosition().getY()
                    );
                }
            }).start();
        }

        bullets.add(this);
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public double getTravelSpeed() {
        return GameConstants.BULLET_TRAVEL_SPEED.getValue() * TimeManager.getDeltaTime(); // Arbitrary value
    }

    public static ArrayList<Bullet> getBullets() {
        return bullets;
    }

    @Override
    public void updatePosition() {
        if (getPosition().y < 1) {
            setIsAlive(false);
//            if (Main.debug) System.out.printf("Bullet %s died.\n", Bullet.bullets.indexOf(this));
        }

        if (getIsAlive()) {
            getPosition().y -= getTravelSpeed();
        }
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
}

