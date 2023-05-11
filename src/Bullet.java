import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Bullet extends GameItem {
    private final Player player;
    private Point2D.Double position;

    private boolean isAlive;

    private static final ArrayList<Bullet> bullets = new ArrayList<>();

    public Bullet(Player player, JPanel parent) {
        // constructor creates the bullet at the tip of the players muzzle and handles the logic
        // for updating its location. This method should track the required properties of the bullet
        // (its location, whether it is destroyed or not etc.) and handle its destruction accordingly.
        this.player = player;
        isAlive = true;

        bullets.add(this);
        parent.add(this);

        setPosition(new Point2D.Double((double) (int) getPlayer().getPosition().getX() / 2, getPlayer().getPosition().getY()));

        Timer timer = new Timer(1000 / 120, e -> updatePosition());
        timer.start();

        System.out.println("bullet time\n");
    }

    public static ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public Point2D.Double getPosition() {
        return position;
    }

    @Override
    public void setPosition(Point2D p) {
        super.setPosition(p);
    }

    @Override
    public double getTravelSpeed() {
        return GameConstants.BULLET_TRAVEL_SPEED.getValue(); // Arbitrary value
    }

    @Override
    void updatePosition() {
        if (isAlive) {
            setPosition(new Point2D.Double((double) (int) getPlayer().getPosition().getX() / 2, getPlayer().getPosition().getY() - getTravelSpeed()));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Point2D.Double p = getPosition();

        g.setColor(Color.WHITE);
        g.fillRect((int) p.getX(), (int) p.getY(), 5, 25);
    }
}
