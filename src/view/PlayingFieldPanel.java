package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Game;
import model.Globals;
import model.Item;
import model.Unit;

public class PlayingFieldPanel extends JPanel implements Observer {

  private Image unitImage;
  private Image flag;
  private Image knight;
  private Game game;

  // private JPanel[][] tiles;

  public PlayingFieldPanel(Game game) {
    this.game = game;
    this.setBackground(Color.GREEN); // new Color(0, 160, 55));
    ListenToMouseClick listener = new ListenToMouseClick();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
    try {
      unitImage = ImageIO.read(new File("images/Sponge.png"));
      flag = ImageIO.read(new File("images/flag.gif"));
      knight = ImageIO.read(new File("images/knight1.png"));
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    setSize(Globals.WIDTH, Globals.HEIGHT);

    //    setLayout(new GridLayout(ROWS, COLUMNS, 1, 1));

    //    tiles = new JPanel[ROWS][COLUMNS];
    //    for (int i = 0; i < ROWS; i++) {
    //      for (int j = 0; j < COLUMNS; j++) {
    //        tiles[i][j] = new JPanel();
    //        tiles[i][j].setBackground(new Color(0, 160 + 3 * i, 65 + 2 * j));
    //        add(tiles[i][j]);
    //      }
    //    }
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    for (int r = 0; r < Globals.ROWS; r++) {
      for (int c = 0; c < Globals.COLUMNS; c++) {
        Object o = game.getSprite(r, c);
        if (o instanceof Unit)
          g2.drawImage(unitImage, r * Globals.WIDTH / Globals.TILE_SIZE, c
              * Globals.WIDTH / Globals.TILE_SIZE, null);
        if (o instanceof Item)
          g2.drawImage(knight, r * Globals.WIDTH / Globals.TILE_SIZE, c
              * Globals.WIDTH / Globals.TILE_SIZE, null);
      }
    }
    this.setBackground(Color.GREEN); // new Color(0, 160, 55));
  }

  @Override
  public void update(Observable theGame, Object arg1) {
    game = (Game) theGame;
    System.out.println("In update");
    repaint();
  }

  private class ListenToMouseClick implements MouseListener,
      MouseMotionListener {

    @Override
    public void mouseDragged(MouseEvent arg0) {
      // TODO Auto-generated method stub
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
      System.out.println("Moved" + arg0.getX() + " " + arg0.getY());
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
      game.moveUnit(0, 0, 0, 0);
      System.out.println("Moved" + arg0.getX() + " " + arg0.getY());

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
      // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
      // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent arg0) {

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
      // TODO Auto-generated method stub
    }

  }
}