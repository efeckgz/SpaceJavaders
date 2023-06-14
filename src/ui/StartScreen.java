package ui;

import abstracts.AbstractScreen;
import constants.GameConstants;
import engine.ImageManager;
import engine.TimeManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class StartScreen extends AbstractScreen implements GameConstants {
    private final BufferedImage allAliens;
    private String titleColor1 = "#f14f50";
    private String titleColor2 = "#50d070";
    private Color active = Color.WHITE;
    private Color inactive = Color.BLACK;

    public StartScreen() {
        super();

        // synchronize title colors
        // handle user not logged in flashing
        TimeManager.startTimer(1000 / 3, e -> {
            cycleTitleColors();
            if (!LoginRegisterDialog.LOGGED_IN) {
                cycleLoggedInActive();
            }
        });

        // load alien assets and add them to the array
        BufferedImage redAlien = ImageManager.load(GameConstants.RED_ALIEN_ASSET_PATH);
        BufferedImage greenAlien = ImageManager.load(GameConstants.GREEN_ALIEN_ASSET_PATH);
        BufferedImage extraAlien = ImageManager.load(GameConstants.EXTRA_ALIEN_ASSET_PATH);
        BufferedImage yellowALien = ImageManager.load(GameConstants.YELLOW_ALIEN_ASSET_PATH);
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
    }

    // method to cycle the title colors - this is called in a timer
    private void cycleTitleColors() {
        String temp = titleColor1;
        titleColor1 = titleColor2;
        titleColor2 = temp;
    }

    private void cycleLoggedInActive() {
        if (!LoginRegisterDialog.LOGGED_IN) {
            Color temp = active;
            active = inactive;
            inactive = temp;
        } else {
            active = Color.WHITE; // set the active color to white if the user is logged in
        }
    }

    @Override
    protected void drawItems(Graphics g) {
        // titles
        g.setColor(Color.decode(titleColor1));
        g.setFont(FONT_TITLE);
        String spaceJavaders = "Space Javaders";
        int spaceJavadersWidth = g.getFontMetrics().stringWidth(spaceJavaders);
        g.drawString(spaceJavaders, getWidth() / 2 - spaceJavadersWidth / 2, 300);

        g.setColor(Color.decode(titleColor2));
        g.setFont(FONT_SUBTITLE);
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
