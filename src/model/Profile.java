package model;

import java.util.LinkedList;
import java.util.List;

import units.Unit;

public class Profile {

  private int gamesPlayed;
  private int gamesWon;
  private List<Unit> myUnits;

  public Profile() {
    setGamesPlayed(0);
    setGamesWon(0);
    myUnits = new LinkedList<Unit>();
  }

  public int getGamesPlayed() {
    return gamesPlayed;
  }

  public void setGamesPlayed(int gamesPlayed) {
    this.gamesPlayed = gamesPlayed;
  }

  public int getGamesWon() {
    return gamesWon;
  }

  public void setGamesWon(int gamesWon) {
    this.gamesWon = gamesWon;
  }

}