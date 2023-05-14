import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontManager {
    private static Font font;
    private static Font fontTitle;
    private static Font fontSubtitle;
    private static Font fontText;

    // load font
    public static void loadFont(GraphicsEnvironment ge) {
        try {
            //  FIX THE PATH!!!!!!!
            font = Font.createFont(Font.TRUETYPE_FONT, new File("/Users/efeacikgoz/projects/SpaceJavaders/src/Assets/upheavtt.ttf"));
            // font = Font.createFont(Font.TRUETYPE_FONT, new File("Assets/upheavtt.ttf"));
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
