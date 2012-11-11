package model;

import java.util.Observable;

public class Game extends Observable {

  private GamePiece[][] field;

  public Game() {
    field = new GamePiece[Globals.ROWS][Globals.COLUMNS];
  }

  public void set(int row, int column, GamePiece gamePiece) {
    if (gamePiece == null)
      throw new IllegalArgumentException();
    field[row][column] = gamePiece;
  }

  public void moveUnit(int fromRow, int fromColumn, int toRow, int toColumn) {
    if (outOfRange(fromRow, fromColumn) || outOfRange(toRow, toColumn))
      throw new IllegalArgumentException();

    field[toRow][toColumn] = field[fromRow][fromColumn];
    field[fromRow][fromColumn] = null;
    setChanged();
    notifyObservers();
  }

  public Object getSprite(int row, int column) {
    return field[row][column];
  }

  public void addPiece(GamePiece gamePiece, int row, int column) {
    if (outOfRange(row, column) || field[row][column] != null)
      throw new IllegalArgumentException();

    field[row][column] = gamePiece;
  }

  private boolean outOfRange(int row, int column) {
    return row < 0 || column < 0 || column >= Globals.COLUMNS
        || row >= -Globals.ROWS;
  }
}