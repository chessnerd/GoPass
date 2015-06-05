package gopass.gui;

import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * A pane for displaying information
 * 
 * @author Jason Mey
 * @version 1.0
 */
@SuppressWarnings("serial")
public class InfoPane extends JScrollPane {

	/** The main text area of the pane */
	public JTextArea textArea;

	/**
	 * Creates a blank pane
	 * 
	 * @param rows
	 *            the number of rows for the text area
	 * @param cols
	 *            the number of columns for the text area
	 */
	public InfoPane(int rows, int cols) {
		super();
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setColumns(cols);
		textArea.setRows(rows);
		this.setViewportView(textArea);
		this
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}

	/**
	 * Adds the information to the text area in the pane
	 * 
	 * @param info
	 *            the string of information
	 */
	public void setInfo(String info) {
		textArea.setText(info);
	}

	/**
	 * Sets the font of the text area
	 * 
	 * @param font
	 *            the new font to use
	 */
	public void setTextFont(Font font) {
		textArea.setFont(font);
	}
	
	/**
	 * Move the scroll bar to the top
	 */
	public void toTop() {
		// Moving the caret to the start moves the bar
		textArea.setCaretPosition(0);
	}
}
