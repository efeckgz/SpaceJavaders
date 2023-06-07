package ui;

import abstracts.AbstractScreen;
import constants.GameConstants;
import utils.UserManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

public class HighScoresScreen extends AbstractScreen implements GameConstants {
    private final BufferedImage highScoresImg;

    public HighScoresScreen() {
        super();

        if (UserManager.getUserCount() > 0) {
            this.highScoresImg = createHighScoresImage();
        } else {
            this.highScoresImg = null;
        }
    }

    private BufferedImage createHighScoresImage() {
        int imageHeight = UserManager.getUserCount() * 60;

        BufferedImage img = new BufferedImage(SCREEN_WIDTH, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        g.setFont(FONT_SUBTITLE);
        AtomicInteger index = new AtomicInteger(0);
        UserManager.forEach(true, user -> {
            String username = user[0];
            String userHighScoreStr = user[2];
            String usernameHighScorePairing = String.format("%s %s", username, userHighScoreStr);
            int usernameHighScorePairingWidth = g.getFontMetrics().stringWidth(usernameHighScorePairing);

            g.drawString(usernameHighScorePairing, SCREEN_WIDTH / 2 - usernameHighScorePairingWidth / 2, (index.getAndIncrement() + 1) * 60);
        });

        g.dispose();

        return img;
    }

    @Override
    protected void drawItems(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(FONT_TITLE);

        String highScores = "High Scores";
        int highScoresWidth = g.getFontMetrics().stringWidth(highScores);
        g.drawString(highScores, getWidth() / 2 - highScoresWidth / 2, 150);

        if (highScoresImg != null) {
            int x = (getWidth() - highScoresImg.getWidth()) / 2;
            int y = (getHeight() - highScoresImg.getHeight()) / 2;
            g.drawImage(highScoresImg, x, y, null);
        } else {
            g.setFont(FONT_SUBTITLE);
            String noHighScores = "No high scores yet!";
            int noHighScoresWidth = g.getFontMetrics().stringWidth(noHighScores);
            g.drawString(noHighScores, getWidth() / 2 - noHighScoresWidth / 2, getHeight() / 2);
        }
    }
}
