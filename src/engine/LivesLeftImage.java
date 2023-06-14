package engine;

import constants.GameConstants;
import items.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LivesLeftImage extends BufferedImage implements GameConstants {
    // WORK IN PROGRESS
    public LivesLeftImage(Player player) {
        super(1, 1, BufferedImage.TYPE_INT_ARGB); // Temporary size to create graphics

        String text = "Lives: ";

        Graphics2D g = createGraphics();
        g.setFont(FONT_TEXT);

        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        BufferedImage lives = null;
        if (player.getLivesLeft() == 3) lives = ImageManager.load(PLAYER_LIVES_THREE_ASSET_PATH);
        if (player.getLivesLeft() == 2) lives = ImageManager.load(PLAYER_LIVES_TWO_ASSET_PATH);
        if (player.getLivesLeft() == 1) lives = ImageManager.load(PLAYER_LIVES_ONE_ASSET_PATH);

        if (lives != null) {
            int width = textWidth + 55 + lives.getWidth();
            int height = Math.max(textHeight, lives.getHeight());

            // Now create the actual size of BufferedImage
            BufferedImage actualSizeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = actualSizeImage.createGraphics();

            g2.setColor(Color.WHITE);
            g2.setFont(FONT_TEXT);
            g2.drawString(text, 0, fm.getAscent());
            g2.drawImage(lives, textWidth + 55, 0, null);


            g.dispose();
            g2.dispose();

            setData(actualSizeImage.getData()); // Copy the data from the new image
        }
    }
}
