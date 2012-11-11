package tests;

// actual begin: 8:30  Fri                                                                                         
// actual end: 11:00

//actual begin: 1:00 pm Sat                                                                                           
//actual end: 4:00

// begin 7:45 Sunday
// end

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import model.Globals;
import model.NotOnSameSideException;
import model.Profile;
import model.Reward;
import model.Unit;

import org.junit.Test;

import units.Defender;
import units.JailBreaker;
import units.Runner;

public class UnitTest {

  @Test
  public void testGetters() {
    Unit uA = new Defender("Bob");
    assertEquals(Globals.DEFENDER_CALORIES, uA.getCalories());
    assertEquals(1, uA.getBandanaCount());
    assertEquals("Bob", uA.getName());
    assertFalse(uA.isTagged());
    assertFalse(uA.isInJail());
    assertEquals(0, uA.challengesInvolvedIn());
    assertEquals(0, uA.challengesWon());
    assertEquals(0, uA.numberOfRewards());
    
    Unit uB = new Runner("Rick");
    assertEquals(Globals.RUNNER_CALORIES, uB.getCalories());
    assertEquals(1, uB.getBandanaCount());
    assertEquals("Rick", uB.getName());
    assertEquals(1, uB.getBandanaCount());
    assertFalse(uB.isTagged());
    assertFalse(uB.isInJail());
    assertEquals(0, uB.challengesInvolvedIn());
    assertEquals(0, uB.challengesWon());
    assertEquals(0, uB.numberOfRewards());   
  
    Unit uC = new JailBreaker("Hanger on");
    assertEquals(Globals.JAIL_FREER_CALORIES, uC.getCalories());
    assertEquals(1, uC.getBandanaCount());
    assertEquals("Hanger on", uC.getName());
    assertEquals(1, uC.getBandanaCount());
    assertFalse(uC.isTagged());
    assertFalse(uC.isInJail());
    assertEquals(0, uC.challengesInvolvedIn());
    assertEquals(0, uC.challengesWon());
    assertEquals(0, uC.numberOfRewards());   

    
  }

  @Test
  public void testFightWhenStrengthIsEqual() {
    Unit uA = new Unit("Bob", 100);
    Unit uB = new Unit("Rick", 100);
    uA.challenge(uB);
    assertEquals(100, uA.getCalories());
    assertEquals(100, uB.getCalories());
    uB.challenge(uA);
    assertEquals(100, uA.getCalories());
    assertEquals(100, uB.getCalories());
  }

  @Test
  public void testPosition() {
    Unit uA = new Unit("Bob", 100);
    uA.setPosition(1, 2);
    assertEquals(1, uA.getRow());
    assertEquals(2, uA.getColumn());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPositionThrowsException() {
    Unit uA = new Unit("Bob", 100);
    uA.setPosition(-1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPositionThrowsExceptionColumn() {
    Unit uA = new Unit("Bob", 100);
    uA.setPosition(1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPositionThrowsExceptionRowsTooBig() {
    Unit uA = new Unit("Bob", 100);
    uA.setPosition(Globals.ROWS, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPositionThrowsExceptionColumnsTooBig() {
    Unit uA = new Unit("Bob", 100);
    uA.setPosition(1, Globals.COLUMNS);
  }

  @Test
  public void testFightWhenThisCaloriesIsBetter() {
    Unit uA = new Unit("Bob", 100);
    Unit uB = new Unit("Rick", 99);
    // A > B
    uA.challenge(uB);
    assertEquals(100, uA.getCalories());
    assertEquals(99 - Globals.CALORIE_LOSS_PER_CHALLENGE, uB.getCalories());
    uB.challenge(uA);
    assertEquals(100, uA.getCalories());
    assertEquals(99 - 2 * Globals.CALORIE_LOSS_PER_CHALLENGE, uB.getCalories());
  }

  @Test
  public void testFightWhenOthersCaloriesIsBetter() {
    Unit uA = new Unit("Rick", 80);
    Unit uB = new Unit("Bob", 90);
    uA.challenge(uB);
    // B > A
    assertEquals(80 - Globals.CALORIE_LOSS_PER_CHALLENGE, uA.getCalories());
    assertEquals(90, uB.getCalories());
    uB.challenge(uA);
    assertEquals(80 - 2 * Globals.CALORIE_LOSS_PER_CHALLENGE, uA.getCalories());
    assertEquals(90, uB.getCalories());
  }

  @Test(expected = NotOnSameSideException.class)
  public void testDifferentSides() {
    Unit uA = new Unit("Bob", 100);
    Unit uB = new Unit("Bob", 99);
    uA.challenge(uB);
  }

  @Test(expected = NotOnSameSideException.class)
  public void testDifferentSidesWithADifferentReceiver() {
    Unit uA = new Unit("Bob", 100);
    Unit uB = new Unit("Bob", 99);
    uB.challenge(uA);
  }

  /**
   * Try to have one unit take a bandana from another where 
   * both begin with 2 flags, but uA has more calories and
   * should eventually get a bandana.
   * 
   * Current plan is to have one take another bandana when
   * one has twice as many calories (or the other has only half)
   */
  @Test
  public void testOneUnitGetsAnothersFlag() {
    int intialCaloriesOfA = 5 * Globals.CALORIE_LOSS_PER_CHALLENGE;
    Unit uA = new Unit("WinnerToBe", intialCaloriesOfA);
    int intialCaloriesOfB = 3 * Globals.CALORIE_LOSS_PER_CHALLENGE;
    Unit uB = new Unit("WillEventuallyLose", intialCaloriesOfB);

    // Sanity check
    assertEquals(1, uA.getBandanaCount());
    assertEquals(1, uB.getBandanaCount());

    // Let uA battle uB until uA removes 1 flag from uB.
    // Current thought is to have uA must with twice the
    // calories for a flag take.  
    uA.challenge(uB);
    assertEquals(intialCaloriesOfA, uA.getCalories());
    assertEquals(intialCaloriesOfB - Globals.CALORIE_LOSS_PER_CHALLENGE, uB.getCalories());
    // 2nd challenge with B's move to challenge A
    uB.challenge(uA);
    assertEquals(intialCaloriesOfA, uA.getCalories());
    assertEquals(intialCaloriesOfB - 2 * Globals.CALORIE_LOSS_PER_CHALLENGE, uB.getCalories());
  }

  @Test
  public void testChallengeTakesBandana() {
    int intialCaloriesOfA = 5 * Globals.CALORIE_LOSS_PER_CHALLENGE;
    Unit uA = new Unit("WinnerToBe", intialCaloriesOfA);
    int intialCaloriesOfB = 3 * Globals.CALORIE_LOSS_PER_CHALLENGE;
    Unit uB = new Unit("WillEventuallyLose", intialCaloriesOfB);

    assertEquals(1, uA.getBandanaCount());
    assertEquals(1, uB.getBandanaCount());
    assertFalse(uA.isTagged());
    assertFalse(uA.isTagged());
    uA.challenge(uB);
    assertEquals(1, uA.getBandanaCount());
    assertEquals(0, uB.getBandanaCount());
    assertFalse(uA.isTagged());
    assertTrue(uB.isTagged());

    assertEquals(0, uB.challengesWon());
    assertEquals(1, uA.challengesWon());
  }

  @Test
  public void testProfileUpdate() {
    int intialCaloriesOfA = 5 * Globals.CALORIE_LOSS_PER_CHALLENGE;
    Unit uA = new Unit("WinnerToBe", intialCaloriesOfA);
    int intialCaloriesOfB = 2 * Globals.CALORIE_LOSS_PER_CHALLENGE;
    Unit uB = new Unit("WillEventuallyLose", intialCaloriesOfB);

    // Sanity check
    assertEquals(1, uA.getBandanaCount());
    assertEquals(1, uB.getBandanaCount());
    uA.challenge(uB);
    assertTrue(uB.isTagged());
  }

  @Test
  public void testIfTagged() {
    int intialCaloriesOfA = 5 * Globals.CALORIE_LOSS_PER_CHALLENGE;
    Unit uA = new Unit("WinnerToBe", intialCaloriesOfA);
    int intialCaloriesOfB = 4 * Globals.CALORIE_LOSS_PER_CHALLENGE;
    Unit uB = new Unit("WillEventuallyLose", intialCaloriesOfB);

    // Needs to challenges to get to 50 >= 2 *(40-10-10)
    uA.challenge(uB);
    uA.challenge(uB);
    assertEquals(1, uA.getBandanaCount());
    assertEquals(0, uB.getBandanaCount());
    assertTrue(uB.isTagged());
  }

  @Test
  public void testIsTagged() {
    Unit uA = new Unit("A", 6 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    Unit second = new Unit("B", 4 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    Unit third = new Unit("C", 4 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    Unit fourth = new Unit("D", 12 * Globals.CALORIE_LOSS_PER_CHALLENGE);

    assertFalse(uA.isTagged());
    assertFalse(second.isTagged());
    assertFalse(third.isTagged());

    uA.challenge(second);
    assertFalse(uA.isTagged());
    assertTrue(second.isTagged());
    assertFalse(third.isTagged());

    uA.challenge(third);
    assertFalse(uA.isTagged());
    assertTrue(second.isTagged());
    assertTrue(third.isTagged());

    uA.challenge(fourth);
    assertFalse(fourth.isTagged());
    assertTrue(uA.isTagged());

  }

  @Test
  public void testIsTaggedWhenChallengerIsReversedTheReceiverThatIs() {
    Unit uA = new Unit("A", 6 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    Unit second = new Unit("B", 4 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    Unit third = new Unit("C", 4 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    Unit fourth = new Unit("D", 12 * Globals.CALORIE_LOSS_PER_CHALLENGE);

    assertFalse(uA.isTagged());
    assertFalse(second.isTagged());
    assertFalse(third.isTagged());

    second.challenge(uA);
    assertFalse(uA.isTagged());
    assertTrue(second.isTagged());
    assertFalse(third.isTagged());

    third.challenge(uA);
    assertFalse(uA.isTagged());
    assertTrue(second.isTagged());
    assertTrue(third.isTagged());

    fourth.challenge(uA);
    assertFalse(fourth.isTagged());
    assertTrue(uA.isTagged());

  }

  @Test
  public void testWinsRecordedInTheProfile() {
    // A will always take a bandana 
    Unit uA = new Unit("A", 5 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    Unit other = new Unit("B", 3 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    uA.challenge(other); // should take bandana with only one challenge
    assertEquals(1, uA.challengesInvolvedIn());
    assertEquals(1, uA.challengesWon());
    assertEquals(0, uA.getRewards().size());
    uA.challenge(other); // should take bandana with only one challenge
    assertEquals(2, uA.challengesInvolvedIn());
    assertEquals(2, uA.challengesWon());
    assertEquals(0, uA.getRewards().size());

    Unit third = new Unit("C", 2 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    uA.challenge(third); // should take bandana with only one challenge
    assertEquals(3, uA.challengesInvolvedIn());
    assertEquals(3, uA.challengesWon());
    assertEquals(0, uA.getRewards().size());

    Unit fourth = new Unit("D", 6 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    uA.challenge(fourth); // uA should NOT win this challenge
    assertEquals(4, uA.challengesInvolvedIn());
    assertEquals(3, uA.challengesWon());
    assertEquals(0, uA.getRewards().size());

    assertEquals(1, fourth.challengesInvolvedIn());
    assertEquals(0, fourth.challengesWon());
    assertEquals(0, fourth.getRewards().size());

    assertFalse(uA.isTagged());
    uA.challenge(fourth); // uA should become tagged with the 3rd challenge
    assertTrue(uA.isTagged());
    assertEquals(5, uA.challengesInvolvedIn());
    assertEquals(3, uA.challengesWon());
    assertEquals(0, uA.getRewards().size());

    assertEquals(2, fourth.challengesInvolvedIn());
    assertEquals(1, fourth.challengesWon());
    assertEquals(0, fourth.getRewards().size());
  }

  @Test
  public void testProfileIsUpdated() {
    // A will always take a bandana at each challenge
    Unit uA = new Unit("A", 5 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    Unit other = null;
    for (int wins = 1; wins <= Globals.WINS_FOR_REWARD - 1; wins++) {
      // Each new B will always be tagged with only one challenge
      // because that will make B have half to calories of A
      other = new Unit("B", 3 * Globals.CALORIE_LOSS_PER_CHALLENGE);
      assertFalse(other.isTagged());
      uA.challenge(other); // should win with only one challenge
      assertTrue(other.isTagged());
    }
    // uA should have non zero stats
    assertEquals(Globals.WINS_FOR_REWARD - 1, uA.challengesInvolvedIn());
    assertEquals(Globals.WINS_FOR_REWARD - 1, uA.challengesWon());
    assertEquals(0, uA.getRewards().size());
    uA.challenge(other); // should win with only one challenge
    assertEquals(Globals.WINS_FOR_REWARD, uA.challengesInvolvedIn());
    assertEquals(Globals.WINS_FOR_REWARD, uA.challengesWon());
    assertEquals(1, uA.getRewards().size());
  }

  @Test
  public void testProfileGetsAllThreeAwards() {

    Unit uA = new Unit("A", 2000 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    for (int wins = 1; wins <= Globals.WINS_FOR_REWARD; wins++) {
      Unit other = new Unit("B", 1000 * Globals.CALORIE_LOSS_PER_CHALLENGE);
      uA.challenge(other); // should win every time
    }
    // uA should have non zero stats
    assertEquals(1, uA.getRewards().size());
  }

  @Test
  public void testGetAward() {

    Unit uA = new Unit("A", 2000 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    Unit other = null;
    // Almost get a reward for 
    for (int wins = 1; wins <= Globals.WINS_FOR_REWARD - 1; wins++) {
      other = new Unit("B", 1000 * Globals.CALORIE_LOSS_PER_CHALLENGE);
      uA.challenge(other); // should win every time
    }
    // uA should have non zero stats
    assertEquals(0, uA.getRewards().size());
    uA.challenge(other);
    assertEquals(1, uA.getRewards().size());
  }

  @Test
  public void testUnitTypesHaveCorrectCalories() {
    Unit off = new Runner("Get the flag");
    Unit def = new Defender("Protect the flag");
    Unit jail = new JailBreaker("FreeTheTaggedPlayers");
    assertEquals(Globals.RUNNER_CALORIES, off.getCalories());
    assertEquals(Globals.DEFENDER_CALORIES, def.getCalories());
    assertEquals(Globals.JAIL_FREER_CALORIES, jail.getCalories());
  }
  
 
  @Test
  public void testMutators() {
    Unit p = new Unit("Bob", 1000);
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
    Unit p = new Unit("Bob", 1000);
        for (int wins = 1; wins <= 8; wins++) {
      p.addToChallengesInvolvedIn();
    }
    assertEquals(8, p.challengesInvolvedIn());
    p.addToChallengesWon();
    assertEquals(8, p.challengesInvolvedIn());
  }

  @Test
  public void testChallengesWonCount() {
    Unit p = new Unit("Bob", 1000);
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
    Unit p = new Unit("Bob", 1000);
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