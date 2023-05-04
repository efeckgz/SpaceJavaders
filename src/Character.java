public abstract class Character extends GameItem {
    protected int hp;
    protected boolean isEnemy;

    public abstract int getHp();

    public void takeDamage(int damage) {
        this.hp = getHp() - damage;
    }

    public abstract boolean getIsEnemy();
}
