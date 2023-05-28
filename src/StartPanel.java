import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class StartPanel extends JPanel implements GameConstants {
    private final BufferedImage allAliens;
    private String titleColor1 = "#f14f50";
    private String titleColor2 = "#50d070";
    private Color active = Color.WHITE;
    private Color inactive = Color.BLACK;
    private StarField starField;

    public StartPanel() {
        setBackground(Color.BLACK); // set background color

        // synchronize title colors
        // handle user not logged in flashing
        TimeManager.startTimer(1000 / 3, e -> {
            cycleTitleColors();
            if (!LoginRegisterDialog.LOGGED_IN) {
                cycleLoggedInActive();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if (starField == null) {
                    starField = new StarField(getWidth(), getHeight());
                    starField.start();
                }
            }
        });

        // Load font
        FontManager.loadFont(GraphicsEnvironment.getLocalGraphicsEnvironment());

        // load alien assets and add them to the ArrayList
        BufferedImage redAlien = ImageManager.load(RED_ALIEN_ASSET_PATH);
        BufferedImage greenAlien = ImageManager.load(GREEN_ALIEN_ASSET_PATH);
        BufferedImage extraAlien = ImageManager.load(EXTRA_ALIEN_ASSET_PATH);
        BufferedImage yellowALien = ImageManager.load(YELLOW_ALIEN_ASSET_PATH);

        // this array holds alien assets to be drawn on the screen.
        BufferedImage[] aliens = new BufferedImage[]{redAlien, greenAlien, extraAlien, yellowALien};


        // Use the aliens array to create a BufferedImage that contains all the aliens assets
        // with a horizontal distance of 60 pixels.

        // Calculate total width needed
        int totalWidth = (aliens.length - 1) * 30; // spacing
        for (BufferedImage alien : aliens) {
            totalWidth += alien.getWidth(); // add width of each alien
        }

        // Create a new BufferedImage with the required width and a height equal to the tallest alien
        int maxAlienHeight = Arrays.stream(aliens).mapToInt(BufferedImage::getHeight).max().getAsInt();
        allAliens = new BufferedImage(totalWidth, maxAlienHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = allAliens.createGraphics();

        // Draw the aliens onto the new image
        int currentX = 0;
        for (BufferedImage alien : aliens) {
            g2.drawImage(alien, currentX, 0, null);
            currentX += alien.getWidth() + 30; // move to next position
        }
        g2.dispose(); // Always dispose of Graphics objects when you're done with them

        TimeManager.startTimer(1000 / GAME_FPS, e1 -> updateComponent());
    }

    private void updateComponent() {
        revalidate();
        repaint();
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

        starField.draw(g, 0, 0); // draws the star field

        // titles
        g.setColor(Color.decode(titleColor1));
        g.setFont(FontManager.getFontTitle());
        String spaceJavaders = "Space Javaders";
        int spaceJavadersWidth = g.getFontMetrics().stringWidth(spaceJavaders);
        g.drawString(spaceJavaders, getWidth() / 2 - spaceJavadersWidth / 2, 300);

        g.setColor(Color.decode(titleColor2));
        g.setFont(FontManager.getFontSubtitle());
        String bytecodeBattle = "Bytecode Battle";
        int bytecodeBattleWidth = g.getFontMetrics().stringWidth(bytecodeBattle);
        g.drawString(bytecodeBattle, getWidth() / 2 - bytecodeBattleWidth / 2, 350);

        // draws the aliens
        g.drawImage(allAliens, getWidth() / 2 - allAliens.getWidth() / 2, 375, null);

        String loggedInMessage = LoginRegisterDialog.LOGGED_IN ? String.format("Logged in as %s.", LoginRegisterDialog.getCurrentUsername()) : "User not logged in!";
        int loggedInMessageWidth = g.getFontMetrics().stringWidth(loggedInMessage);

        g.setColor(active);
        g.drawString(loggedInMessage, getWidth() / 2 - loggedInMessageWidth / 2, 625);
    }
}
