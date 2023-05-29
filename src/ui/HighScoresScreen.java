package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

public class HighScoresScreen extends GameView implements GameConstants {
    private final BufferedImage highScoresImg;

    public HighScoresScreen() {
        super();
        this.highScoresImg = createHighScoresImage();
    }

    private BufferedImage createHighScoresImage() {
        int imageHeight = LoginRegisterDialog.getUserCount() * 60;

        BufferedImage img = new BufferedImage(GameConstants.SCREEN_WIDTH, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        g.setFont(FontManager.getFontSubtitle());
        AtomicInteger index = new AtomicInteger(0);
        LoginRegisterDialog.forEach(user -> {
            String username = user[0];
            String userHighScoreStr = user[2];
            String usernameHighScorePairing = String.format("%s %s", username, userHighScoreStr);
            int usernameHighScorePairingWidth = g.getFontMetrics().stringWidth(usernameHighScorePairing);
            g.drawString(usernameHighScorePairing, GameConstants.SCREEN_WIDTH / 2 - usernameHighScorePairingWidth / 2, (index.getAndIncrement() + 1) * 60);
        }, true);

        g.dispose();

        return img;
    }

    @Override
    protected void drawItems(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(FontManager.getFontTitle());

        String highScores = "High Scores";
        int highScoresWidth = g.getFontMetrics().stringWidth(highScores);
        g.drawString(highScores, getWidth() / 2 - highScoresWidth / 2, 150);

        if (highScoresImg != null) {
            int x = (getWidth() - highScoresImg.getWidth()) / 2;
            int y = (getHeight() - highScoresImg.getHeight()) / 2;
            g.drawImage(highScoresImg, x, y, null);
        }
    }
}
