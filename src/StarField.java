import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class StarField {
    private BufferedImage starField;
    private int yOffset;

    public StarField(int width, int height) {
        starField = createStarField(width, height);
        yOffset = 0;
    }

    private BufferedImage createStarField(int width, int height) {
        BufferedImage starField = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = starField.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.WHITE);
        Random random = new Random();

        int step = 30;
        int offset = 7;
        for (int i = offset; i < width - offset; i += step) {
            for (int j = step; j < height - offset; j += step) {
                if (random.nextFloat() < 0.3) g.fillOval(i, j, 3, 3);
            }
        }
        g.dispose();

        return starField;
    }

    public void animate() {
        yOffset += 1;
        if (yOffset >= starField.getHeight()) yOffset = 0;
    }

//    public void draw(Graphics g, int x, int y) {
//        int starFieldHeight = starField.getHeight();
//        int firstPartHeight = starFieldHeight - yOffset;
//        int secondPartHeight = yOffset;
//
//        g.drawImage(
//                starField,
//                x,
//                y,
//                starField.getWidth(),
//                firstPartHeight,
//                0,
//                yOffset,
//                starField.getWidth(),
//                starFieldHeight,
//                null
//        );
//
//        g.drawImage(
//                starField,
//                x,
//                y + firstPartHeight,
//                starField.getWidth(),
//                secondPartHeight,
//                0,
//                0,
//                starField.getWidth(),
//                yOffset,
//                null
//        );
//    }
    public void draw(Graphics g, int x, int y) {
        int starFieldHeight = starField.getHeight();
        int firstPartHeight = starFieldHeight - yOffset;
        int secondPartHeight = yOffset;

        g.drawImage(
                starField,
                x,
                y,
                starField.getWidth(),
                y + firstPartHeight,
                0,
                yOffset,
                starField.getWidth(),
                starFieldHeight,
                null
        );

        g.drawImage(
                starField,
                x,
                y + firstPartHeight,
                starField.getWidth(),
                y + starFieldHeight,
                0,
                0,
                starField.getWidth(),
                secondPartHeight,
                null
        );
    }
}
