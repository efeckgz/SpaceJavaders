import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Bullet extends GameItem {
    private static final ArrayList<Bullet> bullets = new ArrayList<>();
    private final Player player;
    private boolean isAlive = true;

    public Bullet(Player player) {
        super();
        this.player = player;

        setPosition(new Point2D.Double(
                (player.getPosition().getX() + (double) player.getAsset().getWidth() / 2) - (double) BULLET_WIDTH / 2,
                player.getPosition().getY() - player.getAsset().getHeight()
        ));

        if (Main.debug) {
            TimeManager.startTimer(1000, e -> {
                if (getIsAlive()) {
                    System.out.printf(
                            "Bullet %s location (x, y): %.2f, %.2f\n",
                            Bullet.getBullets().indexOf(this),
                            getPosition().getX(),
                            getPosition().getY()
                    );
                }
            });
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

        if (getPosition().y <= 0) {
            setIsAlive(false);
//            if (Main.debug) System.out.printf("Bullet %s died.\n", Bullet.bullets.indexOf(this));
        }
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
}

