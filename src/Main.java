import javax.swing.*;

public class Main {
    public static boolean debug = false;

    public static void main(String[] args) {
        // Use native macOS menu bar if possible
        try {
            System.setProperty("apple.awt.application.name", "Space Javaders");
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(WindowManager::new);
    }
}