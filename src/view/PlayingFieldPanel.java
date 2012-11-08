package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Board;
import model.Globals;

public class PlayingFieldPanel extends JPanel implements Observer {

  private BufferedImage sprites;
  private Image flag;
  private Board board;

  // private JPanel[][] tiles;

  public PlayingFieldPanel(Board board) {
    this.board = board;
    ListenToKeys listener = new ListenToKeys();
    this.addKeyListener(listener);
    try {
      flag = ImageIO.read(new File("images/flag.gif"));
      sprites = ImageIO.read(new File("images/megaman7.gif"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    setBackground(color);
    animating = false;
    this.setSize(Globals.WIDTH, Globals.HEIGHT);
    setFocusable(true);
  }

  Graphics2D g2 = null;
  Color color = new Color(2, 165, 60);

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g2 = (Graphics2D) g;
    if (animating) {
      BufferedImage sub = sprites.getSubimage(x, y, 55, 55);
      g2.drawImage(sub, null, upperLeftX, upperLeftY);
    }
  }

  private class AnimationListener implements ActionListener {

    public void actionPerformed(ActionEvent arg0) {
      repaint();
      y = tic * 52;
      tic++;
      if (tic >= 5)
        timer.stop();
    }
  }

  int x;
  int y;
  int tic;
  Timer timer;
  private boolean animating;
  public int upperLeftX, upperLeftY;

  private void animateAnAttack() {
    timer = new Timer(100, new AnimationListener());
    x = 8 * 57;
    y = 1;
    tic = 1;
    upperLeftX = 150;
    upperLeftY = 100;
    repaint();
    timer.start();
    animating = true;
  }

  @Override
  public void update(Observable theboard, Object arg1) {
    board = (Board) theboard;
    repaint();
  }

  private class ListenToKeys implements KeyListener {

    @Override
    public void keyPressed(KeyEvent arg0) {
      // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent arg0) {
      // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent key) {
      System.out.println(key);
      char ch = key.getKeyChar();
      if (ch == 'a')
        animateAnAttack();
    }
  }
}