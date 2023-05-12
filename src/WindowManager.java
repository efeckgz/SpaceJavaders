import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class WindowManager extends JFrame {

    public WindowManager() {
        // setting window properties
        setTitle("Space Javaders: Bytecode Battle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        setSize(GameConstants.SCREEN_WIDTH.getValue(), GameConstants.SCREEN_HEIGHT.getValue());
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        // Adding the sub-menu items to the file menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem loginRegisterItem = new JMenuItem("Login/Register");
        fileMenu.add(loginRegisterItem);
        JMenuItem playGameItem = new JMenuItem("Play Game");
        fileMenu.add(playGameItem);
        JMenuItem highScoresItem = new JMenuItem("High Scores");
        fileMenu.add(highScoresItem);
        JMenuItem backToStartItem = new JMenuItem("Back to Start");
        fileMenu.add(backToStartItem);
        JMenuItem aboutItem = new JMenuItem("About");
        fileMenu.add(aboutItem);

        // adding file menu to menu bar
        menuBar.add(fileMenu);

        // setting the frames menu bar
        setJMenuBar(menuBar);

        // Loading the start screen
        StartPanel startPanel = new StartPanel();
        add(startPanel);

        // Adding action listeners for menu bar items
        playGameItem.addActionListener(e -> {
            GamePanel gamePanel = new GamePanel();
            remove(startPanel);
            add(gamePanel);
            setVisible(true);
            revalidate();
            repaint();
            gamePanel.requestFocusInWindow();
        });

        backToStartItem.addActionListener(e -> {
            // frame.remove();
        });

        loginRegisterItem.addActionListener(e -> {
            LoginRegisterDialog loginRegisterDialog = new LoginRegisterDialog(this);
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
    }
}
