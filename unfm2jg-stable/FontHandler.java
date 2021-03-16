import fallk.logmaster.HLogger;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Manages fonts
 *
 * @author Kaffeinated
 */
class FontHandler {

    /**
     * global replacement for ftm
     */
    public static FontMetrics fMetrics;
    /**
     * path used to look for fonts
     */
    private static final String fontPath = "data/fonts/";

    public FontHandler() {
        loadFonts();
        HLogger.info("Done loading fonts!");
    }

    private void loadFonts() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontPath + "OpenSans-Bold.ttf")));

			/*
			 * how to do it
			 * 0. place ttf, otf, whatever, inside your data/fonts folder
			 * 1. place font name
			 * 2. done 
			 */

            //ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,  this.getClass().getResourceAsStream(fontPath + "font name")));
			 
			/*
			 * print out all the fonts that are usable by java in your environment
			 */
			/*Font[] fonts = ge.getAllFonts();
			for (int i = 0; i < fonts.length; i++) {
				HLogger.info(fonts[i].getFontName() + " : ");
				HLogger.info(fonts[i].getFamily() + " : ");
				HLogger.info(fonts[i].getName());
			}*/

        } catch (FontFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
