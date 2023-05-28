import javax.swing.*;

public class Main {
    public static boolean debug = false;

    public static void main(String[] args) {
        try {
            System.setProperty("apple.awt.application.name", "Space Javaders"); // Change the macOS menu bar application name
            System.setProperty("apple.laf.useScreenMenuBar", "true"); // Use native macOS menu bar if possible
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(WindowManager::new); // berhiv
    }
}