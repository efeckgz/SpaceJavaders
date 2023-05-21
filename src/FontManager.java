import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class FontManager {
    private static Font font;
    private static Font fontTitle;
    private static Font fontSubtitle;
    private static Font fontText;

    // load font
    public static void loadFont(GraphicsEnvironment ge) {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FontManager.class.getResourceAsStream(GameConstants.FONT_PATH)));
            fontTitle = font.deriveFont(100f);
            fontSubtitle = font.deriveFont(32f);
            fontText = font.deriveFont(16f);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        if (font != null) {
            ge.registerFont(font);
        }
    }


    public static Font getFont() {
        return font;
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
