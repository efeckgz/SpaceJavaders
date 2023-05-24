import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class GameItem extends JPanel implements GameConstants {
    // Every game object must extend this abstract class. The abstract class includes the position of the item
    // and methods to update it. This abstract class may get bigger in the future.

    // ArrayList that holds all the game items in it. Loop over this ArrayList and run
    // updatePosition() on everything.
    private static final ArrayList<GameItem> items = new ArrayList<>();

    // A Point2D instance holds the position of each GameItem. This one here uses the double data type.
    protected Point2D.Double position = new Point2D.Double();
    // The asset of the item
    protected BufferedImage asset;

    public GameItem() {
        items.add(this);
    }

    public static void updateAllPositions(float deltaTime) {
        for (GameItem item : GameItem.getItems()) item.updatePosition(deltaTime);
    }

    public static ArrayList<GameItem> getItems() {
        return items;
    }

    public Point2D.Double getPosition() {
        return position;
    }

    public void setPosition(Point2D.Double p) {
        position = p;
    }

    protected BufferedImage getAsset() {
        return asset;
    }

    protected void setAsset(BufferedImage asset) {
        this.asset = asset;
    }

    public abstract double getTravelSpeed();

    // Calculate the changes in items position and update it accordingly using setPosition.
    abstract void updatePosition(float deltaTime);

    // Method for collision detection.
    abstract boolean intersects(GameItem item);

    public void draw(Graphics g) {
        if (this instanceof Bullet) {
            Bullet b = (Bullet) this;
            if (b.getIsAlive()) {
                // Bullets are of rectangular shape, so fillRect() is used.
                g.fillRect((int) getPosition().getX(), (int) getPosition().getY(), BULLET_WIDTH, BULLET_HEIGHT);
            }

            return;
        }

        g.drawImage(getAsset(), (int) getPosition().getX(), (int) getPosition().getY(), null);
    }
}
