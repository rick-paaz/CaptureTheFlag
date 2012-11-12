package model;

public abstract class GamePiece {

  private String name;
  protected int row, column;

  public GamePiece(String name) {
    this.name = name;
  }

  public abstract char getLetterRepresentation();

  /**
   * Access to the name of this unit that is used to ensure different sides are
   * challenging each other.
   * 
   * @return This units name
   */
  public String getName() {
    return name;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public void setPosition(int row, int column) throws IllegalArgumentException {
    if (row < 0 || column < 0 || row >= Globals.ROWS
        || column >= Globals.COLUMNS)
      throw new IllegalArgumentException();
    this.row = row;
    this.column = column;
  }

}
