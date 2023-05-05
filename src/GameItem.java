import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class GameItem implements CanMove {
    // Every game object must extend this abstract class. The abstract class includes the position of the Item
    // and methods to update it. This abstract class may get bigger in the future.

    // A Point2D instance holds the position of each GameItem. THis one here uses the double data type.
    Point2D position = new Point2D.Double();

    // This is an arraylist that holds every item in the game.
    // The constructor of this class adds every object created to this arraylist.
    // In the main game loop, this arraylist will be iterated over and
    // Its value's "updatePosition()" method will be called.
    private static final ArrayList<GameItem> gameItems = new ArrayList<>(); // made final to silence warning

    public GameItem() {
        GameItem.getGameItems().add(this); // Add the created object to the arraylist.
    }

    // getter setters - these will be used in updatePosition() implementations.
    public Point2D getPosition() {
        return position;
    }
    public void setPosition(Point2D p) {
        position = p;
    }

    @Override
    public abstract double getTravelSpeed();

    public static ArrayList<GameItem> getGameItems() {
        return gameItems;
    }

    // Calculate the changes in items position and update it accordingly using setPosition.
    abstract void updatePosition();
}
