package game;

import gamepieces.Unit;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

public class Profile implements Serializable {

  private LinkedList<Unit> units;
  private int gamesPlayed;
  private int gamesWon;
  private String owner;
  public String fileName;

  public Profile(String fileName) {
    this.fileName = fileName;
    usePersistentProfile();
  }

  public void saveThisPofile() {
    try {
      FileOutputStream outFile = new FileOutputStream("serializedObjects/"
          + fileName);
      ObjectOutputStream outputStream = new ObjectOutputStream(outFile);
      outputStream.writeObject(units);
      outputStream.writeObject(gamesPlayed);
      outputStream.writeObject(gamesWon);
      // Do NOT forget to close the output stream!
      outputStream.close();
    } catch (IOException ioe) {
      String message = "Error writing objects to disk: " + "\n" + ioe
          + "\nHope you had data backed up...";
      JOptionPane.showMessageDialog(null, message);
    }
  }

  @SuppressWarnings("unchecked")
  public void usePersistentProfile() {
    try {
      // Read the BankAccountCollection object from it disk file
      FileInputStream inFile = new FileInputStream("serializedObjects/"
          + fileName);
      ObjectInputStream inputStream = new ObjectInputStream(inFile);

      units = (LinkedList<Unit>) inputStream.readObject();
      gamesPlayed = (Integer) inputStream.readObject();
      gamesWon = (Integer) inputStream.readObject();
      inputStream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<Unit> getUnits() {
    return units;
  }

  public double getWinPercentage() {
    if (gamesPlayed <= 0)
      return -1.0;
    else
      return gamesWon / gamesPlayed;
  }

  public String getName() {
    return owner;
  }

  public void setUnits(Game g) {
    for (int piece = 0; piece < units.size(); piece++) {
      Unit unit = units.get(piece);
      g.addPiece(unit, unit.getRow(), unit.getColumn());
    }
  }
}