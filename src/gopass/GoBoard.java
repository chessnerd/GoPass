package gopass;

/**
 * Defines a go board
 * 
 * @author Jason Mey
 * @version 1.0
 */
public interface GoBoard {

	/** Denotes an empty space on the board */
	public static final int EMPTY = 0;

	/** Denotes a space with a black piece */
	public static final int BLACK = 1;

	/** Denotes a space with a white piece */
	public static final int WHITE = 2;

	/**
	 * Places a piece on the board
	 * 
	 * @param x the x-coordinate of the piece
	 * @param y the y-coordinate of the piece
	 * @param color the color of the piece
	 */
	public void place(int x, int y, int color);

	/**
	 * Gets the number of rows
	 * 
	 * @return the number of rows
	 */
	public int getRowNum();

	/**
	 * Gets the number of columns
	 * 
	 * @return the number of columns
	 */
	public int getColNum();

	/**
	 * Gets the color of the stone at the given coordinates
	 * 
	 * @param x the x-coordinate of the stone
	 * @param y the y-coordinate of the stone
	 * @return the color of the stone
	 */
	public int getStoneAt(int x, int y);

}
