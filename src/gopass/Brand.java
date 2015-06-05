package gopass;

import gopass.images.Put;

import java.awt.Font;
import java.io.IOException;

import javax.swing.ImageIcon;

/**
 * Branding for GoPass
 * 
 * @author Jason Mey
 * 
 * @version 1.1
 * @version 6/2/2015
 */
public class Brand {

	/** The year of the most recent update */
	public static final int YEAR = 2015;

	/** The last two digits of the year */
	public static final int YEAR_SHORT = 15;

	/** The full application name */
	public static final String APP_NAME = "GoPass Password Generator";

	/** The shortened application name */
	public static final String SHORT_NAME = "GoPass";

	/** The name(s) of the copyright holder(s) */
	public static final String COPYRIGHT_HOLDER = "Jason Mey";

	/** The copyright year(s) */
	public static final String COPYRIGHT_YEAR = "Copyright \u00a9 " + YEAR;

	/** The copyright information */
	public static final String COPYRIGHT_INFO = COPYRIGHT_YEAR + " "
			+ COPYRIGHT_HOLDER + ", All Rights Reserved";
	
	/** The website information */
	public static final String WEBSITE = "sourceforge.net/projects/gopass/";

	/** The default font for GUI elements */
	public static final Font FONT = new Font("monospaced", Font.PLAIN, 12);

	/** The primary version number */
	public static final String VERSION = "1.1";

	/** The full version number */
	public static final String FULL_VERSION = "1.1.0";

	/**
	 * Get the brand icon
	 * 
	 * @return the brand icon
	 */
	public static ImageIcon getIcon() {
		try {
			return new ImageIcon(Put.image(new Brand(), "GoPass64.png"));
		} catch (IOException e) {
			// Can't get the icon
			return null;
		} catch (NullPointerException e) {
			// Can't get the icon
			return null;
		}
	}

	/** Beginning of the README */
	public static final String README_SHORT = "\nGoPass is a password generator that generates passwords "
			+ "based on opening moves in a go game.\n\n"
			+ "The Seed Code at the bottom is used to seed a random number generator (RNG). "
			+ "That RNG assigns a value to every intersection on the go board. "
			+ "After moves are played, and the Make Password button pressed, "
			+ "the random values of the intersections are used to create a password "
			+ "based on the game record. "
			+ "\n\nAll of this is done locally on the computer. "
			+ "No information is ever sent outside of the program.";

	/** The license information */
	public static final String LICENSE = "Permission is hereby granted, free of charge, to any person obtaining a copy of this software "
			+ "and associated documentation files (the \"Software\"), to deal in the Software without restriction, "
			+ "including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, "
			+ "and/or sell copies of the Software, and to permit persons to whom the Software is "
			+ "furnished to do so, subject to the following conditions:\n"
			+ "\nThe above copyright notice and this permission notice shall be included in "
			+ "all copies or substantial portions of the Software.\n"
			+ "\nTHIS SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR "
			+ "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, "
			+ "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE "
			+ "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER "
			+ "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, "
			+ "ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER "
			+ "DEALINGS IN THE SOFTWARE.\n";
}