package gopass.gui;

import gopass.Brand;
import gopass.engine.EncodeBoard;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JViewport;

/**
 * The side panel for the GUI
 * 
 * @author Jason Mey
 * @version 1.1
 * @version 3/30/2015
 */
@SuppressWarnings("serial")
public class SidePanel extends JPanel {

	/** Default number of characters per move */
	private static final int DEFAULT_CHARS_PER_MOVE = 2;

	/** The "Make Password" button */
	private JButton passButton;

	/** The "Clear Board" button */
	private JButton clearButton;

	/** The GoGUI element this side panel is a part of */
	private GoGUI gui;

	/** The text area for displaying the password output */
	private JTextArea passwordOutput;

	/** A viewport for the text area */
	private JViewport view;

	/** Radio button for deciding to allow all symbols in passwords */
	private JRadioButton allowAll;

	/** Radio button for deciding to allow some symbols in passwords */
	private JRadioButton allowSome;

	/** Radio button for deciding to allow no symbols in passwords */
	private JRadioButton allowNone;

	/**
	 * Creates the side panel given which GoGUI it is a part of
	 * 
	 * @param gui the GoGUI this panel is on
	 */
	public SidePanel(GoGUI gui) {
		super(new BorderLayout());
		this.gui = gui;

		// Create the password output text area for showing the password
		passwordOutput = new JTextArea(20, 20);
		passwordOutput.setLineWrap(true);
		passwordOutput.setFont(Brand.FONT);

		// Create a viewport for the text area
		view = new JViewport();
		view.setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

		// Put the output text onto the viewport
		view.add(passwordOutput);

		// Add panel with radio buttons for choosing whether to allow symbols
		JPanel symbols = new JPanel(new BorderLayout());

		// First, label the panel so the button text can be less verbose
		JLabel allowSymbols = new JLabel("Allow Symbols?");
		allowSymbols.setFont(Brand.FONT);

		// Set the button's text and font and add them to a group
		allowAll = new JRadioButton("All");
		allowSome = new JRadioButton("Some");
		allowNone = new JRadioButton("None");
		allowAll.setFont(Brand.FONT);
		allowSome.setFont(Brand.FONT);
		allowNone.setFont(Brand.FONT);
		ButtonGroup symbolButtons = new ButtonGroup();
		symbolButtons.add(allowAll);
		symbolButtons.add(allowSome);
		symbolButtons.add(allowNone);

		// By default, allow all symbols
		allowAll.setSelected(true);

		// Put the radio buttons on a panel with a grid layout
		JPanel symbolPanel = new JPanel(new GridLayout(2, 2));
		symbolPanel.add(allowAll);
		symbolPanel.add(allowSome);
		symbolPanel.add(allowNone);

		// Add the label to the north and the buttons to the center
		symbols.add(allowSymbols, BorderLayout.NORTH);
		symbols.add(symbolPanel, BorderLayout.CENTER);

		// Create the "Make Password" button
		passButton = new JButton("Make Password");
		passButton.setFont(Brand.FONT);
		passButton.addActionListener(new Passer());

		// Create the "Clear Board" button
		clearButton = new JButton("Clear Board");
		clearButton.setFont(Brand.FONT);
		clearButton.addActionListener(new Clear());

		// Add the password and clear buttons to a single panel
		JPanel passPanel = new JPanel(new GridLayout(2, 1));
		passPanel.add(passButton);
		passPanel.add(clearButton);

		// Put the symbols selection and other buttons onto one panel
		JPanel dualPanel = new JPanel(new BorderLayout());
		dualPanel.add(symbols, BorderLayout.CENTER);
		dualPanel.add(passPanel, BorderLayout.SOUTH);

		// Finally, add the viewport and dual panel to the side panel
		this.add(view, BorderLayout.CENTER);
		this.add(dualPanel, BorderLayout.SOUTH);
	}

	/**
	 * Sets the password output text area to the specified string
	 * 
	 * @param text the password output
	 */
	public void setPasswordOutput(String text) {
		passwordOutput.setText(text);
	}

	/**
	 * Adds the given string to the password output text area
	 * 
	 * @param text the string to append
	 */
	public void appendToPasswordOutput(String text) {
		passwordOutput.setText(passwordOutput.getText() + text);
	}

	/**
	 * Listener for the "Make Password" button
	 */
	private class Passer implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Figure out how the board should be encoded based on the user
			// preferences
			int encode;

			if (allowAll.isSelected()) {
				encode = EncodeBoard.USE_ALL_SYMBOLS;
			} else if (allowSome.isSelected()) {
				encode = EncodeBoard.USE_SOME_SYMBOLS;
			} else {
				encode = EncodeBoard.USE_NO_SYMBOLS;
			}

			gui.setPassword(DEFAULT_CHARS_PER_MOVE, encode);
		}
	}

	/**
	 * Listener for the "Clear Board" button
	 */
	private class Clear implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			gui.clearBoard();
		}
	}
}
