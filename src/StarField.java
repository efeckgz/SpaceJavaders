import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class StarField implements GameConstants {
    private final BufferedImage starField;
    private int yOffset;

    public StarField(int width, int height) {
        starField = createStarField(width, height);
        yOffset = starField.getHeight();
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
}
