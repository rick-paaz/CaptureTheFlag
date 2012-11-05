package model;

import java.util.Observable;


public class Game extends Observable {

  private Object[][] field;
  private boolean hasComputerPlayer;
  
  public Game() {
    field = new Object[Globals.ROWS][Globals.COLUMNS];
    hasComputerPlayer = false;
  }

  public void set(int row, int column, Object sprite) {
    if(sprite == null)
      throw new IllegalArgumentException();
    field[row][column] = sprite;
  }
  
   public void moveUnit(int fromRow, int fromColumn, int toRow, int toColumn) {
     field[toRow][toColumn] = field;
     field[fromRow][fromColumn] = null;
     setChanged();
     notifyObservers();
   }

  public Object getSprite(int r, int c) {
    return field[r][c];
  }
}