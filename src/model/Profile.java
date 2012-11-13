package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import units.Defender;
import units.JailBreaker;
import units.Offensive;
import units.Unit;

public class Profile implements Serializable {

  private List<Unit> units;
  private int gamesPlayed;
  private int gamesWon;
  private String owner;
  public String fileName;

  public Profile(String owner, String fileName, boolean createDefaultUnits) {
    this.fileName = fileName;
    this.owner = owner;
    if (createDefaultUnits) {
      gamesPlayed = 0;
      gamesWon = 0;
      createDefaultUnitList();
    } else {
      usePersistentProfile();
    }
  }

  private void createDefaultUnitList() {
    Unit a = new Offensive("Runner 1", Globals.SIDE_LEFT);
    Unit b = new Offensive("Runner 2", Globals.SIDE_LEFT);
    Unit c = new Defender("Defender", Globals.SIDE_LEFT);
    Unit d = new JailBreaker("Free Tagged Players", Globals.SIDE_LEFT);

    units = new LinkedList<Unit>();
    units.add(a);
    units.add(b);
    units.add(c);
    units.add(d);
  }

  public void saveThisPofile() {
    try {
      FileOutputStream outFile = new FileOutputStream("serializedObjects/"
          + fileName);
      ObjectOutputStream outputStream = new ObjectOutputStream(outFile);
      outputStream.writeObject(gamesPlayed);
      outputStream.writeObject(gamesWon);
      outputStream.writeObject(units);
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

      gamesPlayed = (Integer) inputStream.readObject();
      gamesWon = (Integer) inputStream.readObject();
      units = (List<Unit>) inputStream.readObject();
      inputStream.close();
    } catch (Exception e) {
      e.printStackTrace();
      String message = "Error reading serialized objects\n";
      JOptionPane.showMessageDialog(null, message);
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
}
