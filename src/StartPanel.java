import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class StartPanel extends JPanel {
    // Initializing title related variables.
    private Font font;
    private Font fontTitle;
    private Font fontSubtitle;
    private String titleColor1 = "#f14f50";
    private String titleColor2 = "#50d070";
    private Color active = Color.WHITE;
    private Color inactive = Color.BLACK;

    // Initializing the star field.
    private StarField starField;
    private boolean starFieldInitialized = false;


    // Set to true if the user is logged in.
    private boolean loggedIn = false;

    // this array holds alien assets to be drawn on the screen.
    private BufferedImage[] aliens;

    public StartPanel() {
        setBackground(Color.BLACK); // set background color

        // synchronize title colors
        // handle user not logged in flashing
        Timer colorTimer = new Timer(1000 / 3, e -> {
            cycleTitleColors();
            if (!loggedIn) {
                cycleLoggedInActive();
            }
        });
        colorTimer.start();

        // load alien assets and add them to the ArrayList
        BufferedImage redAlien, greenAlien, extraAlien, yellowALien;
        try {
            redAlien = ImageIO.read(Objects.requireNonNull(getClass().getResource("Assets/red.png")));
            greenAlien = ImageIO.read(Objects.requireNonNull(getClass().getResource("Assets/green.png")));
            extraAlien = ImageIO.read(Objects.requireNonNull(getClass().getResource("Assets/extra.png")));
            yellowALien = ImageIO.read(Objects.requireNonNull(getClass().getResource("Assets/yellow.png")));

            aliens = new BufferedImage[]{redAlien, greenAlien, extraAlien, yellowALien};
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

    private void cycleLoggedInActive() {
        Color temp = active;
        active = inactive;
        inactive = temp;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Initialize the StarField the first time paintComponent is called
        if (!starFieldInitialized) {
            starField = new StarField(getWidth(), getHeight());

            Timer timer = new Timer(1000 / 120, e -> {
                starField.animate();
                repaint();
            });
            timer.start();

            starFieldInitialized = true;
        }

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

        String loggedInMessage = loggedIn ? String.format("Logged in as %s.", "efeckgz") : "User not logged in!";
        int loggedInMessageX = loggedIn ? 274 : 287;

        g.setColor(active);
        g.drawString(loggedInMessage, loggedInMessageX, 625);
    }
}
