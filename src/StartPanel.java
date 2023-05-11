import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class StartPanel extends JPanel {
    private Font font;
    private Font fontTitle;
    private Font fontSubtitle;

    private StarField starField;
    private Timer timer;

    private String titleColor1 = "#f14f50";
    private String titleColor2 = "#50d070";

    private final ArrayList<BufferedImage> aliens = new ArrayList<>();

    public StartPanel() {
        setBackground(Color.BLACK); // set background color

        // initialize star field
        SwingUtilities.invokeLater(() -> {
            starField = new StarField(getWidth(), getHeight());

            timer = new Timer(1000 / 120, e -> {
                starField.animate();
                repaint();
            });
            timer.start();
        });

        // synchronize title colors
        Timer colorTimer = new Timer(1000 / 3, e -> cycleTitleColors());
        colorTimer.start();

        // load alien assets and add them to the ArrayList
        BufferedImage redAlien, greenAlien, extraAlien, yellowALien;
        try {
            redAlien = ImageIO.read(Objects.requireNonNull(getClass().getResource("Assets/red.png")));
            aliens.add(redAlien);

            greenAlien = ImageIO.read(Objects.requireNonNull(getClass().getResource("Assets/green.png")));
            aliens.add(greenAlien);

            extraAlien = ImageIO.read(Objects.requireNonNull(getClass().getResource("Assets/extra.png")));
            aliens.add(extraAlien);

            yellowALien = ImageIO.read(Objects.requireNonNull(getClass().getResource("Assets/yellow.png")));
            aliens.add(yellowALien);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // load font
        try {
            //  FIX THE PATH!!!!!!!
            font = Font.createFont(Font.TRUETYPE_FONT, new File("/Users/efeacikgoz/projects/SpaceJavaders/src/Assets/upheavtt.ttf"));
            // font = Font.createFont(Font.TRUETYPE_FONT, new File("Assets/upheavtt.ttf"));
            fontTitle = font.deriveFont(100f);
            fontSubtitle = font.deriveFont(32f);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        if (font != null) ge.registerFont(font);
    }

    // method to cycle the title colors - this is called in a timer
    private void cycleTitleColors() {
        String temp = titleColor1;
        titleColor1 = titleColor2;
        titleColor2 = temp;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        starField.draw(g, 0, 0); // draws the star field

        // titles
        g.setColor(Color.decode(titleColor1));
        g.setFont(fontTitle);
        g.drawString("Space Javaders", getWidth() / 16, 300);

        g.setColor(Color.decode(titleColor2));
        g.setFont(fontSubtitle);
        g.drawString("Bytecode Battle", 315, 350);

        // draws the aliens
        int x = 350;
        for (BufferedImage alien : aliens) {
            g.drawImage(alien, x, 375, null);
            x += 60;
        }
    }
}
