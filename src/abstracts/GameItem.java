package abstracts;

import constants.GameConstants;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class GameItem implements GameConstants {
    // Every game object must extend this abstract class. The abstract class includes the position of the item
    // and methods to update it. This abstract class may get bigger in the future.

    // CopyOnWriteArrayList that holds all the game items in it. Loop over this ArrayList and run
    // updatePosition() and draw() methods on everything.
    // This is a special type of ArrayList that achieves thread safety, at the cost of system resources
    // by making a copy of the ArrayList when dealing with mutative operations such as adding and removing.
//    private static final CopyOnWriteArrayList<GameItem> items = new CopyOnWriteArrayList<>();


    private static final ConcurrentLinkedQueue<GameItem> items = new ConcurrentLinkedQueue<>();

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

    public static void drawAllItems(Graphics g) {
        for (GameItem item : GameItem.getItems()) item.draw(g);
    }

    public static ConcurrentLinkedQueue<GameItem> getItems() {
        return items;
    }

    public static void clearItems() {
        items.clear();
    }

    public void destroy() {
        items.remove(this);
    }

    public double getX() {
        return this.getPosition().getX();
    }

    public double getY() {
        return this.getPosition().getY();
    }

    public Point2D.Double getPosition() {
        return position;
    }

    public void setPosition(Point2D.Double p) {
        position = p;
    }

    public BufferedImage getAsset() {
        return asset;
    }

    protected void setAsset(BufferedImage asset) {
        this.asset = asset;
    }

    public abstract double getTravelSpeed();

    // Calculate the changes in items position and update it accordingly using setPosition.
    protected abstract void updatePosition(float deltaTime);

    // Method for collision detection.
    public boolean intersects(GameItem other) {
        double x1 = this.getPosition().x;
        double y1 = this.getPosition().y;
        double w1 = this.getAsset().getWidth();
        double h1 = this.getAsset().getHeight();

        double x2 = other.getPosition().x;
        double y2 = other.getPosition().y;
        double w2 = other.getAsset().getWidth();
        double h2 = other.getAsset().getHeight();

        // Check if bounding boxes overlap
        return x1 < x2 + w2 && x2 < x1 + w1 && y1 < y2 + h2 && y2 < y1 + h1;
    }

    protected abstract void draw(Graphics g);
}
