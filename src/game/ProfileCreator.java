package game;

import gamepieces.Unit;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class ProfileCreator {

  // There will have to be an inverse method to read
  // this in, probably in class Profile.  Order must be this:
  //  1) the list of units
  //  2) the games tried by this player
  //  3) the games won by this player
  public static void saveProfile(String fileName, LinkedList<Unit> units) {

    try {
      FileOutputStream outFile = new FileOutputStream("serializedObjects/"
          + fileName);
      ObjectOutputStream outputStream = new ObjectOutputStream(outFile);
      int gamesWon = 0;
      int gamesTried = 0;
      outputStream.writeObject(units);
      outputStream.writeObject(gamesTried);
      outputStream.writeObject(gamesWon);
      // Do NOT forget to close the output stream!
      outputStream.close();
    } catch (IOException ioe) {
      String message = "Error writing objects to disk: " + "\n" + ioe
          + "\nHope you had data backed up...";
      JOptionPane.showMessageDialog(null, message);
    }
  }
}