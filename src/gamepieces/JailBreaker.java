package gamepieces;

import game.Globals;

import java.io.Serializable;


public class JailBreaker extends Unit implements Serializable {

  public JailBreaker(String name, String side) {
    super(name, Globals.JAIL_FREER_CALORIES, side);
  }

  @Override
  public char getLetterRepresentation() {
    return 'J';
  }
  
  @Override
  public void chargeOneMoveCost() {
    int currentCalories = this.getCalories();
    setStrength(currentCalories - Globals.JAIL_FREER_MOVE_COST);
  }
}
