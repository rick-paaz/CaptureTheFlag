package view;

import game.Game;
import game.Globals;
import game.Profile;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RunCaptureTheFlagFrame extends JFrame {

  public static void main(String[] args) {
    RunCaptureTheFlagFrame frame = new RunCaptureTheFlagFrame();
    frame.setVisible(true);
  }

  private MapOne playFieldPanel;
  private Game game;

  public RunCaptureTheFlagFrame() {
    setSize(Globals.WIDTH + 200, Globals.HEIGHT + 200);
    setLocation(10, 10);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Capture the Flag");

    setLayout(null);
    
    // Initialize a game with two persistent Profiles
    game = new Game();
    Profile p1 = new Profile("leftSide");
    Profile p2 = new Profile("rightSide");

   //  I commented this failing searilization, add and committed
    // checked out two branches and found the changes are gone and
    // now replace with this
//    p1.setUnits(game);
//    p2.setUnits(game);

    playFieldPanel = new MapOne(game);
    playFieldPanel.setSize(Globals.WIDTH, Globals.HEIGHT);
    playFieldPanel.setLocation(10, 13);
    // setLayout(null); 
 
    playFieldPanel = new MapOne(game);
    playFieldPanel.setSize(500, 300);
    playFieldPanel.setLocation(5, 3);

    game.addObserver(playFieldPanel);

    //    playFieldPanel.setSize(PlayingFieldPanel.WIDTH, PlayingFieldPanel.HEIGHT);
    //    playFieldPanel.setLocation(25, 25);
    add(playFieldPanel);
  }

}