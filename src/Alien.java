public class Alien extends Character {
    private final boolean isStrong;

    public Alien(boolean isStrong) {
        this.isStrong = isStrong;
    }

    public boolean getIsStrong() {
        return isStrong;
    }

    @Override
    public int getHp() {
        return getIsStrong() ? 20 : 10;
    }

    @Override
    public boolean getIsEnemy() {
        return true;
    }

    @Override
    public double getTravelSpeed() {
        return 0; // I will try to make the aliens move with the background
    }

    @Override
    public void updatePosition() {
        // logic that calculates the new position
        // setPosition(p);
    }
}