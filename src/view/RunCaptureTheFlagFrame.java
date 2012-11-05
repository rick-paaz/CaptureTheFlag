package view;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Game;
import model.Globals;
import model.Item;
import model.Offender;
import model.Unit;

public class RunCaptureTheFlagFrame extends JFrame {

  public static void main(String[] args) {
    RunCaptureTheFlagFrame frame = new RunCaptureTheFlagFrame();
    frame.setVisible(true);
  }

  private PlayingFieldPanel playFieldPanel;
  private Game game;

  public RunCaptureTheFlagFrame() {
    setSize(Globals.WIDTH + 200, Globals.HEIGHT + 200);
    setLocation(10, 10);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Capture the Flag");

    game = new Game();
    setUpGame();
    // setLayout(null); 

    JPanel p = new JPanel();
    p.setBackground(Color.RED);
    p.setSize(20, 20);
    p.setLocation(45, 37);
    add(p);

    playFieldPanel = new PlayingFieldPanel(game);
    playFieldPanel.setSize(500, 300);
    playFieldPanel.setLocation(5, 3);

    game.addObserver(playFieldPanel);

//    playFieldPanel.setSize(PlayingFieldPanel.WIDTH, PlayingFieldPanel.HEIGHT);
//    playFieldPanel.setLocation(25, 25);
    add(playFieldPanel);
  }

  public void setUpGame() {
    Unit unit = new Offender("Bob");
    game.set(1, 0, Item.HumanFlag);
    game.set(Globals.ROWS / 2, Globals.COLUMNS - 2, Item.OpponentFlag);
    game.set(2, 2, unit);
    game.set(4, 4, Item.Bear);
  }
}