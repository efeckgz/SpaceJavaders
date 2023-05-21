import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class FontManager implements GameConstants {
    private static Font font;
    private static Font fontTitle;
    private static Font fontSubtitle;
    private static Font fontText;

    // load font
    public static void loadFont(GraphicsEnvironment ge) {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FontManager.class.getResourceAsStream(FONT_PATH)));
            fontTitle = font.deriveFont(FONT_TITLE_SIZE);
            fontSubtitle = font.deriveFont(FONT_SUBTITLE_SIZE);
            fontText = font.deriveFont(FONT_TEXT_SIZE);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        if (font != null) {
            ge.registerFont(font);
        }
    }

    public static Font getFontSubtitle() {
        return fontSubtitle;
    }

    public static Font getFontTitle() {
        return fontTitle;
    }

    public static Font getFontText() {
        return fontText;
    }
}
