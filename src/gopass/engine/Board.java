package gopass.engine;

import gopass.GoBoard;

/**
 * The logic of a Go board
 * 
 * @author Jason Mey
 * @version 1.0
 */
public class Board implements GoBoard {

   /** Denotes an empty space on the board */
   public static final int EMPTY = 0;

   /** Denotes a space with a black piece */
   public static final int BLACK = 1;

   /** Denotes a space with a white piece */
   public static final int WHITE = 2;

   /** The board */
   private int[][] board;

   /**
    * Creates a standard 19x19 board
    */
   public Board() {
      this(19, 19);
   }

   /**
    * Creates a board with x rows and y columns
    * 
    * @param x the number of rows
    * @param y the number of columns
    */
   public Board(int x, int y) {
      if (x <= 1) {
         x = 2;
      }
      if (y <= 1) {
         y = 2;
      }

      board = new int[x][y];
   }

   /**
    * Places a piece on the board
    * 
    * @param x the x-coordinate of the piece
    * @param y the y-coordinate of the piece
    * @param color the color of the piece
    */
   public void place(int x, int y, int color) {
      board[x][y] = color;
   }

   /**
    * Gets the number of rows
    * 
    * @return the number of rows
    */
   public int getRowNum() {
      return board.length;
   }

   /**
    * Gets the number of columns
    * 
    * @return the number of columns
    */
   public int getColNum() {
      return board[0].length;
   }

   /**
    * Gets the color of the stone at the given coordinates
    * 
    * @param x the x-coordinate of the stone
    * @param y the y-coordinate of the stone
    * @return the color of the stone
    */
   public int getStoneAt(int x, int y) {
      return board[x][y];
   }

   /**
    * Prints the board in its current state
    */
   public void printBoard() {
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[0].length; j++) {
            System.out.print(board[i][j] + " ");
         }
         System.out.println();
      }
      System.out.println();
   }
}
