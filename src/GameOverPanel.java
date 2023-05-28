import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameOverPanel extends JPanel implements GameConstants {
    private final Player player;
    private StarField starField;

    public GameOverPanel(Player player) {
        setBackground(Color.BLACK);

        this.player = player;

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

        // Draw the game over text and the player score
        g.setColor(Color.WHITE);
        g.setFont(FontManager.getFontTitle());

        String gameOver = "Game Over!";
        int gameOverWidth = g.getFontMetrics().stringWidth(gameOver);
        g.drawString(gameOver, getWidth() / 2 - gameOverWidth / 2, 300);

        g.setFont(FontManager.getFontSubtitle());
        String score = String.format("Score: %d", player.getScore());
        int scoreWidth = g.getFontMetrics().stringWidth(score);
        g.drawString(score, getWidth() / 2 - scoreWidth / 2, 350);
    }
}
