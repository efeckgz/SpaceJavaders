import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

public class HighScoresPanel extends JPanel implements GameConstants {
    private final BufferedImage userScoresImg;
    private StarField starField;

    public HighScoresPanel() {
        setBackground(Color.BLACK);
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

        int imageHeight = LoginRegisterDialog.getUserCount() * 60;

        BufferedImage img = new BufferedImage(SCREEN_WIDTH, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        g.setFont(FontManager.getFontSubtitle());
        AtomicInteger index = new AtomicInteger(0);
        LoginRegisterDialog.forEach(user -> {
            String username = user[0];
            String userHighScoreStr = user[2];
            String usernameHighScorePairing = String.format("%s %s", username, userHighScoreStr);
            int usernameHighScorePairingWidth = g.getFontMetrics().stringWidth(usernameHighScorePairing);
            g.drawString(usernameHighScorePairing, SCREEN_WIDTH / 2 - usernameHighScorePairingWidth / 2, (index.getAndIncrement() + 1) * 60);
        }, true);

        g.dispose();

        this.userScoresImg = img;

        TimeManager.startTimer(1000 / GAME_FPS, e -> updateComponent());
    }

    private void updateComponent() {
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        starField.draw(g, 0, 0);

        g.setColor(Color.WHITE);
        g.setFont(FontManager.getFontTitle());

        String highScores = "High Scores";
        int highScoresWidth = g.getFontMetrics().stringWidth(highScores);
        g.drawString(highScores, getWidth() / 2 - highScoresWidth / 2, 150);

        if (userScoresImg != null) {
            int x = (getWidth() - userScoresImg.getWidth()) / 2;
            int y = (getHeight() - userScoresImg.getHeight()) / 2;
            g.drawImage(userScoresImg, x, y, null);
        }

//        g.setFont(FontManager.getFontSubtitle());
//        AtomicInteger y = new AtomicInteger(getHeight() / 2); // Atomic variable used to be able to use in Consumer
//        LoginRegisterDialog.forEach(user -> {
//            String username = user[0];
//            String userHighScoreStr = user[2];
//
//            String usernameHighScorePairing = String.format("%s %s\n", username, userHighScoreStr);
//            int usernameHighScorePairingWidth = g.getFontMetrics().stringWidth(usernameHighScorePairing);
//
//            g.drawString(usernameHighScorePairing, getWidth() / 2 - usernameHighScorePairingWidth / 2, y.get());
//            y.addAndGet(60);
//        }, true);
    }
}
