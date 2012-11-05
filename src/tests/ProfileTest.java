package tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import model.Globals;
import model.Profile;
import model.Reward;

import org.junit.Test;

public class ProfileTest {

  @Test
  public void testGetters() {
    Profile p = new Profile();
    assertEquals(0, p.challengesInvolvedIn());
    assertEquals(0, p.challengesWon());
    assertEquals(0, p.numberOfRewards());
  }

  @Test
  public void testMutators() {
    Profile p = new Profile();
    p.addToChallengesInvolvedIn();
    p.addToChallengesInvolvedIn();
    p.addToChallengesInvolvedIn();
    assertEquals(3, p.challengesInvolvedIn());
    p.addToChallengesWon();
    p.addToChallengesWon();
    assertEquals(2, p.challengesWon());
    assertEquals(0, p.numberOfRewards());
  }

  @Test
  public void testChallengeCount() {
    Profile p = new Profile();
    for (int wins = 1; wins <= 8; wins++) {
      p.addToChallengesInvolvedIn();
    }
    assertEquals(8, p.challengesInvolvedIn());
    p.addToChallengesWon();
    assertEquals(8, p.challengesInvolvedIn());
  }

  @Test
  public void testChallengesWonCount() {
    Profile p = new Profile();
    for (int wins = 1; wins <= Globals.WINS_FOR_REWARD - 1; wins++) {
      p.addToChallengesWon();
    }
    assertEquals(Globals.WINS_FOR_REWARD - 1, p.challengesWon());
    p.addToChallengesWon();
    assertEquals(Globals.WINS_FOR_REWARD, p.challengesWon());
  }
  

  /**
   * The Rewards will probably never be fully implemented
   * The cheap version is the Unit's profile adds an Award
   * when the Unit has won (taken a bandana) 10 times
   */
  @Test
  public void testAwardGiven() {
    Profile p = new Profile();
    for (int wins = 1; wins <= Globals.WINS_FOR_REWARD - 1; wins++) {
      p.addToChallengesWon();
    }
    assertEquals(Globals.WINS_FOR_REWARD - 1, p.challengesWon());
    List<Reward> rewards = p.getRewards();
    assertEquals(0, rewards.size());
    p.addToChallengesWon();
    assertEquals(Globals.WINS_FOR_REWARD, p.challengesWon());
    assertEquals(1, rewards.size());
    assertEquals(Reward.JUMPS_WALLS, rewards.get(0));
  }
}
