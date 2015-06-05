package gopass.engine;

/**
 * A class for storing a single move's record
 * 
 * @author Jason Mey
 * @version 1.0
 */
public class Record {

	/** The x-coordinate of the move */
	private int xCoor;

	/** The y-coordinate of the move */
	private int yCoor;

	/** The color of the stone played */
	private int color;

	/**
	 * Creates a new record, given the information of a move
	 * 
	 * @param x
	 *            the row the move was played in
	 * @param y
	 *            the column the move was played in
	 * @param color
	 *            the color of the stone played
	 */
	public Record(int x, int y, int color) {
		xCoor = x;
		yCoor = y;
		this.color = color;
	}

	/**
	 * Whether or not the move was a pass
	 * 
	 * @return whether or not the move was a pass
	 */
	public boolean wasPass() {
		if (xCoor == Game.PASS && yCoor == Game.PASS) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the x-coordinate of the move
	 * 
	 * @return the x-coordinate of the move
	 */
	public int getXCoor() {
		return xCoor;
	}

	/**
	 * Gets the y-coordinate of the move
	 * 
	 * @return the y-coordinate of the move
	 */
	public int getYCoor() {
		return yCoor;
	}

	/**
	 * Gets the color of the stone played
	 * 
	 * @return the color of the stone played
	 */
	public int getColor() {
		return color;
	}

	/**
	 * String form of the record
	 */
	public String toString() {
		if (wasPass()) {
			return "PASS";
		}
		char yLetter = 'a';
		if (yCoor < 26) {
			yLetter = (char) (yLetter + yCoor);
			return yLetter + "" + (xCoor + 1);
		}
		return (yCoor + 1) + " " + (xCoor + 1);
	}
}
