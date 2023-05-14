import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class StartPanel extends JPanel {
    // Initializing title related variables.
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
    private final BufferedImage[] aliens;

    public StartPanel() {
        setBackground(Color.BLACK); // set background color
        //FontManager.loadFont(GraphicsEnvironment.getLocalGraphicsEnvironment());

        // synchronize title colors
        // handle user not logged in flashing
        new Timer(1000 / 3, e -> {
            cycleTitleColors();
            if (!loggedIn) {
                cycleLoggedInActive();
            }
        }).start();

        // Load font
        FontManager.loadFont(GraphicsEnvironment.getLocalGraphicsEnvironment());

        // load alien assets and add them to the ArrayList
        BufferedImage redAlien = ImageManager.load("Assets/red.png");
        BufferedImage greenAlien = ImageManager.load("Assets/green.png");
        BufferedImage extraAlien = ImageManager.load("Assets/extra.png");
        BufferedImage yellowALien = ImageManager.load("Assets/yellow.png");

        aliens = new BufferedImage[] {redAlien, greenAlien, extraAlien, yellowALien};
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

            new Timer(1000 / 120, e -> {
                starField.animate();
                repaint();
            }).start();

            starFieldInitialized = true;
        }

        starField.draw(g, 0, 0); // draws the star field

        // titles
        g.setColor(Color.decode(titleColor1));
        g.setFont(FontManager.getFontTitle());
        g.drawString("Space Javaders", getWidth() / 16, 300);

        g.setColor(Color.decode(titleColor2));
        g.setFont(FontManager.getFontSubtitle());
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
