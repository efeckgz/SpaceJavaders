import java.awt.geom.Point2D;

public class Player extends Character {
    private int livesLeft = 3;

    public Player() {
        setPosition(new Point2D.Double(919.0 / 2.0, 0));

        if (isDead()) {
            this.livesLeft -= 1;
            resetHp();
        }
    }

    @Override
    public double getTravelSpeed() {
        return 30; // Arbitrary value
    }

    @Override
    public void updatePosition() {
        // The position of player will be updated according to the user input.
    }

    @Override
    public int getHp() {
        return 100; // research this
    }

    @Override
    public boolean getIsEnemy() {
        return false;
    }

    public void resetHp() {
        hp = 100;
    }

    public int getLivesLeft() {
        return livesLeft;
    }

    public void fireBullet() {
        // fire bullets here
        Bullet bullet = new Bullet();
    }
}
