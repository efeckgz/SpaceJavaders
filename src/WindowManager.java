import javax.swing.*;

public class WindowManager extends JFrame implements GameConstants {
    private final JMenuItem backToStartItem;
    private final JMenuItem playGameItem;

    public WindowManager() {
        // Loading the start screen
        StartPanel startPanel = new StartPanel();
        add(startPanel);

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

        updateMenuItems();
    }

    // This method gets called after a menu items is clicked to make sure the correct menu items are displayed.
    private void updateMenuItems() {
        backToStartItem.setVisible(!(getContentPane().getComponent(0) instanceof StartPanel));
        playGameItem.setVisible(LoginRegisterDialog.LOGGED_IN);
    }

    private void switchToPanel(JPanel panel) {
        JPanel current = (JPanel) getContentPane().getComponent(0);
        if (current instanceof GamePanel) ((GamePanel) current).stopGameThread();
        remove(current);
        add(panel);
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
                LoginRegisterDialog.saveHighScore(gamePanel.getPlayer().getUsername());
                switchToPanel(new GameOverPanel(gamePanel.getPlayer()));
            });
            switchToPanel(gamePanel);
        }
    }

    private void backToStartItemActionHandler() {
        switchToPanel(new StartPanel());
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
