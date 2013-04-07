package game;

import gamepieces.GamePiece;
import gamepieces.Unit;

import java.util.Observable;

// Added a comment 

public class Game extends Observable {

  // Add an instance variable
  int garbage;

  private void foo() {
    String meaningless = "Remove me";
  }
  
  private GamePiece[][] field;

  public Game() {
    field = new GamePiece[Globals.ROWS][Globals.COLUMNS];
  }

  // Also update the gamePiece location
  public void moveUnit(int fromRow, int fromColumn, int toRow, int toColumn) {
    if (outOfRange(fromRow, fromColumn) || outOfRange(toRow, toColumn))
      throw new IllegalArgumentException();

    field[toRow][toColumn] = field[fromRow][fromColumn];
    field[fromRow][fromColumn] = null;

    field[toRow][toColumn].setPosition(toRow, toColumn);

    ((Unit) field[toRow][toColumn]).chargeOneMoveCost();

    setChanged();
    notifyObservers();
  }

  public Unit getUnit(int row, int column) {
    return (Unit) field[row][column];
  }

  // Also update the gamePiece location if the gemPiece is a unit

  public void addPiece(GamePiece gamePiece, int row, int column) {
    if (outOfRange(row, column) || field[row][column] != null)
      throw new IllegalArgumentException();

    if (gamePiece instanceof Unit)
      gamePiece.setPosition(row, column);

    field[row][column] = gamePiece;
  }

  private boolean outOfRange(int row, int column) {
    return row < 0 || column < 0 || column >= Globals.COLUMNS
        || row >= Globals.ROWS;
  }

  public String toString() {
    String result = "";
    for (int r = 0; r < Globals.ROWS; r++) {
      for (int c = 0; c < Globals.COLUMNS; c++) {

        String sideLetter = " ";
        Object o = field[r][c];
        if (o instanceof Unit) {
          if (((Unit) field[r][c]).getSide().equals(Globals.SIDE_LEFT))
            sideLetter = "l";
          else
            sideLetter = "r";
        }
        if (field[r][c] == null)
          result += " . ";
        else
          result += sideLetter + field[r][c].getLetterRepresentation() + " ";
      }
      result += "\n";
    }
    return result;
  }
}