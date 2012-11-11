package items;

public class BearTrap extends Item {

  public BearTrap(String imageFileName) {
    super("BearTrap", imageFileName);
  }

  public char getLetterRepresentation() {
    return 'T';
  }
}
