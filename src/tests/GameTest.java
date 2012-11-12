package tests;

import static org.junit.Assert.*;
import model.Game;
import model.GamePiece;
import model.Globals;

import org.junit.Test;

import units.Defender;
import units.JailBreaker;
import units.Runner;
import units.Unit;

public class GameTest {

  @Test
  public void testGameSetupAndMakeOneChallenge() {
    Game game = new Game();
    GamePiece human1 = new Runner("h1", Globals.SIDE_LEFT);
    GamePiece human2 = new Defender("h2", Globals.SIDE_LEFT);
    GamePiece human3 = new JailBreaker("h3", Globals.SIDE_LEFT);

    game.addPiece(human1, 2, 2);
    game.addPiece(human2, 4, 1);
    game.addPiece(human3, 3, 0);

    GamePiece player1 = new Runner("p1", Globals.SIDE_RIGHT);
    GamePiece player2 = new Defender("p2", Globals.SIDE_RIGHT);
    GamePiece player3 = new JailBreaker("p3", Globals.SIDE_RIGHT);

    game.addPiece(player1, 2, Globals.COLUMNS - 3);
    game.addPiece(player2, 4, Globals.COLUMNS - 2);
    game.addPiece(player3, 3, Globals.COLUMNS - 1);
    
    Unit uA = game.getUnit(2, 2);
    assertEquals(Globals.RUNNER_CALORIES, uA.getCalories());
    game.moveUnit(2, 2, 2, 3);
    assertEquals(Globals.RUNNER_CALORIES - Globals.RUNNER__MOVE_COST,
        uA.getCalories());

    game.moveUnit(2, 7, 2, 6);
    game.moveUnit(2, 3, 2, 4);
    assertEquals(Globals.RUNNER_CALORIES - 2 * Globals.RUNNER__MOVE_COST,
        uA.getCalories());
    game.moveUnit(2, 6, 2, 5);
    
    assertEquals(960, game.getUnit(2, 4).getCalories());
    assertEquals(960, game.getUnit(2, 5).getCalories());
    
    Unit attacker = game.getUnit(2, 4);
    Unit other = game.getUnit(2, 5);
    attacker.challenge(other);
    
    assertEquals(game.getUnit(2, 4).getCalories(), game.getUnit(2, 5).getCalories());
    
    game.moveUnit(2, 5, 1, 5);
    game.moveUnit(1, 5, 2, 5);
    
    assertEquals(920, game.getUnit(2, 5).getCalories());

    
    game.moveUnit(3, 0, 2, 0);
    assertEquals(245, game.getUnit(2, 0).getCalories());

    game.moveUnit(4, 1, 5, 2);
    assertEquals(738, game.getUnit(5, 2).getCalories());
  }
  
  @Test
  public void testGetPieceInChallange() {
    Game game = new Game();
    
    Unit human1 = new Runner("h1", Globals.SIDE_LEFT);
    game.addPiece(human1, 4, 2);
    
    Unit p2 = new Defender("p2", Globals.SIDE_RIGHT);
    game.addPiece(p2, 3, 2);
 
    assertEquals(Globals.RUNNER_CALORIES , human1.getCalories());
    
    Unit attack = game.getUnit(4, 2);
    Unit defend = game.getUnit(3, 2);
    
    attack.challenge(defend);
    
    assertEquals(Globals.DEFENDER_CALORIES - Globals.CALORIE_LOSS_PER_CHALLENGE , p2.getCalories());
  }

  
  @Test
  public void testUnitKnowPositionAfteSetAndMoveTo() {
    Game game = new Game();
    
    Unit human1 = new Runner("h1", Globals.SIDE_LEFT);
    game.addPiece(human1, 4, 2);
    
    Unit p2 = new Defender("p2", Globals.SIDE_RIGHT);
    game.addPiece(p2, 3, 2);
 
    assertEquals(4, human1.getRow());
    assertEquals(2, human1.getColumn());
    assertEquals(3, p2.getRow());
    assertEquals(2, p2.getColumn());
    
    game.moveUnit(4, 2, 5, 3);
    game.moveUnit(3, 2, 2, 3);
    
    assertEquals(5, human1.getRow());
    assertEquals(3, human1.getColumn());   
    assertEquals(2, p2.getRow());
    assertEquals(3, p2.getColumn());
   
    
  }
}
  