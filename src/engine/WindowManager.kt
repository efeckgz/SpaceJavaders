package engine

import abstracts.AbstractGameItem.Companion.clearItems
import abstracts.AbstractScreen
import constants.GameConstants
import engine.FontManager.initialize
import engine.ImageManager.load
import ui.*
import java.awt.GraphicsEnvironment
import java.awt.Toolkit
import java.awt.event.ActionEvent
import javax.swing.*

class WindowManager : JFrame(), GameConstants {
    private val backToStartItem: JMenuItem
    private val playGameItem: JMenuItem

    init {
        // Load the game font
        initialize(GraphicsEnvironment.getLocalGraphicsEnvironment())

        // Loading the start screen
        val startScreen = StartScreen()
        startScreen.startTimer() // Start the timer of start screen
        add(startScreen)
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        // setting window properties
        title = GameConstants.WINDOW_TITLE
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT)
        setLocationRelativeTo(null)
        isResizable = false
        isVisible = true
        iconImage = load(GameConstants.RED_ALIEN_ASSET_PATH)
        val menuBar = JMenuBar()
        // The file menu
        val fileMenu = JMenu("File")

        // Adding the sub-menu items to the file menu
        val loginRegisterItem = JMenuItem("Login/Register")
        fileMenu.add(loginRegisterItem)
        playGameItem = JMenuItem("Play Game")
        fileMenu.add(playGameItem)
        val highScoresItem = JMenuItem("High Scores")
        fileMenu.add(highScoresItem)
        backToStartItem = JMenuItem("Back to Start")
        fileMenu.add(backToStartItem)
        val aboutItem = JMenuItem("About")
        fileMenu.add(aboutItem)

        // adding file menu to menu bar
        menuBar.add(fileMenu)

        // setting the frames menu bar
        jMenuBar = menuBar

        // Handling click actions on the menu bar items
        playGameItem.addActionListener { e: ActionEvent? -> playGameItemActionHandler() }
        backToStartItem.addActionListener { e: ActionEvent? -> backToStartItemActionHandler() }
        loginRegisterItem.addActionListener { e: ActionEvent? -> loginRegisterItemActionHandler() }
        aboutItem.addActionListener { e: ActionEvent? -> aboutItemActionHandler() }
        highScoresItem.addActionListener { e: ActionEvent? -> highScoresItemActionHandler() }
        updateMenuItems()
    }

    // This method gets called after a menu items is clicked to make sure the correct menu items are displayed.
    private fun updateMenuItems() {
        val current = contentPane.getComponent(0) as JPanel
        backToStartItem.isVisible = contentPane.getComponent(0) !is StartScreen
        playGameItem.isVisible = LoginRegisterDialog.LOGGED_IN && current !is GameScreen
    }

    private fun switchScreens(abstractScreen: AbstractScreen) {
        val current = contentPane.getComponent(0) as JPanel
        if (current is GameScreen) {
            // If the player is switching from GamePanel, save their game progress and stop the game loop
            val gp = current
            LoginRegisterDialog.saveHighScore(gp.player)
            gp.stopGameThread()
        }
        val currentAbstractScreen = current as AbstractScreen
        currentAbstractScreen.stopTimer()
        remove(currentAbstractScreen) // Remove the current screen
        add(abstractScreen) // add the desired screen
        abstractScreen.startTimer() // Start the update timer for the screen to add
        abstractScreen.requestFocusInWindow()
        updateMenuItems()
        revalidate()
        repaint()
    }

    private fun playGameItemActionHandler() {
        if (LoginRegisterDialog.LOGGED_IN) {
            val gameScreen = GameScreen()
            val gameUpdateThread = gameScreen.gameUpdateThread

            //gameScreen.createAliens();
            gameUpdateThread.gameOverAction = Runnable {
                gameUpdateThread.stop()
                LoginRegisterDialog.saveHighScore(gameScreen.player)
                switchScreens(GameOverScreen(gameScreen.player))
            }
            switchScreens(gameScreen)
        }
    }

    private fun backToStartItemActionHandler() {
        clearItems()
        switchScreens(StartScreen())
    }

    private fun highScoresItemActionHandler() {
        clearItems()
        switchScreens(HighScoresScreen())
    }

    private fun loginRegisterItemActionHandler() {
        val loginRegisterDialog = LoginRegisterDialog(this)
        loginRegisterDialog.isVisible = true
        updateMenuItems()
    }

    private fun aboutItemActionHandler() {
        JOptionPane.showMessageDialog(
                null,
                "Space Javaders: Bytecode Battle\nCreated by: Efe Açıkgöz\n20210702094",
                "About",
                JOptionPane.INFORMATION_MESSAGE,
                ImageIcon(load(GameConstants.RED_ALIEN_ASSET_PATH))
        )
        updateMenuItems()
    }
}
