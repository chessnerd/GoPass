package gopass.gui;

import gopass.Brand;
import gopass.GoBoard;
import gopass.GoGame;
import gopass.engine.EncodeBoard;
import gopass.engine.Game;
import gopass.engine.Record;
import gopass.engine.RecordBook;
import gopass.gui.AboutDialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 * The main GUI window
 * 
 * @author Jason Mey
 * @version 1.1.1
 * @version 9/26/2015
 */
@SuppressWarnings("serial")
public class GoGUI extends JFrame implements GoGame {

	/** Help dialog at the top of the frame */
	private JLabel helpLine1 = new JLabel(
			"To generate a password, simply play moves, then click \"Make Password\". Order matters.");

	/** Second row of the help dialog */
	private JLabel helpLine2 = new JLabel(
			"Enter your own seed below for more variation.");

	/** The game engine */
	private Game game;

	/** A graphics-based board */
	private GraphicBoard board;

	/** The panel where the graphic board goes */
	private JPanel graphicPanel;

	/** The side panel */
	private SidePanel side;

	/** The seed code field */
	private JPasswordField code;

	/** Brings up the about dialog */
	private JButton about;

	/** How many rows the board has */
	private int rows;

	/** How many columns the board has */
	private int cols;

	/**
	 * Creates a GUI with a board of the default (19x19) size
	 */
	public GoGUI() {
		super(Brand.APP_NAME + " " + Brand.VERSION);

		// Create a new GUI with a 19x19 board
		this.rows = 19;
		this.cols = 19;
		game = new Game(rows, cols);

		// Place the help information at the top of the frame
		JPanel northPanel = new JPanel(new GridLayout(2, 1));
		helpLine1.setFont(Brand.FONT);
		helpLine1.setHorizontalAlignment(JLabel.CENTER);
		northPanel.add(helpLine1);
		helpLine2.setFont(Brand.FONT);
		helpLine2.setHorizontalAlignment(JLabel.CENTER);
		northPanel.add(helpLine2);
		this.add(BorderLayout.NORTH, northPanel);

		// Create the graphical board and add it to the center
		graphicPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		board = new GraphicBoard(rows, cols, game.getBoard(), this);
		graphicPanel.add(board);
		this.add(BorderLayout.CENTER, graphicPanel);
		board.repaint();

		// Add the side panel to the right side
		side = new SidePanel(this);
		this.add(BorderLayout.EAST, side);

		// Create and label the "Custom Seed" field
		code = new JPasswordField(15);
		code.setFont(Brand.FONT);
		JLabel codeText = new JLabel("Custom Seed:", JLabel.CENTER);
		codeText.setFont(Brand.FONT);

		// Add the "About" button
		about = new JButton("About");
		about.setFont(Brand.FONT);
		about.addActionListener(new About());

		// Add the code field and about button to the bottom
		JPanel southPanel = new JPanel(new GridLayout(2, 3));
		southPanel.add(new JLabel(" "));
		southPanel.add(codeText);
		southPanel.add(new JLabel(" "));
		southPanel.add(new JLabel(" "));
		southPanel.add(code);
		JPanel aboutPanel = new JPanel(new GridLayout(1, 3));
		aboutPanel.add(new JLabel(" "));
		aboutPanel.add(new JLabel(" "));
		aboutPanel.add(about);
		southPanel.add(aboutPanel);
		this.add(BorderLayout.SOUTH, southPanel);

		// Set the frame icon
		this.setIconImage(Brand.getIcon().getImage());

		// If the user hits the close button, close the whole program
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Pack it up and keep it that way
		pack();
		setResizable(false);
		setMinimumSize(new Dimension(getSize()));

		// Put it in the middle of the screen
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Sets the password in the side panel based on the seed code and moves
	 */
	protected void setPassword(int charsPerMove, int useSymbols) {
		// Convert the seed into a long for the RNG
		long seed = explodeStringToLong(code.getPassword());

		// Encode a board based on the seed
		EncodeBoard encodedBoard = new EncodeBoard(seed, charsPerMove,
				useSymbols);

		// Get the current turn so we know how long to iterate
		int turn = getTurn();

		// Build the password based on the game record
		String password = "";
		for (int i = 0; i < turn; i++) {
			Record r = game.getRecordBook().getRecord(i);
			String[][] board = encodedBoard.getBoard();
			String s = board[r.getXCoor()][r.getYCoor()];
			password = password + s;

			// Now update the encoded board based on the value of the previous
			// move to increase the randomness of the subsequent characters
			encodedBoard = new EncodeBoard(seed
					+ explodeStringToLong(s.toCharArray()), charsPerMove,
					useSymbols);
		}
		side.setPasswordOutput(password);

		// Warn the user if their password is short
		if (password.length() < 12) {
			side.appendToPasswordOutput("\n\nWARNING: This \npassword is short!");
		}

		// Warn the user if they didn't use a seed
		if (code.getPassword().length == 0) {
			side.appendToPasswordOutput("\n\nNOTE: No seed used.");
		}
	}

	/**
	 * Converts a character array to a long based on its ASCII values. This
	 * method also increases the size of the number based on character position.
	 * 
	 * This allows for greater variations in seeds. For example, it means that
	 * the seeds generated from "top" and "pot" come out to different values.
	 * 
	 * @param str array to convert
	 * @return the converted long
	 */
	private long explodeStringToLong(char[] str) {
		// Start the value equal to the total length of the string
		long value = str.length;

		// For every character, add the ASCII value to the value
		// First, multiply the character by its position to increase variation
		for (int i = 0; i < str.length; i++) {
			int character = str[i];
			long explodedChar = character * (i + 1);
			value = value + explodedChar;
		}

		return value;
	}

	/**
	 * Clears the board
	 */
	protected void clearBoard() {
		// End the game and start a new one
		game.gameOver();
		game = new Game(rows, cols);

		// Remove the old board and put on a new one based on the new game
		graphicPanel.remove(board);
		board = new GraphicBoard(rows, cols, game.getBoard(), this);
		graphicPanel.add(board);

		// Repaint and validate the frame
		this.repaint();
		board.repaint();
		this.validate();
	}

	/**
	 * Brings up the about dialog
	 */
	private void aboutDialog() {
		new AboutDialog(this);
	}

	/**
	 * @return whether or not stones were captured on the previous turn
	 */
	public boolean stonesCapped() {
		return game.stonesCapped();
	}

	/**
	 * Plays a move onto the board and updates the board if there was a capture
	 */
	public void play(int x, int y) {
		int blackCap = game.getBlackCap();
		int whiteCap = game.getWhiteCap();

		game.play(x, y);

		// If there was a capture, force a whole board update
		if (blackCap != game.getBlackCap() || whiteCap != game.getWhiteCap()) {
			board.captureUpdate();
		}
	}

	public void pass() {
		game.pass();
	}

	public void gameOver() {
		game.gameOver();
	}

	public GoBoard getBoard() {
		return board;
	}

	public int getTurn() {
		return game.getTurn();
	}
	
	public RecordBook getRecordBook() {
		return game.getRecordBook();
	}

	/**
	 * Listener for the "About" button
	 */
	private class About implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			aboutDialog();
		}
	}
}
