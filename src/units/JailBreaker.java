package units;

import model.Globals;

public class JailBreaker extends Unit {

  public JailBreaker(String name, String side) {
    super(name, Globals.JAIL_FREER_CALORIES, side);
  }

  @Override
  public char getLetterRepresentation() {
    return 'J';
  }
}
