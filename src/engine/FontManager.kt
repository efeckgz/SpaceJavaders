package engine

import constants.GameConstants
import java.awt.Font
import java.awt.FontFormatException
import java.awt.GraphicsEnvironment
import java.io.IOException
import java.util.*

object FontManager : GameConstants {
    private var font: Font? = null
    var fontTitle: Font? = null
        private set
    var fontSubtitle: Font? = null
        private set
    var fontText: Font? = null
        private set

    // load font
    @JvmStatic
    fun initialize(ge: GraphicsEnvironment) {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FontManager::class.java.getResourceAsStream(GameConstants.FONT_PATH)))
            fontTitle = font.deriveFont(GameConstants.FONT_TITLE_SIZE)
            fontSubtitle = font.deriveFont(GameConstants.FONT_SUBTITLE_SIZE)
            fontText = font.deriveFont(GameConstants.FONT_TEXT_SIZE)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: FontFormatException) {
            e.printStackTrace()
        }
        if (font != null) {
            ge.registerFont(font)
        }
    }
}
