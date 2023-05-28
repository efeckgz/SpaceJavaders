import javax.swing.*;
import java.awt.*;

public class WindowManager extends JFrame implements GameConstants {
    private final JMenuItem backToStartItem;
    private final JMenuItem playGameItem;

    public WindowManager() {
        // Load the game font
        FontManager.loadFont(GraphicsEnvironment.getLocalGraphicsEnvironment());

        // Loading the start screen
        StartPanel startPanel = new StartPanel();
        add(startPanel);


//        HighScoresPanel hsp = new HighScoresPanel();
//        add(hsp);

        LoginRegisterDialog.forEach(user -> {
            System.out.printf("username: %s high score: %s\n", user[0], user[2]);
        });

        // setting window properties
        setTitle(WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setIconImage(ImageManager.load(RED_ALIEN_ASSET_PATH));

        JMenuBar menuBar = new JMenuBar();
        // The file menu
        JMenu fileMenu = new JMenu("File");

        // Adding the sub-menu items to the file menu
        JMenuItem loginRegisterItem = new JMenuItem("Login/Register");
        fileMenu.add(loginRegisterItem);
        playGameItem = new JMenuItem("Play Game");
        fileMenu.add(playGameItem);
        JMenuItem highScoresItem = new JMenuItem("High Scores");
        fileMenu.add(highScoresItem);
        backToStartItem = new JMenuItem("Back to Start");
        fileMenu.add(backToStartItem);
        JMenuItem aboutItem = new JMenuItem("About");
        fileMenu.add(aboutItem);

        // adding file menu to menu bar
        menuBar.add(fileMenu);

        // setting the frames menu bar
        setJMenuBar(menuBar);

        // Handling click actions on the menu bar items
        playGameItem.addActionListener(e -> playGameItemActionHandler());
        backToStartItem.addActionListener(e -> backToStartItemActionHandler());
        loginRegisterItem.addActionListener(e -> loginRegisterItemActionHandler());
        aboutItem.addActionListener(e -> aboutItemActionHandler());
        highScoresItem.addActionListener(e -> highScoresItemActionHandler());

        updateMenuItems();
    }

    // This method gets called after a menu items is clicked to make sure the correct menu items are displayed.
    private void updateMenuItems() {
        backToStartItem.setVisible(!(getContentPane().getComponent(0) instanceof StartPanel));
        playGameItem.setVisible(LoginRegisterDialog.LOGGED_IN);
    }

    private void switchToPanel(JPanel panel) {
        JPanel current = (JPanel) getContentPane().getComponent(0);
        if (current instanceof GamePanel) {
            // If the player is switching from GamePanel, save their game progress and stop the game loop
            GamePanel gp = (GamePanel) current;
            LoginRegisterDialog.saveHighScore(gp.getPlayer());
            gp.stopGameThread();
        }

        remove(current); // Remove the current panel
        add(panel); // add the desired panel
        panel.requestFocusInWindow();

        updateMenuItems();
        revalidate();
        repaint();
    }

    private void playGameItemActionHandler() {
        if (LoginRegisterDialog.LOGGED_IN) {
            GamePanel gamePanel = new GamePanel();
            GameUpdateThread gameUpdateThread = gamePanel.getGameUpdateThread();
            gameUpdateThread.setGameOverAction(() -> {
                gameUpdateThread.stop();
                LoginRegisterDialog.saveHighScore(gamePanel.getPlayer());
                switchToPanel(new GameOverPanel(gamePanel.getPlayer()));
            });
            switchToPanel(gamePanel);
        }
    }

    private void backToStartItemActionHandler() {
        switchToPanel(new StartPanel());
    }

    private void highScoresItemActionHandler() {
        switchToPanel(new HighScoresPanel());
    }

    private void loginRegisterItemActionHandler() {
        LoginRegisterDialog loginRegisterDialog = new LoginRegisterDialog(this);
        loginRegisterDialog.setVisible(true);
        updateMenuItems();
    }

    private void aboutItemActionHandler() {
        JOptionPane.showMessageDialog(
                null,
                "Space Javaders: Bytecode Battle\nCreated by: Efe Açıkgöz\n20210702094",
                "About",
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon(ImageManager.load("Assets/red.png"))
        );
        updateMenuItems();
    }
}
