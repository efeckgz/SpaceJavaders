import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class StarField implements Runnable, GameConstants {
    private final BufferedImage starField;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private int yOffset;

    public StarField(int width, int height) {
        starField = createStarField(width, height);
        yOffset = starField.getHeight();
    }


    // WORK IN PROGRESS!!!
    public static ComponentAdapter starFieldComponentListener(JPanel parent) {
        return new ComponentAdapter() {
            // Creation of a StarField object depends on the panels width and height, however they need to be
            // nonzero. When the component gets created, it gets "resized" from 0x0 to the correct width and
            // height. That is why the star field is initialized in the componentResized() method.
            @Override
            public void componentResized(ComponentEvent e) {
                StarField starField = new StarField(parent.getWidth(), parent.getHeight());
                starField.start(); // Start the StarField thread
            }
        };
    }

    public void start() {
        running.set(true);
        new Thread(this).start();
    }

    public void stop() {
        running.set(false);
    }

    private BufferedImage createStarField(int width, int height) {
        BufferedImage starField = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = starField.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.WHITE);
        Random random = new Random();

        for (int i = STAR_FIELD_OFFSET; i < width - STAR_FIELD_OFFSET; i += STAR_FIELD_STEP) {
            for (int j = STAR_FIELD_STEP; j < height - STAR_FIELD_OFFSET; j += STAR_FIELD_STEP) {
                if (random.nextFloat() < 0.3) g.fillOval(i, j, 3, 3);
            }
        }
        g.dispose();

        return starField;
    }

    public void animate(float deltaTime) {
        yOffset -= STAR_FIELD_SPEED * deltaTime;
        if (yOffset < 0) yOffset = starField.getHeight();
    }

    public void draw(Graphics g, int x, int y) {
        int starFieldHeight = starField.getHeight();
        int firstPartHeight = starFieldHeight - yOffset;
        int secondPartHeight = yOffset;

        // Draw the first part
        g.drawImage(
                starField,
                x, // destination rectangle x1
                y, // destination rectangle y1
                starField.getWidth(), // destination rectangle x2
                y + firstPartHeight, // destination rectangle y2
                0, // source rectangle x1
                secondPartHeight, // source rectangle y1
                starField.getWidth(), // source rectangle x2
                starFieldHeight, // source rectangle y2
                null
        );

        // Draw the second part
        g.drawImage(
                starField,
                x, // destination rectangle x1
                y + firstPartHeight, // destination rectangle y1
                starField.getWidth(), // destination rectangle x2
                y + starFieldHeight, // destination rectangle y2
                0, // source rectangle x1
                0, // source rectangle y1
                starField.getWidth(), // source rectangle x2
                yOffset, // source rectangle y2
                null
        );
    }

    @Override
    public void run() {
        long lastUpdateTime = System.currentTimeMillis();
        while (running.get()) {
            long now = System.currentTimeMillis();
            long deltaTime = now - lastUpdateTime;

            animate(deltaTime);

            lastUpdateTime = now;

            try {
                Thread.sleep(GAME_UPDATE_RATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
