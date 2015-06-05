package gopass;

/**
 * Defines a go game
 * 
 * @author Jason Mey
 * @version 1.0
 */
public interface GoGame {

	/** Denotes a passed turn */
	public static final int PASS = -1;

	/**
	 * Plays a stone at the specified location
	 * 
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public void play(int x, int y);

	/**
	 * Denotes that the player has passed on their turn
	 */
	public void pass();

	/**
	 * Gets the board being played on
	 * 
	 * @return the board being played on
	 */
	public GoBoard getBoard();

	/**
	 * Gets the number of turns there have been
	 * 
	 * @return the number of turns there have been
	 */
	public int getTurn();

	/**
	 * Ends the game and tolls the score
	 */
	public void gameOver();

	/**
	 * @return whether or not stones were captured on the previous turn
	 */
	public boolean stonesCapped();
}
