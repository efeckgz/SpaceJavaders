package ui;

import java.awt.*;

public class GameOverScreen extends GameView implements GameConstants {
    private final Player player;

    public GameOverScreen(Player player) {
        super();
        this.player = player;
    }

    @Override
    protected void drawItems(Graphics g) {
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
