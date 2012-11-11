package items;

public class OpponentFlag extends Item {

  public OpponentFlag(String imageFileName) {
    super("OpponentFlag", imageFileName);
  }

  public char getLetterRepresentation() {
    return 'f';
  }
}
