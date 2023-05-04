public class Bullet extends GameItem {
    public Bullet() {
        // constructor creates the bullet at the tip of the players muzzle and handles the logic
        // for updating its location. This method should track the required properties of the bullet
        // (its location, whether it is destroyed or not etc.) and handle its destruction accordingly.
    }

    @Override
    public double getTravelSpeed() {
        return 5; // Arbitrary value
    }

    @Override
    void updatePosition() {

    }
}
