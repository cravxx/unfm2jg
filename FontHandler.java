import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

/**
 * Manages fonts
 * 
 * @author Kaffeinated
 *
 */
public class FontHandler {
	
	/**
	 * global replacement for ftm
	 */
	static FontMetrics fMetrics;
	/**
	 * path used to look for fonts
	 */
	final String fontPath = "data/fonts/";

	public FontHandler() {
		loadFonts();
		System.out.println("Done loading fonts!");
	}

	public void loadFonts() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {			
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(getClass().getClassLoader().getResource(fontPath + "OpenSans-Bold.ttf").getFile())));
			
			/*
			 * how to do it
			 * 0. place ttf, otf, whatever, inside your data/fonts folder
			 * 1. place font name
			 * 2. done 
			 */
			
			//ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,  this.getClass().getResourceAsStream(fontPath + "font name")));
			 
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * print out all the fonts that are usable by java in your environment
		 */
		/*Font[] fonts = ge.getAllFonts();
		for (int i = 0; i < fonts.length; i++) {
			System.out.print(fonts[i].getFontName() + " : ");
			System.out.print(fonts[i].getFamily() + " : ");
			System.out.print(fonts[i].getName());
			System.out.println();
		}*/
	}

}
