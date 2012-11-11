package tests;

import model.Game;
import model.GamePiece;

import org.junit.Test;

import units.Runner;

public class GameTest {

  @Test
  public void testGetter() {
    Game game = new Game();
    GamePiece runner = new Runner("Bob");
    game.addPiece(runner, 2, 2);
    //   game.addPiece(new HumanFlag("images\flag.png"));

    //    game.setLocation(1, 0, new USA.gif());
    //    game.set(Globals.ROWS / 2, Globals.COLUMNS - 2, Item.OpponentFlag);
    //    game.set(2, 2, unit);
    //    game.set(4, 4, new BearTrap("images\bananas.png"));

  }
}