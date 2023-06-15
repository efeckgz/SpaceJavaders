package engine

import javax.swing.SwingUtilities

object StartupUtilities {
    @JvmStatic
    fun start() {
        try {
            System.setProperty("apple.awt.application.name", "Space Javaders") // Change the macOS menu bar application name
            System.setProperty("apple.laf.useScreenMenuBar", "true") // Use native macOS menu bar if possible
        } catch (ignored: Exception) {
        }

        // Log info & sources
        println(
                "Space Javaders: Bytecode Battle\nCreated by Efe Açıkgöz\n\nFont: https://www.dafont.com/upheaval.font\nAssets: https://opengameart.org/content/assets-for-a-space-invader-like-game"
        )
        UserManager.createUsersFile() // Create the users file on the first start of the program
        SwingUtilities.invokeLater { WindowManager() } // berhiv
    }
}
