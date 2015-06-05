package gopass.engine;

import gopass.GoBoard;
import gopass.GoGame;

/**
 * The logic of a Go game
 * 
 * @author Jason Mey
 * @version 1.0
 */
public class Game implements GoGame {

	/** Denotes a passed turn */
	public static final int PASS = -1;

	/** The Go board */
	private Board board;

	/** The current turn */
	private int turn;

	/** The record book for the game */
	private RecordBook recordBook;

	/** Whether or not the last move was a pass */
	private boolean previousPass;

	/** Whether or not any stones were captured by the previous move */
	private boolean stonesCapped;

	/** Whether or not liberties have already been checked */
	private boolean[][] checkedLiberties;

	/** The number of stones black has captured */
	private int blackCap;

	/** The number of stones white has captured */
	private int whiteCap;

	/** Whether or not the game is over */
	private boolean gameOver;

	/**
	 * Starts a new game on a 19x19 board
	 */
	public Game() {
		this(19, 19);
	}

	/**
	 * Starts a new game given the dimensions of the board
	 * 
	 * @param rows the number of rows for the board
	 * @param cols the number of columns for the board
	 */
	public Game(int rows, int cols) {
		board = new Board(rows, cols);
		turn = 0;
		recordBook = new RecordBook();
		checkedLiberties = new boolean[rows][cols];
		gameOver = false;
		stonesCapped = false;
	}

	/**
	 * Plays a stone at the specified location
	 * 
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @return whether any stones were captured
	 */
	public void play(int x, int y) {
		int stoneColor = turn % 2 + 1;
		int numCap = 0;
		board.place(x, y, stoneColor);
		recordBook.addRecord(x, y, stoneColor);

		boolean top = false, bottom = false;
		boolean left = false, right = false;

		// If the stone is on the bottom or top row
		if (x == board.getRowNum() - 1) {
			bottom = true;
		} else if (x == 0) {
			top = true;
		}

		// If the stone is on the left or right edge
		if (y == board.getColNum() - 1) {
			right = true;
		} else if (y == 0) {
			left = true;
		}

		// Check toward the top
		if (!top && board.getStoneAt(x - 1, y) != stoneColor
				&& checkLiberties(x - 1, y)) {
			numCap += removeGroup(x - 1, y);
		}
		// Recreate the checked liberties 2D array
		for (int i = 0; i < checkedLiberties.length; i++) {
			for (int j = 0; j < checkedLiberties[0].length; j++) {
				checkedLiberties[i][j] = false;
			}
		}
		// Check toward the bottom
		if (!bottom && board.getStoneAt(x + 1, y) != stoneColor
				&& checkLiberties(x + 1, y)) {
			numCap += removeGroup(x + 1, y);
		}
		// Recreate the checked liberties 2D array
		for (int i = 0; i < checkedLiberties.length; i++) {
			for (int j = 0; j < checkedLiberties[0].length; j++) {
				checkedLiberties[i][j] = false;
			}
		}
		// Check to the left
		if (!left && board.getStoneAt(x, y - 1) != stoneColor
				&& checkLiberties(x, y - 1)) {
			numCap += removeGroup(x, y - 1);
		}
		// Recreate the checked liberties 2D array
		for (int i = 0; i < checkedLiberties.length; i++) {
			for (int j = 0; j < checkedLiberties[0].length; j++) {
				checkedLiberties[i][j] = false;
			}
		}
		// Check to the right
		if (!right && board.getStoneAt(x, y + 1) != stoneColor
				&& checkLiberties(x, y + 1)) {
			numCap += removeGroup(x, y + 1);
		}
		// Recreate the checked liberties 2D array
		for (int i = 0; i < checkedLiberties.length; i++) {
			for (int j = 0; j < checkedLiberties[0].length; j++) {
				checkedLiberties[i][j] = false;
			}
		}

		// See if the player committed suicide
		if (checkLiberties(x, y)) {
			int suiNumCap = removeGroup(x, y);
			if (stoneColor == GoBoard.BLACK) {
				whiteCap += suiNumCap;
			} else if (stoneColor == GoBoard.WHITE) {
				blackCap += suiNumCap;
			}
			board.place(x, y, GoBoard.EMPTY);
			System.out.println(board.getStoneAt(x, y));
		}
		// Recreate the checked liberties 2D array
		for (int i = 0; i < checkedLiberties.length; i++) {
			for (int j = 0; j < checkedLiberties[0].length; j++) {
				checkedLiberties[i][j] = false;
			}
		}

		turn++;
		if (stoneColor == GoBoard.BLACK) {
			blackCap += numCap;
		} else if (stoneColor == GoBoard.WHITE) {
			whiteCap += numCap;
		}
		
		if (numCap > 0) {
			stonesCapped = true;
		} else {
			stonesCapped = false;
		}
		previousPass = false;
	}

	/**
	 * Denotes that the player has passed on their turn
	 */
	public void pass() {
		int stoneColor = turn % 2 + 1;
		recordBook.addRecord(PASS, PASS, stoneColor);

		if (previousPass) {
			gameOver();
		}

		turn++;
		previousPass = true;
	}

	/**
	 * Checks the liberties of the stone at the given location
	 * 
	 * @param x the x-coordinate of the stone
	 * @param y the y-coordinate of the stone
	 * @return if the stone(s) is dead
	 */
	public boolean checkLiberties(int x, int y) {
		checkedLiberties[x][y] = true;
		if (board.getStoneAt(x, y) == Board.EMPTY) {
			return false;
		} else {
			int color = board.getStoneAt(x, y);
			boolean top = false, bottom = false;
			boolean left = false, right = false;

			// If the stone is on the bottom or top row
			if (x == board.getRowNum() - 1) {
				bottom = true;
			} else if (x == 0) {
				top = true;
			}

			// If the stone is on the left or right edge
			if (y == board.getColNum() - 1) {
				right = true;
			} else if (y == 0) {
				left = true;
			}

			if (!top) {
				if (board.getStoneAt(x - 1, y) == Board.EMPTY) {
					return false;
				} else if (board.getStoneAt(x - 1, y) == color) {
					if (!checkedLiberties[x - 1][y]
							&& !checkLiberties(x - 1, y)) {
						return false;
					}
				}
			}

			if (!bottom) {
				if (board.getStoneAt(x + 1, y) == Board.EMPTY) {
					return false;
				} else if (board.getStoneAt(x + 1, y) == color) {
					if (!checkedLiberties[x + 1][y]
							&& !checkLiberties(x + 1, y)) {
						return false;
					}
				}
			}

			if (!left) {
				if (board.getStoneAt(x, y - 1) == Board.EMPTY) {
					return false;
				} else if (board.getStoneAt(x, y - 1) == color) {
					if (!checkedLiberties[x][y - 1]
							&& !checkLiberties(x, y - 1)) {
						return false;
					}
				}
			}

			if (!right) {
				if (board.getStoneAt(x, y + 1) == Board.EMPTY) {
					return false;
				} else if (board.getStoneAt(x, y + 1) == color) {
					if (!checkedLiberties[x][y + 1]
							&& !checkLiberties(x, y + 1)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Removes the group of stones with a member at location x, y
	 * 
	 * @param x the x-coordinate of the group member
	 * @param y the y-coordinate of the group member
	 * @return the number of stones removed
	 */
	public int removeGroup(int x, int y) {
		int color = board.getStoneAt(x, y);
		int numRemoved = 0;
		boolean top = false, bottom = false;
		boolean left = false, right = false;

		board.place(x, y, Board.EMPTY);
		numRemoved++;

		// If the stone is on the bottom or top row
		if (x == board.getRowNum() - 1) {
			bottom = true;
		} else if (x == 0) {
			top = true;
		}

		// If the stone is on the left or right edge
		if (y == board.getColNum() - 1) {
			right = true;
		} else if (y == 0) {
			left = true;
		}

		if (!top) {
			if (board.getStoneAt(x - 1, y) == color) {
				numRemoved += removeGroup(x - 1, y);
			}
		}

		if (!bottom) {
			if (board.getStoneAt(x + 1, y) == color) {
				numRemoved += removeGroup(x + 1, y);
			}
		}

		if (!left) {
			if (board.getStoneAt(x, y - 1) == color) {
				numRemoved += removeGroup(x, y - 1);
			}
		}

		if (!right) {
			if (board.getStoneAt(x, y + 1) == color) {
				numRemoved += removeGroup(x, y + 1);
			}
		}

		return numRemoved;
	}

	/**
	 * Ends the game and tolls the score
	 */
	public void gameOver() {
		gameOver = true;
	}

	/**
	 * Returns whether or not the game is over
	 * 
	 * @return whether or not the game is over
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * Gets the board being played on
	 * 
	 * @return the board being played on
	 */
	public GoBoard getBoard() {
		return board;
	}

	/**
	 * Gets the game record
	 * 
	 * @return the game record book
	 */
	public RecordBook getRecordBook() {
		return recordBook;
	}

	/**
	 * Gets the number of turns there have been
	 * 
	 * @return the number of turns there have been
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Gets the number of stones black has captured
	 * 
	 * @return the number of stones black has captured
	 */
	public int getBlackCap() {
		return blackCap;
	}

	/**
	 * Gets the number of stones white has captured
	 * 
	 * @return the number of stones white has captured
	 */
	public int getWhiteCap() {
		return whiteCap;
	}

	/**
	 * @return whether or not any stones were captured on the previous turn
	 */
	public boolean stonesCapped() {
		return stonesCapped;
	}
}
