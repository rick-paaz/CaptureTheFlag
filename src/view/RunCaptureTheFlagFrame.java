package view;

import game.Game;
import game.Globals;

import javax.swing.JFrame;

public class RunCaptureTheFlagFrame extends JFrame {

  public static void main(String[] args) {
    RunCaptureTheFlagFrame frame = new RunCaptureTheFlagFrame();
    frame.setVisible(true);
  }

  private MapOne playFieldPanel;
  private Game game;

  public RunCaptureTheFlagFrame() {
    setSize(Globals.WIDTH + 100, Globals.HEIGHT + 100);
    setLocation(15, 15);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Capture the Flag");

    setLayout(null);

    // Initialize a game with two persistent Profiles
    game = new Game();
    // Profile p1 = new Profile("leftSide");
    // Profile p2 = new Profile("rightSide");

    // 2-March-14 This attempt to read from the serialized object results
    // in a null point exception.  Get rid of the Profile type perhaps?
    //    p1.setUnits(game);
    //    p2.setUnits(game);

    playFieldPanel = new MapOne(game);
    playFieldPanel.setSize(Globals.WIDTH, Globals.HEIGHT);
    playFieldPanel.setLocation(10, 13);
    // setLayout(null); 

    playFieldPanel = new MapOne(game);
  //  playFieldPanel.setSize(500, 300);
    playFieldPanel.setSize(400, 240); 
    
    playFieldPanel.setLocation(5, 3);

    game.addObserver(playFieldPanel);

    //    playFieldPanel.setSize(PlayingFieldPanel.WIDTH, PlayingFieldPanel.HEIGHT);
    //    playFieldPanel.setLocation(25, 25);
    add(playFieldPanel);
  }

}