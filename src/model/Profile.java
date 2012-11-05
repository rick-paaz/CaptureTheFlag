package model;

import java.util.ArrayList;
import java.util.List;


public class Profile {

  private int challenges;
  private List<Reward> rewards;
  private int challengesWon;

  public Profile() {
    challenges = 0;
    challengesWon = 0;
    rewards = new ArrayList<Reward>();
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

}