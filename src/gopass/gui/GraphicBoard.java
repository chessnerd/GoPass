package gopass.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gopass.GoBoard;
import gopass.GoGame;

/**
 * Creates a go board using a Graphics object
 * 
 * @author Jason Mey
 * @version 1.6
 * @version 6/2/2015
 */
@SuppressWarnings("serial")
public class GraphicBoard extends Canvas implements GoBoard {

	/** The color of the Go board */
	public static final Color BOARD_COLOR = new Color(236, 178, 33);

	/** The color of the lines on the Go board */
	public static final Color LINE_COLOR = new Color(1, 1, 1);

	/** The color of the temporary black stone */
	public static final Color BLACK_TEMP_STONE = new Color(64, 64, 64, 127);

	/** The color of the temporary white stone */
	public static final Color WHITE_TEMP_STONE = new Color(222, 222, 222, 127);

	/** Represents that the mouse is off the board */
	public static final Point OFF_BOARD = new Point(-1, -1);

	/** The back-end board */
	private GoBoard board;

	/** The back-end game */
	private GoGame game;

	/** The temporary stone that is displayed when hovering over a space */
	private Point tempStone;

	/** The previous location of the temporary stone */
	private Point prevTemp;
	
	/**
	 * Creates a standard 19x19 board
	 * 
	 * @param board the board back-end
	 * @param game the game back-end
	 */
	public GraphicBoard(GoBoard board, GoGame game) {
		this(19, 19, board, game);
	}

	/**
	 * Creates a board of the specified size (rows and columns)
	 * 
	 * @param x the number of rows
	 * @param y the number of columns
	 * @param board the board back-end
	 * @param game the game back-end
	 */
	public GraphicBoard(int x, int y, GoBoard board, GoGame game) {
		super();

		this.board = board;
		this.game = game;

		// The absolute size of the board in pixels
		Dimension size = new Dimension(500, 500);

		this.setSize(size);
		this.setPreferredSize(size);

		this.setBackground(BOARD_COLOR);

		tempStone = OFF_BOARD;
		prevTemp = OFF_BOARD;

		// Add the mouse listeners (for clicks and movement)
		MouseList ml = new MouseList();
		this.addMouseListener(ml);
		this.addMouseMotionListener(ml);
	}

	public int getColNum() {
		return board.getColNum();
	}

	public int getRowNum() {
		return board.getRowNum();
	}

	public int getStoneAt(int x, int y) {
		return board.getStoneAt(x, y);
	}

	public void place(int x, int y, int color) {
		board.place(x, y, color);
		repaint();
	}

	/**
	 * Paints the initial board with lines and star points
	 * 
	 * @param g the graphics object being used
	 */
	public void paint(Graphics g) {
		update(g, true);
	}

	/**
	 * Draws or re-draws the entire board as blank
	 * 
	 * @param g the graphics object being used
	 */
	private void drawBoard(Graphics g) {
		// Define these variables here because we're going to be
		// working with them a lot
		int width = this.getWidth();
		int height = this.getHeight();
		int rowInterval = this.getRowInterval();
		int colInterval = this.getColInterval();

		// Start by blanking the board
		g.setColor(BOARD_COLOR);
		g.fillRect(0, 0, width, height);

		// Draws the rows
		g.setColor(LINE_COLOR);
		for (int i = 1; i < getRowNum() + 1; i++) {
			g.fillRect(colInterval, rowInterval * i, width - (colInterval * 2)
					+ getLineWidth(), getLineWidth());
		}

		// Draws the columns
		for (int i = 1; i < getColNum() + 1; i++) {
			g.fillRect(colInterval * i, rowInterval, getLineWidth(), height
					- (rowInterval * 2));
		}

		// Draws the star points
		for (int i = 0; i < getRowNum(); i++) {
			for (int j = 0; j < getColNum(); j++) {
				if (getRowNum() > 12 && (i == 3 || i == 9 || i == 15)
						&& getColNum() > 12 && (j == 3 || j == 9 || j == 15)) {
					// Add in the stars
					g.setColor(Color.BLACK);
					g.fillOval(colInterval * (j + 1) - (getLineWidth() * 3) / 2
							+ getLineWidth() / 2, rowInterval * (i + 1)
							- (getLineWidth() * 3) / 2 + getLineWidth() / 2,
							getLineWidth() * 3, getLineWidth() * 3);
				} else if (getRowNum() <= 12 && (i == 2 || i == 4 || i == 6)
						&& getColNum() <= 12 && (j == 2 || j == 4 || j == 6)) {
					// Add in the stars
					g.setColor(Color.BLACK);
					g.fillOval(colInterval * (j + 1) - (getLineWidth() * 3) / 2
							+ getLineWidth() / 2, rowInterval * (i + 1)
							- (getLineWidth() * 3) / 2 + getLineWidth() / 2,
							getLineWidth() * 3, getLineWidth() * 3);
				}
			}
		}
	}

	/**
	 * Draws a stone on the board at the specified point
	 * 
	 * @param x x-coordinate of the point
	 * @param y y-coordinate of the point
	 * @param c color of the stone to draw
	 * @param g graphics object to draw with
	 */
	private void drawStone(int x, int y, Color c, Graphics g) {
		Color prevColor = g.getColor();
		int stoneX = x + 1;
		int stoneY = y + 1;
		g.setColor(c);
		g.fillOval(this.getColInterval() * stoneY - getStoneWidth() / 2
				+ getLineWidth() / 2, this.getRowInterval() * stoneX
				- getStoneWidth() / 2 + getLineWidth() / 2, getStoneWidth(),
				getStoneWidth());
		g.setColor(prevColor);
	}

	/**
	 * Method called on repaint()
	 * 
	 * @param g the graphics object being used
	 */
	public void update(Graphics g) {
		// Erases the previous temporary stone (if needed)
		if (!prevTemp.getLocation().equals(OFF_BOARD)) {
			updateIntersection(prevTemp);
		}

		// Draws the temporary stone (if needed)
		if (!getTemp().getLocation().equals(OFF_BOARD)) {
			// Assume it's Black's turn...
			Color tempColor = BLACK_TEMP_STONE;

			// Otherwise, set the stone color to White
			if (getCurrentPlayer() == GoBoard.WHITE) {
				tempColor = WHITE_TEMP_STONE;
			}

			// Draw the temporary stone
			drawStone((int) (getTemp().getX()), (int) (getTemp().getY()),
					tempColor, g);
		}

	}

	/**
	 * Forces a full update of the board if true
	 * 
	 * This can be used to redraw the board after a capture
	 * 
	 * @param graphics the board's graphics object
	 * @param force whether or not to force an update
	 */
	public void update(Graphics g, boolean force) {
		if (force) {
			// Wipe the board clear
			drawBoard(g);

			// Draw every stone that is actually on the board
			for (int i = 0; i < getRowNum(); i++) {
				for (int j = 0; j < getColNum(); j++) {
					updateIntersection(new Point(i, j));
				}
			}
		}

	}

	/**
	 * Update the specified intersection
	 * 
	 * @param p the point on the board to update
	 */
	protected void updateIntersection(Point p) {
		// If we haven't made the board yet, we can't update it
		if (this.getGraphics() == null) {
			return;
		}

		Graphics g = this.getGraphics();

		// The "adjusted" x and y values
		int adjX = p.x + 1;
		int adjY = p.y + 1;

		int colInterval = getColInterval();
		int rowInterval = getRowInterval();

		// If the intersection isn't empty, draw a stone there
		if (getStoneAt(p.x, p.y) != GoBoard.EMPTY) {
			if (getStoneAt(p.x, p.y) == GoBoard.WHITE) {
				drawStone(p.x, p.y, Color.WHITE, g);
			} else if (getStoneAt(p.x, p.y) == GoBoard.BLACK) {
				drawStone(p.x, p.y, Color.BLACK, g);
			}
		}

		// Otherwise, blank the space
		else {
			// Fill in the spot with the board color
			drawStone(p.x, p.y, BOARD_COLOR, g);

			// // REDRAW THE INTERSECTION // //
			
			g.setColor(LINE_COLOR);

			// If we are in the first column, we only want to draw right
			// If we are in the last column, we only want to draw left
			// Otherwise, draw both ways
			if (p.y == 0) {
				g.fillRect(colInterval * adjY, rowInterval * adjX,
						getStoneWidth() / 2 + getLineWidth(), getLineWidth());
			} else if (p.y == getColNum() - 1) {
				g.fillRect(colInterval * adjY - getStoneWidth() / 2,
						rowInterval * adjX, getStoneWidth() / 2
								+ getLineWidth(), getLineWidth());
			} else {
				g.fillRect(colInterval * adjY - getStoneWidth() / 2
						+ getLineWidth() / 2, rowInterval * adjX,
						getStoneWidth(), getLineWidth());
			}

			// If we are in the first row, we only want to draw down
			// If we are in the last row, we only want to draw up
			// Otherwise, draw both ways
			if (p.x == 0) {
				g.fillRect(colInterval * adjY, rowInterval * adjX,
						getLineWidth(), getStoneWidth() / 2 + getLineWidth());
			} else if (p.x == getRowNum() - 1) {
				g.fillRect(colInterval * adjY, rowInterval * adjX
						- getStoneWidth() / 2, getLineWidth(), getStoneWidth()
						/ 2 + getLineWidth());
			} else {
				g.fillRect(colInterval * adjY, rowInterval * adjX
						- getStoneWidth() / 2 + getLineWidth() / 2,
						getLineWidth(), getStoneWidth());
			}

			// Put in the star point if it was taken out
			if (getRowNum() > 12 && (p.x == 3 || p.x == 9 || p.x == 15)
					&& getColNum() > 12 && (p.y == 3 || p.y == 9 || p.y == 15)) {
				// Draw the star point
				g.setColor(Color.BLACK);
				g.fillOval(colInterval * adjY - (getLineWidth() * 3) / 2
						+ getLineWidth() / 2, rowInterval * adjX
						- (getLineWidth() * 3) / 2 + getLineWidth() / 2,
						getLineWidth() * 3, getLineWidth() * 3);
			} else if (getRowNum() <= 12 && (p.x == 2 || p.x == 4 || p.x == 6)
					&& getColNum() <= 12 && (p.y == 2 || p.y == 4 || p.y == 6)) {
				// Draw the star point
				g.setColor(Color.BLACK);
				g.fillOval(colInterval * adjY - (getLineWidth() * 3) / 2
						+ getLineWidth() / 2, rowInterval * adjX
						- (getLineWidth() * 3) / 2 + getLineWidth() / 2,
						getLineWidth() * 3, getLineWidth() * 3);
			}
		}
	}

	/**
	 * Gets the distance between rows
	 * 
	 * @return the distance between rows
	 */
	private int getRowInterval() {
		int height = this.getHeight();

		// The number of rows plus the outer edge
		return height / (getRowNum() + 1);
	}

	/**
	 * Gets the distance between columns
	 * 
	 * @return the distance between columns
	 */
	private int getColInterval() {
		int width = this.getWidth();

		// The number of columns plus the outer edge
		return width / (getColNum() + 1);
	}

	/**
	 * Gets the width of the lines on the board
	 * 
	 * @return the width of the lines on the board
	 */
	private int getLineWidth() {
		int width = this.getWidth();
		int height = this.getHeight();

		if (width < 650 || height < 650) {
			return 3;
		} else {
			return 5;
		}
	}

	/**
	 * Gets the width of the stones on the board
	 * 
	 * @return the width of the stones on the board
	 */
	private int getStoneWidth() {
		return getLineWidth() * 7;
	}

	/**
	 * Gets the color of the current player
	 * 
	 * @return the color of the current player
	 */
	private int getCurrentPlayer() {
		int stoneColor = game.getTurn() % 2 + 1;
		return stoneColor;
	}

	/**
	 * Gets the point of the temporary stone
	 * 
	 * @return the point of the temporary stone
	 */
	private Point getTemp() {
		return tempStone;
	}

	/**
	 * Sets the point of the temporary stone
	 * 
	 * @param newTemp the point of the new temporary stone
	 */
	private void setTemp(Point newTemp) {
		prevTemp = tempStone;
		tempStone = newTemp;
	}

	private class MouseList implements MouseListener, MouseMotionListener {
		public void mouseClicked(MouseEvent e) {
			Point newStone = getCurrentIntersection(e);
			// If the move is valid, play it
			if (!newStone.equals(OFF_BOARD)) {
				game.play((int) newStone.getY(), (int) newStone.getX());
				setTemp(OFF_BOARD);
				repaint();
			}
		}

		public void mouseMoved(MouseEvent e) {
			Point newTemp = getCurrentIntersection(e);
			newTemp = new Point((int) newTemp.getY(), (int) newTemp.getX());
			// Only repaint if the player has moved the mouse far enough to need
			// a new stone
			if (!newTemp.equals(getTemp())) {
				setTemp(newTemp);
				repaint();
			}
		}

		private Point getCurrentIntersection(MouseEvent e) {
			double xCoor = e.getPoint().getY();
			double yCoor = e.getPoint().getX();

			int x = (int) (xCoor / getRowInterval() - .5);
			int y = (int) (yCoor / getColInterval() - .5);

			if (x >= getColNum() || y >= getRowNum() || x < 0 || y < 0
					|| getStoneAt(x, y) != GoBoard.EMPTY) {
				return OFF_BOARD;
			}

			return new Point(y, x);
		}

		public void mouseExited(MouseEvent e) {
			Point newTemp = OFF_BOARD;
			if (!newTemp.equals(getTemp())) {
				setTemp(newTemp);
				repaint();
			}
		}

		public void mouseEntered(MouseEvent e) {
			// Do nothing
		}

		public void mousePressed(MouseEvent e) {
			// Do nothing
		}

		public void mouseReleased(MouseEvent e) {
			// Do nothing
		}

		public void mouseDragged(MouseEvent e) {
			// Do nothing
		}

	}
}
