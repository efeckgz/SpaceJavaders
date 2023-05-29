package threads;

import constants.GameConstants;

import java.awt.*;
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
