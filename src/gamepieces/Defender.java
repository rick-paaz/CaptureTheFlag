package gamepieces;

import java.io.Serializable;

import model.Globals;

public class Defender extends Unit implements Serializable{

  public Defender(String name, String side) {
    super(name, Globals.DEFENDER_CALORIES, side);
  }

  @Override
  public char getLetterRepresentation() {
    // TODO Auto-generated method stub
    return 'D';
  }

  @Override
  public void chargeOneMoveCost() {
    int currentCalories = this.getCalories();
    setStrength(currentCalories - Globals.DEFENDER__MOVE_COST);
  }
}