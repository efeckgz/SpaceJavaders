public class Player extends Character {
    private static int livesLeft = 3;

    public Player() {
        if (getHp() == 0) {
            Player.livesLeft -= 1;
            resetHp();
        }
    }

    @Override
    void updatePosition() {
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

    public static int getLivesLeft() {
        return livesLeft;
    }

    public static void fireBullet() {
        // fire bullets here
        Bullet bullet = new Bullet();
    }
}
