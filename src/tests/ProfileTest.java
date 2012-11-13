package tests;

import static org.junit.Assert.assertEquals;

import gamepieces.Unit;

import java.util.List;

import model.Globals;
import model.Profile;

import org.junit.Test;


public class ProfileTest {

  @Test
  public void testProfile() {
    // When the 2nd argument is true, we get a default list of untried
    // units with initial default values. When false, the previous 
    // version of this Profile is read form a serialized object
    Profile p = new Profile("Rick's First", "profileOne", true);
    // Should have four default units;
    // Use this as a bootstrap or perhaps an initial list
    List<Unit> units = p.getUnits();
    assertEquals(4, units.size());
    assertEquals(-1.0, p.getWinPercentage(), 1e-12);
    assertEquals("Rick's First", p.getName());
  }

  @Test
  public void testProfileWhenReadingFromFile() {
    Profile p = new Profile("Rick's First", "profileOne", true);
    List<Unit> units = p.getUnits();
    for (int reward = 1; reward <= Globals.WINS_FOR_REWARD - 1; reward++)
      units.get(0).addToChallengesWon();

    assertEquals(0, units.get(0).getRewards().size());
    units.get(0).addToChallengesWon();
    assertEquals(1, units.get(0).getRewards().size());

    units.get(0).chargeOneMoveCost();
    units.get(0).chargeOneMoveCost();
    units.get(0).chargeOneMoveCost();
    assertEquals(1, units.get(0).getRewards().size());
    assertEquals(0, units.get(0).challengesInvolvedIn());
    assertEquals(10, units.get(0).challengesWon());

    assertEquals(units.get(0).getCalories(), Globals.RUNNER_CALORIES - 3
        * Globals.RUNNER__MOVE_COST);

    p.saveThisPofile();
   }
  
  @Test
  public void testPersistentProfile() {
    // See if the changes above were saved and then reread
    Profile afterSaveAndRead = new Profile("Rick's First", "profileOne", false);
    List<Unit> units = afterSaveAndRead.getUnits();
    assertEquals(units.get(0).getCalories(), Globals.RUNNER_CALORIES - 3
        * Globals.RUNNER__MOVE_COST);
    assertEquals(1, units.get(0).getRewards().size());
    assertEquals(10, units.get(0).challengesWon());
    assertEquals("Rick's First", afterSaveAndRead.getName());
    assertEquals(-1.0, afterSaveAndRead.getWinPercentage(), 1e-12);

  }
}
