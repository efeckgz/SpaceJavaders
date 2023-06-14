package main;

import engine.UserManager;
import engine.WindowManager;

import javax.swing.*;

public class Main {
    public static boolean debug = false;

    public static void main(String[] args) {
        try {
            System.setProperty("apple.awt.application.name", "Space Javaders"); // Change the macOS menu bar application name
            System.setProperty("apple.laf.useScreenMenuBar", "true"); // Use native macOS menu bar if possible
        } catch (Exception ignored) {
        }

        // Log info & sources
        System.out.println(
                "Space Javaders: Bytecode Battle\nCreated by Efe Açıkgöz\n\nSources:\nFont: https://www.dafont.com/upheaval.font\nAssets: https://opengameart.org/content/assets-for-a-space-invader-like-game"
        );

        UserManager.createUsersFile(); // Create the users file on the first start of the program

        SwingUtilities.invokeLater(WindowManager::new); // berhiv
    }
}