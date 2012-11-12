package units;

import model.Globals;

public class Runner extends Unit {
  
  public Runner(String name, String side) {
    super(name, Globals.RUNNER_CALORIES, side);
  }

  @Override
  public char getLetterRepresentation() {
    // TODO Auto-generated method stub
    return 'R';
  }

  @Override
  public void chargeOneMoveCost() {
    int currentCalories = this.getCalories();
    setStrength(currentCalories - Globals.RUNNER__MOVE_COST);
  }
}
