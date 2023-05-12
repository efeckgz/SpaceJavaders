import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class WindowManager {
    public static void mainWindow() {
        // Use native macOS menu bar if possible
        try {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        } catch (Exception ignored) {  }

        JFrame frame = new JFrame("Space Javaders: Bytecode Battle"); // Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar(); // Create the menu bar

        // Creating & adding menus
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        // Creating & adding menu items
        JMenuItem loginRegisterItem = new JMenuItem("Login/Register");
        fileMenu.add(loginRegisterItem);

        JMenuItem playGameItem = new JMenuItem("Play Game");
        fileMenu.add(playGameItem);

        JMenuItem highScoresItem = new JMenuItem("High Scores");
        fileMenu.add(highScoresItem);

        JMenuItem backToStart = new JMenuItem("Back to Start");
        backToStart.setVisible(false);
        fileMenu.add(backToStart);

        fileMenu.addSeparator();

        JMenuItem aboutItem = new JMenuItem("About");
        fileMenu.add(aboutItem);

        StartPanel startPanel = new StartPanel();
        frame.add(startPanel);

        playGameItem.addActionListener(e -> {
            GamePanel gamePanel = new GamePanel();
            frame.remove(startPanel);
            frame.add(gamePanel);
            backToStart.setVisible(true);
            frame.revalidate();
            frame.repaint();
            gamePanel.requestFocusInWindow();
        });

        backToStart.addActionListener(e -> {
            // frame.remove();
        });

        loginRegisterItem.addActionListener(e -> {
            LoginRegisterDialog loginRegisterDialog = new LoginRegisterDialog(frame);
            loginRegisterDialog.setVisible(true);
        });

        aboutItem.addActionListener(e -> {
            try {
                BufferedImage img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("Assets/red.png")));
                JOptionPane.showMessageDialog(
                        null,
                        "Space Javaders: Bytecode Battle\nCreated by: Efe Açıkgöz\n20210702094",
                        "About",
                        JOptionPane.INFORMATION_MESSAGE,
                        new ImageIcon(img)
                );
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        // Setting frame properties
        frame.pack();
        frame.setJMenuBar(menuBar);
        frame.setSize(GameConstants.SCREEN_WIDTH.getValue(), GameConstants.SCREEN_HEIGHT.getValue());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
