package model;

public class Unit {

  private String name;
  private int calories;
  private boolean tagged;
  private boolean inJail;
  private int bandanas;
  private Profile profile;
  private int row, column;

  /**
   * Construct a new Unit with all initial values.  Past things not remembered
   * @param name 
   * @param strength The amount determined after the player has bought the strength
   */
  public Unit(String name, int strength) {
    setStrength(strength);
    setTagged(false);
    this.name = name;
    bandanas = 1;
    profile = new Profile();
    setInJail(false);
    column = -1;
    row = -1;
  }

  public void setPosition(int row, int column) throws IllegalArgumentException {
    if(row < 0 || column < 0 || row >= Globals.ROWS ||  column >= Globals.COLUMNS)
      throw new IllegalArgumentException();
    this.row = row;
    this.column = column;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
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
    if (getName().equalsIgnoreCase(other.getName()))
      throw new NotOnSameSideException();

    // One challenge mean two Units are involved 
    this.profile.addToChallengesInvolvedIn();
    other.getProfile().addToChallengesInvolvedIn();

    if (getCalories() < other.getCalories()) {
      this.setStrength(this.getCalories() - Globals.CALORIE_LOSS);
      if (other.canTakeBandanaFrom(this)) {
        other.getProfile().addToChallengesWon();
        this.removeBandana();
      }
    }

    if (other.getCalories() < getCalories()) {
      other.setStrength(other.getCalories() - Globals.CALORIE_LOSS);
      if (this.canTakeBandanaFrom(other)) {
        other.removeBandana();
        this.profile.addToChallengesWon();
      }
    }
  }

  /**
   * Access to the name of this unit that is used to ensure 
   * different sides are challenging each other.
   * 
   * @return This units name
   */
  public String getName() {
    return name;
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

  public Profile getProfile() {
    return profile;
  }
}