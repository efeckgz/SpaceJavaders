import java.awt.geom.Point2D;

public abstract class Character extends GameItem {
    protected int hp;
    protected boolean isEnemy;
    public abstract int getHp();
    public void takeDamage(int damage) {
        this.hp = getHp() - damage;
    }
    public abstract boolean getIsEnemy();
    public boolean isDead() {
        return getHp() == 0;
    }
}
