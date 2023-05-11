import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::mainWindow);
    }

    private static void mainWindow() {
        // Use native macOS menu bar if possible
        try {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        } catch (Exception ignored) {  }

        JFrame frame = new JFrame("Space Javaders: Galactic Battle"); // Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar(); // Create the menu bar

        // Creating & adding menus
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        // Creating & adding menu items
        JMenuItem playGameItem = new JMenuItem("Play Game");
        fileMenu.add(playGameItem);

        JMenuItem highScoresItem = new JMenuItem("High Scores");
        fileMenu.add(highScoresItem);

        fileMenu.addSeparator();

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> {
            try {
                BufferedImage img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("Assets/red.png")));
                JOptionPane.showMessageDialog(
                        null,
                        "Space Javaders: Galactic Battle\nCreated by: Efe Açıkgöz\n20210702094",
                        "About",
                        JOptionPane.INFORMATION_MESSAGE,
                        new ImageIcon(img)
                );
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        fileMenu.add(aboutItem);

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        // Setting frame properties
        frame.pack();
        frame.setJMenuBar(menuBar);
        frame.setSize(GameConstants.SCREEN_WIDTH.getValue(), GameConstants.SCREEN_HEIGHT.getValue());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}