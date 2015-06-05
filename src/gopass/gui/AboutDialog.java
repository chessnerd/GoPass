package gopass.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gopass.Brand;

/**
 * A dialog box used to display information about this program
 * 
 * @author Jason Mey
 * @version 1.0
 * @version 3/2/2015
 */
@SuppressWarnings("serial")
public class AboutDialog extends JDialog {

	/** The font of the title */
	private static final Font TITLE_FONT = new Font("serif", Font.BOLD, 19);

	/** A spacer */
	private static final String SPACER = "\n---------------------------------------------------\n";

	/**
	 * Creates and displays the about dialog
	 */
	public AboutDialog(Frame frame) {
		super(frame, "About " + Brand.SHORT_NAME);

		// Create the central panel
		JPanel panel = new JPanel(new BorderLayout());

		// Create the title label
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel title = new JLabel(" " + Brand.APP_NAME + " " + Brand.VERSION);
		title.setFont(TITLE_FONT);
		titlePanel.add(title);

		// Put the brand icon on the title
		ImageIcon icon = Brand.getIcon();
		if (icon != null) {
			title.setIcon(icon);
		}

		// Add the title to the main panel
		panel.add(BorderLayout.NORTH, titlePanel);

		// Create and populate the informational pane
		InfoPane info = new InfoPane(10, 50);
		info.setTextFont(new Font("monospaced", Font.PLAIN, 11));
		info.setInfo(Brand.APP_NAME + "\nVersion: " + Brand.FULL_VERSION + "\n"
				+ Brand.COPYRIGHT_INFO  + "\n" + "Find at: " + Brand.WEBSITE + SPACER + Brand.README_SHORT + SPACER
				+ "\nThis software is released under the MIT License:" + "\n\n"
				+ Brand.LICENSE);

		// Add the info pane to the main panel
		panel.add(BorderLayout.CENTER, info);

		// Move the scroll bar back to the top
		info.toTop();

		// Adds the main panel to the dialog
		add(panel);

		// Pack and set non-resizable
		pack();
		setResizable(false);

		// Put the dialog in the center of the screen
		setLocationRelativeTo(null);

		// Make it visible
		setVisible(true);
	}
}