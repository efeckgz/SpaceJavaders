package threads;

import constants.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class StarField extends BufferedImage implements Runnable, GameConstants {
    private final AtomicBoolean running = new AtomicBoolean(false);
    private int yOffset;

    public StarField(int width, int height) {
        super(width, height, BufferedImage.TYPE_INT_RGB);
        createStarField();
        yOffset = getHeight();
    }

    public void start() {
        running.set(true);
        new Thread(this).start();
    }

    private void createStarField() {
        Graphics2D g = createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        Random random = new Random();

        for (int i = STAR_FIELD_OFFSET; i < getWidth() - STAR_FIELD_OFFSET; i += STAR_FIELD_STEP) {
            for (int j = STAR_FIELD_STEP; j < getHeight() - STAR_FIELD_OFFSET; j += STAR_FIELD_STEP) {
                if (random.nextFloat() < 0.3) g.fillOval(i, j, 3, 3);
            }
        }
        g.dispose();
    }

    public void animate(float deltaTime) {
        yOffset -= STAR_FIELD_SPEED * deltaTime;
        if (yOffset < 0) yOffset = getHeight();
    }


    public void draw(Graphics g, int x, int y) {
        int firstPartHeight = getHeight() - yOffset;
        int secondPartHeight = yOffset;

        // Draw the first part
        g.drawImage(
                this,
                x, // destination rectangle x1
                y, // destination rectangle y1
                getWidth(), // destination rectangle x2
                y + firstPartHeight, // destination rectangle y2
                0, // source rectangle x1
                secondPartHeight, // source rectangle y1
                getWidth(), // source rectangle x2
                getHeight(), // source rectangle y2
                null
        );

        // Draw the second part
        g.drawImage(
                this,
                x, // destination rectangle x1
                y + firstPartHeight, // destination rectangle y1
                getWidth(), // destination rectangle x2
                y + getHeight(), // destination rectangle y2
                0, // source rectangle x1
                0, // source rectangle y1
                getWidth(), // source rectangle x2
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

