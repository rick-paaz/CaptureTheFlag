package tests;

import game.Game;
import game.Globals;
import game.Profile;
import game.ProfileCreator;
import gamepieces.Defender;
import gamepieces.JailBreaker;
import gamepieces.Offensive;
import gamepieces.Unit;

import java.util.LinkedList;

public class CreateTwoPofiles {

  public static void main(String[] args) {

    Unit a = new Offensive("Runner 1", Globals.SIDE_LEFT);
    Unit b = new Offensive("Runner 2", Globals.SIDE_LEFT);
    Unit c = new Defender("Defender", Globals.SIDE_LEFT);
    Unit d = new JailBreaker("Free Tagged Players", Globals.SIDE_LEFT);

    a.setPosition(2, 3);
    b.setPosition(4, 3);
    c.setPosition(3, 1);
    d.setPosition(0, 3);

    LinkedList<Unit> unitsOne = new LinkedList<Unit>();
    unitsOne.add(a);
    unitsOne.add(b);
    unitsOne.add(c);
    unitsOne.add(d);

    /////////////////////////////////////////////////

    Unit a2 = new Offensive("Runner 1", Globals.SIDE_RIGHT);
    Unit b2 = new Offensive("Runner 2", Globals.SIDE_RIGHT);
    Unit c2 = new Defender("Defender", Globals.SIDE_RIGHT);
    Unit d2 = new JailBreaker("Free Tagged Players", Globals.SIDE_RIGHT);

    a2.setPosition(2, Globals.COLUMNS - 3);
    b2.setPosition(4, Globals.COLUMNS - 3);
    c2.setPosition(3, Globals.COLUMNS - 1);
    d2.setPosition(2, Globals.COLUMNS - 1);

    LinkedList<Unit> unitsTwo = new LinkedList<Unit>();
    unitsTwo.add(a2);
    unitsTwo.add(b2);
    unitsTwo.add(c2);
    unitsTwo.add(d2);

    // Use the existing profiles to begin a game
    ProfileCreator.saveProfile("leftSide", unitsOne);
    ProfileCreator.saveProfile("rightSide", unitsTwo);

    Profile p1 = new Profile("leftSide");
    Profile p2 = new Profile("rightSide");

    Game g = new Game();
    p1.setUnits(g);
    p2.setUnits(g);

    System.out.println(g);
  }
}