package tests;

import model.Game;
import model.GamePiece;
import model.Globals;

import org.junit.Test;

import units.Defender;
import units.JailBreaker;
import units.Runner;

public class GameTest {

  @Test
  public void testGetter() {
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

    //   game.addPiece(new HumanFlag("images\flag.png"));

    //    game.setLocation(1, 0, new USA.gif());
    //    game.set(Globals.ROWS / 2, Globals.COLUMNS - 2, Item.OpponentFlag);
    //    game.set(2, 2, unit);
    //    game.set(4, 4, new BearTrap("images\bananas.png"));

  }
}