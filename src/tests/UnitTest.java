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

import model.CannotChallengeException;
import model.Game;
import model.Globals;

import org.junit.Test;

import units.Defender;
import units.JailBreaker;
import units.Reward;
import units.Offensive;
import units.Unit;

public class UnitTest {

  @Test
  public void testGetters() {
    Unit uA = new Defender("Bob", Globals.SIDE_LEFT);
    assertEquals(Globals.DEFENDER_CALORIES, uA.getCalories());
    assertEquals(1, uA.getBandanaCount());
    assertEquals("Bob", uA.getName());
    assertEquals('D', uA.getLetterRepresentation());

    assertFalse(uA.isTagged());
    assertFalse(uA.isInJail());
    assertEquals(0, uA.challengesInvolvedIn());
    assertEquals(0, uA.challengesWon());
    assertEquals(0, uA.numberOfRewards());

    Unit uB = new Offensive("Rick", Globals.SIDE_LEFT);
    assertEquals(Globals.RUNNER_CALORIES, uB.getCalories());
    assertEquals(1, uB.getBandanaCount());
    assertEquals("Rick", uB.getName());
    assertEquals('R', uB.getLetterRepresentation());
  assertEquals(1, uB.getBandanaCount());
    assertFalse(uB.isTagged());
    assertFalse(uB.isInJail());
    assertEquals(0, uB.challengesInvolvedIn());
    assertEquals(0, uB.challengesWon());
    assertEquals(0, uB.numberOfRewards());

    Unit uC = new JailBreaker("Hanger on", Globals.SIDE_LEFT);
    assertEquals(Globals.JAIL_FREER_CALORIES, uC.getCalories());
    assertEquals(1, uC.getBandanaCount());
    assertEquals("Hanger on", uC.getName());
    assertEquals('J', uC.getLetterRepresentation());
    assertEquals(1, uC.getBandanaCount());
    assertFalse(uC.isTagged());
    assertFalse(uC.isInJail());
    assertEquals(0, uC.challengesInvolvedIn());
    assertEquals(0, uC.challengesWon());
    assertEquals(0, uC.numberOfRewards());
  }

  @Test
  public void testPosition() {
    Unit uA = new Offensive("Bob", Globals.SIDE_LEFT);
    uA.setPosition(1, 2);
    assertEquals(1, uA.getRow());
    assertEquals(2, uA.getColumn());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPositionThrowsException() {
    Unit uA = new Offensive("Bob", Globals.SIDE_LEFT);
    uA.setPosition(-1, 2);
  }

  @Test(expected = CannotChallengeException.class)
  public void testChallenge() {
    Unit uA = new Offensive("Bob", Globals.SIDE_LEFT);
    Unit uB = new Offensive("Bob", Globals.SIDE_RIGHT);
    Game g = new Game();
    g.addPiece(uA, 2, 2);
    g.addPiece(uB, 4, 2);
    
    uA.challenge(uB);
  }
  
  @Test(expected = CannotChallengeException.class)
  public void testChallenge2() {
    Unit uA = new Offensive("Bob", Globals.SIDE_LEFT);
    Unit uB = new Offensive("Bob", Globals.SIDE_RIGHT);
    Game g = new Game();
    g.addPiece(uA, 3, 4);
    g.addPiece(uB, 4, 2);
    
    uA.challenge(uB);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testPositionThrowsExceptionColumn() {
    Unit uA = new Offensive("Bob", Globals.SIDE_LEFT);
    uA.setPosition(1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPositionThrowsExceptionRowsTooBig() {
    Unit uA = new Offensive("Bob", Globals.SIDE_LEFT);
    uA.setPosition(Globals.ROWS, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPositionThrowsExceptionColumnsTooBig() {
    Unit uA = new Offensive("Bob", Globals.SIDE_LEFT);
    uA.setPosition(1, Globals.COLUMNS);
  }

  @Test
  public void testFightWhenThisCaloriesIsBetter() {
    Unit uA = new Offensive("Bob", Globals.SIDE_LEFT);
    Unit uB = new Offensive("Rick", Globals.SIDE_RIGHT);
    // A > B
    uA.setStrength(500);
    uB.setStrength(499);
    uA.challenge(uB);

    assertEquals(500, uA.getCalories());
    assertEquals(499 - Globals.CALORIE_LOSS_PER_CHALLENGE, uB.getCalories());
    uB.challenge(uA);
    assertEquals(500, uA.getCalories());
    assertEquals(499 - 2 * Globals.CALORIE_LOSS_PER_CHALLENGE, uB.getCalories());
  }

  @Test
  public void testFightWhenOthersCaloriesIsBetter() {
    Unit uA = new Offensive("Rick", Globals.SIDE_LEFT);
    Unit uB = new Offensive("Bob", Globals.SIDE_RIGHT);
    uA.setStrength(800);
    uB.setStrength(900);

    uA.challenge(uB);
    // B > A
    assertEquals(800 - Globals.CALORIE_LOSS_PER_CHALLENGE, uA.getCalories());
    assertEquals(900, uB.getCalories());
    uB.challenge(uA);
    assertEquals(800 - 2 * Globals.CALORIE_LOSS_PER_CHALLENGE, uA.getCalories());
    assertEquals(900, uB.getCalories());
  }

  @Test(expected = CannotChallengeException.class)
  public void testDifferentSides() {
    Unit uA = new Offensive("Bob", Globals.SIDE_LEFT);
    Unit uB = new Offensive("Bob", Globals.SIDE_LEFT);
    uA.challenge(uB);
  }

  @Test(expected = CannotChallengeException.class)
  public void testDifferentSidesWithADifferentReceiver() {
    Unit uA = new Offensive("Bob", Globals.SIDE_RIGHT);
    Unit uB = new Offensive("Bob", Globals.SIDE_RIGHT);
    uB.challenge(uA);
  }

  /**
   * Try to have one unit take a bandana from another where both begin with 2
   * flags, but uA has more calories and should eventually get a bandana.
   * 
   * Current plan is to have one take another bandana when one has twice as many
   * calories (or the other has only half)
   */
  @Test
  public void testOneUnitGetsAnothersFlag() {
    int intialCaloriesOfA = 5 * Globals.CALORIE_LOSS_PER_CHALLENGE;
    Unit uA = new Offensive("WinnerToBe", Globals.SIDE_RIGHT);
    int intialCaloriesOfB = 3 * Globals.CALORIE_LOSS_PER_CHALLENGE;
    Unit uB = new JailBreaker("WillEventuallyLose", Globals.SIDE_LEFT);

    uA.setStrength(intialCaloriesOfA);
    uB.setStrength(intialCaloriesOfB);

    // Sanity check
    assertEquals(1, uA.getBandanaCount());
    assertEquals(1, uB.getBandanaCount());

    // Let uA battle uB until uA removes 1 flag from uB.
    // Current thought is to have uA must with twice the
    // calories for a flag take.
    uA.challenge(uB);
    assertEquals(intialCaloriesOfA, uA.getCalories());
    assertEquals(intialCaloriesOfB - Globals.CALORIE_LOSS_PER_CHALLENGE,
        uB.getCalories());
    // 2nd challenge with B's move to challenge A
    uB.challenge(uA);
    assertEquals(intialCaloriesOfA, uA.getCalories());
    assertEquals(intialCaloriesOfB - 2 * Globals.CALORIE_LOSS_PER_CHALLENGE,
        uB.getCalories());
  }

  @Test
  public void testChallengeTakesBandana() {
    int intialCaloriesOfA = 5 * Globals.CALORIE_LOSS_PER_CHALLENGE;
    Unit uA = new Offensive("WinnerToBe", Globals.SIDE_RIGHT);
    int intialCaloriesOfB = 3 * Globals.CALORIE_LOSS_PER_CHALLENGE;
    Unit uB = new Offensive("WillEventuallyLose", Globals.SIDE_LEFT);

    uA.setStrength(intialCaloriesOfA);
    uB.setStrength(intialCaloriesOfB);

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
    Unit uA = new Offensive("WinnerToBe", Globals.SIDE_RIGHT);
    int intialCaloriesOfB = 2 * Globals.CALORIE_LOSS_PER_CHALLENGE;
    Unit uB = new Offensive("WillEventuallyLose", Globals.SIDE_LEFT);

    uA.setStrength(intialCaloriesOfA);
    uB.setStrength(intialCaloriesOfB);

    // Sanity check
    assertEquals(1, uA.getBandanaCount());
    assertEquals(1, uB.getBandanaCount());
    uA.challenge(uB);
    assertTrue(uB.isTagged());
  }

  @Test
  public void testIfTagged() {
    int intialCaloriesOfA = 5 * Globals.CALORIE_LOSS_PER_CHALLENGE;
    int intialCaloriesOfB = 4 * Globals.CALORIE_LOSS_PER_CHALLENGE;

    Unit uA = new Offensive("WinnerToBe", Globals.SIDE_LEFT);
    Unit uB = new Offensive("WillEventuallyLose", Globals.SIDE_RIGHT);

    uA.setStrength(intialCaloriesOfA);
    uB.setStrength(intialCaloriesOfB);

    // Needs to challenges to get to 50 >= 2 *(40-10-10)
    uA.challenge(uB);
    uA.challenge(uB);
    assertEquals(1, uA.getBandanaCount());
    assertEquals(0, uB.getBandanaCount());
    assertTrue(uB.isTagged());
  }

  @Test
  public void testIsTagged() {
    Unit uA = new Offensive("A", Globals.SIDE_RIGHT);
    Unit second = new Offensive("B", Globals.SIDE_LEFT);
    Unit third = new Offensive("C", Globals.SIDE_LEFT);
    Unit fourth = new Offensive("D", Globals.SIDE_LEFT);

    uA.setStrength(6 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    second.setStrength(4 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    third.setStrength(4 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    fourth.setStrength(12 * Globals.CALORIE_LOSS_PER_CHALLENGE);

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
    Unit uA = new Offensive("A", Globals.SIDE_LEFT);
    Unit second = new Offensive("B", Globals.SIDE_RIGHT);
    Unit third = new Offensive("C", Globals.SIDE_RIGHT);
    Unit fourth = new Offensive("D", Globals.SIDE_RIGHT);

    uA.setStrength(6 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    second.setStrength(4 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    third.setStrength(4 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    fourth.setStrength(12 * Globals.CALORIE_LOSS_PER_CHALLENGE);

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
    Unit uA = new Offensive("A", Globals.SIDE_RIGHT);
    Unit other = new Offensive("B", Globals.SIDE_LEFT);

    uA.setStrength(5 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    other.setStrength(3 * Globals.CALORIE_LOSS_PER_CHALLENGE);

    uA.challenge(other); // should take bandana with only one challenge
    assertEquals(1, uA.challengesInvolvedIn());
    assertEquals(1, uA.challengesWon());
    assertEquals(0, uA.getRewards().size());
    uA.challenge(other); // should take bandana with only one challenge
    assertEquals(2, uA.challengesInvolvedIn());
    assertEquals(2, uA.challengesWon());
    assertEquals(0, uA.getRewards().size());

    Unit third = new Offensive("C", Globals.SIDE_LEFT);

    third.setStrength(2 * Globals.CALORIE_LOSS_PER_CHALLENGE);

    uA.challenge(third); // should take bandana with only one challenge
    assertEquals(3, uA.challengesInvolvedIn());
    assertEquals(3, uA.challengesWon());
    assertEquals(0, uA.getRewards().size());

    Unit fourth = new Offensive("D", Globals.SIDE_LEFT);

    fourth.setStrength(6 * Globals.CALORIE_LOSS_PER_CHALLENGE);

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
    Unit uA = new Offensive("A", Globals.SIDE_LEFT);
    uA.setStrength(5 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    Unit other = null;
    for (int wins = 1; wins <= Globals.WINS_FOR_REWARD - 1; wins++) {
      // Each new B will always be tagged with only one challenge
      // because that will make B have half to calories of A3 *
      // Globals.CALORIE_LOSS_PER_CHALLENGE,
      other = new Offensive("B", Globals.SIDE_RIGHT);
      other.setStrength(3 * Globals.CALORIE_LOSS_PER_CHALLENGE);

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

    Unit uA = new Offensive("A", Globals.SIDE_LEFT);
    uA.setStrength(2000 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    for (int wins = 1; wins <= Globals.WINS_FOR_REWARD; wins++) {
      Unit other = new Offensive("B", Globals.SIDE_RIGHT);
      other.setStrength(1000 * Globals.CALORIE_LOSS_PER_CHALLENGE);
      uA.challenge(other); // should win every time
    }
    // uA should have non zero stats
    assertEquals(1, uA.getRewards().size());
  }

  @Test
  public void testGetAward() {
    Unit uA = new Offensive("A", Globals.SIDE_LEFT);
    uA.setStrength(2000 * Globals.CALORIE_LOSS_PER_CHALLENGE);
    Unit other = null;
    // Almost get a reward for
    for (int wins = 1; wins <= Globals.WINS_FOR_REWARD - 1; wins++) {
      other = new Offensive("B", Globals.SIDE_RIGHT);
      other.setStrength(1000 * Globals.CALORIE_LOSS_PER_CHALLENGE);
      uA.challenge(other); // should win every time
    }
    // uA should have non zero stats
    assertEquals(0, uA.getRewards().size());
    uA.challenge(other);
    assertEquals(1, uA.getRewards().size());
  }

  @Test
  public void testMutators() {
    Unit p = new Offensive("Bob", Globals.SIDE_LEFT);
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
    Unit p = new Offensive("Bob", Globals.SIDE_LEFT);
    for (int wins = 1; wins <= 8; wins++) {
      p.addToChallengesInvolvedIn();
    }
    assertEquals(8, p.challengesInvolvedIn());
    p.addToChallengesWon();
    assertEquals(8, p.challengesInvolvedIn());
  }

  @Test
  public void testChallengesWonCount() {
    Unit p = new Offensive("Bob", Globals.SIDE_LEFT);
    for (int wins = 1; wins <= Globals.WINS_FOR_REWARD - 1; wins++) {
      p.addToChallengesWon();
    }
    assertEquals(Globals.WINS_FOR_REWARD - 1, p.challengesWon());
    p.addToChallengesWon();
    assertEquals(Globals.WINS_FOR_REWARD, p.challengesWon());
  }

  /**
   * The Rewards will probably never be fully implemented The cheap version is
   * the Unit's profile adds an Award when the Unit has won (taken a bandana) 10
   * times
   */
  @Test
  public void testAwardGiven() {
    Unit p = new Offensive("Bob", Globals.SIDE_LEFT);
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