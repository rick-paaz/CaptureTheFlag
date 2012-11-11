package units;

import model.Globals;
import model.Unit;

public class JailBreaker extends Unit {

  public JailBreaker(String name) {
    super(name, Globals.JAIL_FREER_CALORIES);
  }
}
