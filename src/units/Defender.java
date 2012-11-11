package units;

import model.Globals;
import model.Unit;

public class Defender extends Unit {

  public Defender(String name) {
    super(name, Globals.DEFENDER_CALORIES);
  }
}
