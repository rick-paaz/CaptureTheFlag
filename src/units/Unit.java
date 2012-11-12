package units;

import java.util.ArrayList;
import java.util.List;

import model.GamePiece;
import model.Globals;
import model.NotOnSameSideException;

public abstract class Unit extends GamePiece {

  private int calories;
  private boolean tagged;
  private boolean inJail;
  private int bandanas;
  // private Profile profile;
  private int challenges;
  private List<Reward> rewards;
  private int challengesWon;
  private String side;

  /**
   * Construct a new Unit with all initial values.  Past things not remembered
   * @param name 
   * @param Side TODO
   * @param strength The amount determined after the player has bought the strength
   */
  public Unit(String name, int calories, String side) {
    super(name);
    this.side = side;
    setStrength(calories);
    setTagged(false);
    bandanas = 1;
    //  profile = new Profile();
    setInJail(false);
    column = -1;
    row = -1;
    challenges = 0;
    challengesWon = 0;
    rewards = new ArrayList<Reward>();
  }
  

  /**
   * How much does this unit have remaining
   * @return The current strength. 
   */
  public int getCalories() {
    return calories;
  }

  /**
   * This can change during a battle
   * @param strength Perhaps the current strength - whatever can get lost.
   */
  public void setStrength(int strength) {
    this.calories = strength;
  }

  /**
   * Allow two units to battle each other if they are in the same side.
   * @param other The unit being fought
   * @throws NotOnSameSideException
   */
  public void challenge(Unit other) throws NotOnSameSideException {
    if (this.side.equalsIgnoreCase(other.side))
      throw new NotOnSameSideException();
    
 

    // One challenge mean two Units are involved 
    addToChallengesInvolvedIn();
    other.addToChallengesInvolvedIn();

    if (getCalories() < other.getCalories()) {
      this.setStrength(this.getCalories() - Globals.CALORIE_LOSS_PER_CHALLENGE);
      if (other.canTakeBandanaFrom(this)) {
        other.addToChallengesWon();
        this.removeBandana();
      }
    }

    if (other.getCalories() < getCalories()) {
      other.setStrength(other.getCalories()
          - Globals.CALORIE_LOSS_PER_CHALLENGE);
      if (this.canTakeBandanaFrom(other)) {
        other.removeBandana();
        addToChallengesWon();
      }
    }
  }

  public int getBandanaCount() {
    return bandanas;
  }

  public boolean isInJail() {
    return inJail;
  }

  public void setInJail(boolean inJail) {
    this.inJail = inJail;
  }

  public boolean isTagged() {
    return tagged;
  }

  public void setTagged(boolean tagged) {
    this.tagged = tagged;
  }

  /**
   * Return true if this Unit has at least twice as many calories as other
   * @param uB
   * @return
   */
  private boolean canTakeBandanaFrom(Unit other) {
    return getCalories() >= (2 * other.getCalories());
  }

  private void removeBandana() {
    bandanas--;
    if (bandanas == 0)
      tagged = true;
  }

  public int numberOfRewards() {
    return rewards.size();
  }

  public void addToChallengesInvolvedIn() {
    challenges++;
  }

  public int challengesInvolvedIn() {
    return challenges;
  }

  public void addToChallengesWon() {
    challengesWon++;
    // TODO:  checkForReward needs management.  When do we add a 2nd or 3rd
    // At some point, a Reward will be taken by this Unit, what then ?????
    if (challengesWon() >= Globals.WINS_FOR_REWARD)
      rewards.add(Reward.JUMPS_WALLS);
  }

  public int challengesWon() {
    return challengesWon;
  }

  public List<Reward> getRewards() {
    return this.rewards;
  }

  public String getSide() {
    return this.side;
  }
  
  public abstract void chargeOneMoveCost();
}