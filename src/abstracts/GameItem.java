package abstracts;

import constants.GameConstants;
import models.Alien;
import models.Bullet;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class GameItem implements GameConstants {
    // Every game object must extend this abstract class. The abstract class includes the position of the item
    // and methods to update it. This abstract class may get bigger in the future.

    // CopyOnWriteArrayList that holds all the game items in it. Loop over this ArrayList and run
    // updatePosition() and draw() methods on everything.
    // This is a special type of ArrayList that achieves thread safety, at the cost of system resources
    // by making a copy of the ArrayList when dealing with mutative operations such as adding and removing.
    private static final CopyOnWriteArrayList<GameItem> items = new CopyOnWriteArrayList<>();

    // A Point2D instance holds the position of each abstracts.GameItem. This one here uses the double data type.
    protected Point2D.Double position = new Point2D.Double();
    // The asset of the item
    protected BufferedImage asset;

    public GameItem() {
        items.add(this);
    }

    public static void updateAllPositions(float deltaTime) {
        if (!GameItem.getItems().isEmpty())
            for (GameItem item : GameItem.getItems()) item.updatePosition(deltaTime);
    }

    public static void drawALlItems(Graphics g) {
        if (!GameItem.getItems().isEmpty())
            for (GameItem item : GameItem.getItems()) item.draw(g);
    }

    public static CopyOnWriteArrayList<GameItem> getItems() {
        return items;
    }

    public static void clearItems() {
        items.clear();
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
//    public boolean intersects(abstracts.GameItem other) {
//        double x1 = this.getPosition().x;
//        double y1 = this.getPosition().y;
//        double w1 = this.getAsset().getWidth();
//        double h1 = this.getAsset().getHeight();
//
//        double x2 = other.getPosition().x;
//        double y2 = other.getPosition().y;
//        double w2 = other.getAsset().getWidth();
//        double h2 = other.getAsset().getHeight();
//
//        // Check if bounding boxes overlap
//        return x1 < x2 + w2 && x2 < x1 + w1 && y1 < y2 + h2 && y2 < y1 + h1;
//    }

    public boolean intersects(GameItem other) {
        int thisX = (int) this.getPosition().x;
        int thisY = (int) this.getPosition().y;
        int thisWidth, thisHeight;

        if (this instanceof Bullet) {
            thisWidth = BULLET_WIDTH;
            thisHeight = BULLET_HEIGHT;
        } else {
            thisWidth = this.getAsset().getWidth(null);
            thisHeight = this.getAsset().getHeight(null);
        }

        int otherX = (int) other.getPosition().x;
        int otherY = (int) other.getPosition().y;
        int otherWidth, otherHeight;

        if (other instanceof Bullet) {
            otherWidth = BULLET_WIDTH;
            otherHeight = BULLET_HEIGHT;
        } else {
            otherWidth = other.getAsset().getWidth(null);
            otherHeight = other.getAsset().getHeight(null);
        }

        return thisX < otherX + otherWidth && thisX + thisWidth > otherX
                && thisY < otherY + otherHeight && thisY + thisHeight > otherY;
    }


    public void draw(Graphics g) {
        if (this instanceof Bullet) {
            Bullet b = (Bullet) this;
            g.setColor(Color.WHITE);
            if (b.getIsAlive()) {
                // Bullets are of rectangular shape, so fillRect() is used.
                g.fillRect((int) getPosition().getX(), (int) getPosition().getY(), BULLET_WIDTH, BULLET_HEIGHT);
            }
        } else if (this instanceof Alien) {
            Alien alien = (Alien) this;
            if (alien.getIsAlive()) {
                g.drawImage(getAsset(), (int) getPosition().getX(), (int) getPosition().getY(), null);
            }
        } else {
            g.drawImage(getAsset(), (int) getPosition().getX(), (int) getPosition().getY(), null);
        }
    }
}
