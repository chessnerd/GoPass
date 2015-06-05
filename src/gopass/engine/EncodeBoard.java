package gopass.engine;

import java.util.Random;

/**
 * Encodes a board to generate a password
 * 
 * @author Jason Mey
 * 
 * @version 1.1
 * @version 3/30/2015
 */
public class EncodeBoard {

	/** Whether the program should use all available ASCII symbols */
	public static final int USE_ALL_SYMBOLS = 0;

	/** Whether the program should use select ASCII symbols */
	public static final int USE_SOME_SYMBOLS = 1;

	/** Whether the program should use no ASCII symbols */
	public static final int USE_NO_SYMBOLS = 2;

	/** Standard number of characters per move */
	private static final int PER_MOVE = 2;

	/** Size of the go board */
	private static final int BOARD_SIZE = 19;

	/** The random number generator */
	private Random rand;

	/** An array to store all of the board values */
	private String[][] board;

	/** Whether or not symbols will be used */
	private int useSymbols;

	/**
	 * Creates a randomized board given a seed
	 * 
	 * Uses the default 2 characters per move
	 * 
	 * @param seed the seed for the random number generator
	 */
	public EncodeBoard(long seed) {
		this(seed, PER_MOVE, USE_ALL_SYMBOLS);
	}

	/**
	 * Creates a randomized board given a seed and number of characters per move
	 * 
	 * @param seed the seed for the random number generator
	 * @param charPerMove the number of characters for each move
	 */
	public EncodeBoard(long seed, int charPerMove, int useSymbols) {
		rand = new Random(seed);
		board = new String[BOARD_SIZE][BOARD_SIZE];
		this.useSymbols = useSymbols;

		// Encode the board with random characters
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = "";
				for (int x = 0; x < charPerMove; x++) {
					if (useSymbols == USE_ALL_SYMBOLS) {
						board[i][j] = board[i][j] + getRandChar();
					} else if (useSymbols == USE_SOME_SYMBOLS) {
						board[i][j] = board[i][j] + getSelectSymbol();
					} else {
						board[i][j] = board[i][j] + getNonSymbol();
					}
				}
			}
		}
	}

	/**
	 * Generates a random ASCII character
	 * 
	 * @return a random character
	 */
	private char getRandChar() {
		if (useSymbols == USE_ALL_SYMBOLS) {
			// Only give us characters at least at ASCII 33 (!)
			// but no higher than ASCII 126 (~)
			return (char) (rand.nextInt(126 - 33) + 33);
		} else if (useSymbols == USE_SOME_SYMBOLS) {
			return getSelectSymbol();
		} else {
			return getNonSymbol();
		}
	}

	/**
	 * This method can return any ASCII letter or number and some symbols.
	 * Specifically, not <, >, {, }, [, ], /, \, |, ', ", ;, &, #, ~, or ?
	 * Instead, only use !, @, $, %, ^, *, -, _, +, or =
	 * 
	 * @return a random, but select, character
	 */
	private char getSelectSymbol() {
		// Find out if the character should be a number, upper case letter,
		// lower case letter, or symbol
		int type = rand.nextInt(72);

		// Return a non-symbol
		if (type < 62) {
			return getNonSymbol();
		}

		// Otherwise, give us a select symbol
		switch (type) {
		case 62:
			return '!';
		case 63:
			return '@';
		case 64:
			return '$';
		case 65:
			return '%';
		case 66:
			return '^';
		case 67:
			return '*';
		case 68:
			return '-';
		case 69:
			return '_';
		case 70:
			return '+';
		}

		// case 71:
		return '=';
	}

	/**
	 * Gets a character that is not a symbol
	 * 
	 * @return a random non-symbol character
	 */
	private char getNonSymbol() {
		// Find out if the character should be a number, upper case letter, or
		// lower case letter
		int type = rand.nextInt(62);

		int nonSymbol = 0;

		// Give us a number
		if (type < 10) {
			nonSymbol = rand.nextInt(57 - 48) + 48;
		}
		// Give us an uppercase letter
		else if (type < 36) {
			nonSymbol = rand.nextInt(90 - 65) + 65;
		}
		// Give us a lowercase letter
		else {
			nonSymbol = rand.nextInt(122 - 97) + 97;
		}

		return (char) nonSymbol;
	}

	/**
	 * Gets the board after it is generated
	 * 
	 * @return the randomized board
	 */
	public String[][] getBoard() {
		return board;
	}
}
