package view;

import items.Item;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import units.Runner;

import model.Game;
import model.Globals;
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

    setLayout(null);
    game = new Game();
    
    playFieldPanel = new PlayingFieldPanel(game);
    playFieldPanel.setSize(Globals.WIDTH, Globals.HEIGHT);
    playFieldPanel.setLocation(10, 13);
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

}