import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class HighScoresPanel extends JPanel implements GameConstants {
    private final HashMap<String, Integer> usernameScoreMap = new HashMap<>();
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

        g.setFont(FontManager.getFontSubtitle());
        AtomicInteger y = new AtomicInteger(getHeight() / 2); // Atomic variable used to be able to use in Consumer
        LoginRegisterDialog.traverseUsers(user -> {
            String username = user[0];
            String userHighScoreStr = user[2];

            if (!username.equals("admin")) { // Don't show admin because they are not a real player
                String usernameHighScorePairing = String.format("%s %s\n", username, userHighScoreStr);
                int usernameHighScorePairingWidth = g.getFontMetrics().stringWidth(usernameHighScorePairing);

                g.drawString(usernameHighScorePairing, getWidth() / 2 - usernameHighScorePairingWidth / 2, y.get());
                y.addAndGet(60);
            }
        });
    }
}
