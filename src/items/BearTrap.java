package items;

public class BearTrap extends Item {

  public BearTrap(String imageFileName) {
    // Just added this comment after a Fetch and NOT a Synchronize
    super("BearTrap", imageFileName);
  }

  public char getLetterRepresentation() {
    return 'T';
  }
}
