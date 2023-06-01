package utils;

import abstracts.Screen;
import constants.GameConstants;
import threads.GameUpdateThread;
import ui.*;

import javax.swing.*;
import java.awt.*;

public class WindowManager extends JFrame implements GameConstants {
    private final JMenuItem backToStartItem;
    private final JMenuItem playGameItem;

    public WindowManager() {
        // Load the game font
        FontManager.initialize(GraphicsEnvironment.getLocalGraphicsEnvironment());

        // Loading the start screen
        StartScreen startScreen = new StartScreen();
        startScreen.startTimer(); // Start the timer of start screen
        add(startScreen);

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
        JPanel current = (JPanel) getContentPane().getComponent(0);

        backToStartItem.setVisible(!(getContentPane().getComponent(0) instanceof StartScreen));
        playGameItem.setVisible(LoginRegisterDialog.LOGGED_IN && !(current instanceof GameScreen));
    }

    private void switchScreens(Screen screen) {
        JPanel current = (JPanel) getContentPane().getComponent(0);
        if (current instanceof GameScreen) {
            // If the player is switching from GamePanel, save their game progress and stop the game loop
            GameScreen gp = (GameScreen) current;
            LoginRegisterDialog.saveHighScore(gp.getPlayer());
            gp.stopGameThread();
        }

        Screen currentScreen = (Screen) current;
        currentScreen.stopTimer();

        remove(currentScreen); // Remove the current screen
        add(screen); // add the desired screen
        screen.startTimer(); // Start the update timer for the screen to add
        screen.requestFocusInWindow();

        updateMenuItems();
        revalidate();
        repaint();
    }

    private void playGameItemActionHandler() {
        if (LoginRegisterDialog.LOGGED_IN) {
            GameScreen gameScreen = new GameScreen();
            GameUpdateThread gameUpdateThread = gameScreen.getGameUpdateThread();

            //gameScreen.createAliens();
            gameUpdateThread.setGameOverAction(() -> {
                gameUpdateThread.stop();
                LoginRegisterDialog.saveHighScore(gameScreen.getPlayer());
                switchScreens(new GameOverScreen(gameScreen.getPlayer()));
            });

            switchScreens(gameScreen);
        }
    }

    private void backToStartItemActionHandler() {
        switchScreens(new StartScreen());
    }

    private void highScoresItemActionHandler() {
        switchScreens(new HighScoresScreen());
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
                new ImageIcon(ImageManager.load(RED_ALIEN_ASSET_PATH))
        );
        updateMenuItems();
    }
}
