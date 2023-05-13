import java.awt.geom.Point2D;

public interface CanMove {
    // This interface must be implemented by every object that can move (so all the GameItem objects)
    double getTravelSpeed();
    Point2D.Double position = new Point2D.Double();
}
