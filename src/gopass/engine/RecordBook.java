package gopass.engine;

import java.util.ArrayList;

/**
 * A class for recording the plays in a game
 * 
 * @author Jason Mey
 * @version 1.0
 */
public class RecordBook {

   /** The list of all the records */
   private ArrayList<Record> records;

   /**
    * Creates a new record-keeping book
    */
   public RecordBook() {
      records = new ArrayList<Record>(20);
   }

   /**
    * Adds a record of a new move the the record book
    * 
    * @param x the x-coordinate of the move
    * @param y the y-coordinate of the move
    * @param color the color of the stone played
    */
   public void addRecord(int x, int y, int color) {
      records.add(new Record(x, y, color));
   }

   /**
    * Gets the record of the specified turn
    * 
    * @param turn the turn
    * @return the record of the specified turn
    */
   public Record getRecord(int turn) {
      return records.get(turn);
   }

   /**
    * Prints out the records in human-readable form
    */
   public void printRecords() {
      for (int i = 0; i < records.size(); i += 2) {
         if (i + 1 < records.size()) {
            System.out.println(getRecord(i) + ", " + getRecord(i + 1));
         } else {
            System.out.println(getRecord(i) + ", ");
         }
      }
   }
}