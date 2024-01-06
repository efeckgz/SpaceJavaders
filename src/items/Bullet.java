package items;

import abstracts.AbstractGameItem;
import engine.TimeManager;
import main.Main;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Bullet extends AbstractGameItem {
    private static final ArrayList<Bullet> bullets = new ArrayList<>();
    private static final BufferedImage assetSetter;

    static {
        // Set the asset here
        assetSetter = new BufferedImage(BULLET_WIDTH, BULLET_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = assetSetter.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, assetSetter.getWidth(), assetSetter.getHeight());

        g.dispose();
    }

    private final Player player;

    public Bullet(Player player) {
        super();

        this.player = player;

        // Place the bullet at the center of the players asset and just above it
        setPosition(new Point2D.Double(
                (player.getPosition().getX() + (double) player.getAsset().getWidth() / 2) - (double) BULLET_WIDTH / 2,
                player.getPosition().getY() - player.getAsset().getHeight()
        ));

        setAsset(assetSetter);

        if (Main.debug) {
            TimeManager.startTimer(1000, e -> {
                if (isValid()) {
                    System.out.printf(
                            "models.Bullet %s location (x, y): %.2f, %.2f\n",
                            Bullet.getBullets().indexOf(this),
                            getPosition().getX(),
                            getPosition().getY()
                    );
                }
            }, () -> !player.isValid());
        }

        bullets.add(this);
    }

    public static ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void checkForCollisions(Runnable gameOverAction) {
        if (isValid) {
            for (Alien alien : Alien.getAliens()) {
                if (alien.isValid() && this.intersects(alien)) {
                    this.setIsValid(false); // Kill the bullet on impact

                    alien.setLivesLeft(alien.getLivesLeft() - 1); // Decrease alien life
                    alien.setIsValid(); // Kill the alien if it has no lives left

                    // Things to do if the alien is dead
                    if (!alien.isValid()) {
                        // increment player score
                        player.setScore(player.getScore() + 1);
                        player.setCurrentHighScore(Math.max(player.getCurrentHighScore(), player.getScore()));

                        // If all the aliens are dead, show the game over screen
                        if (Alien.getAliens().stream().noneMatch(AbstractGameItem::isValid)) {
                            gameOverAction.run();
                        }
                    }

                    return; // Do not keep checking if the bullet hits other aliens
                }
            }
        }
    }

    @Override
    protected void setIsValid() {
        this.isValid = getPosition().y > 0;
    }

    @Override
    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    @Override
    public double getTravelSpeed() {
        return BULLET_TRAVEL_SPEED;
    }

    @Override
    public void updatePosition(float deltaTime) {
        getPosition().y -= getTravelSpeed() * deltaTime;
        setIsValid();
    }

    @Override
    protected void draw(Graphics g) {
        if (isValid()) {
            g.drawImage(getAsset(), (int) getPosition().getX(), (int) getPosition().getY(), null);
        }
    }
}

