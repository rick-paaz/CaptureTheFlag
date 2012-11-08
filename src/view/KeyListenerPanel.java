package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Game;
import model.Globals;

public class KeyListenerPanel extends JPanel implements Observer {

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 400);
    frame.setLocation(20, 20);
    frame.setLayout(null);

    KeyListenerPanel panel = new KeyListenerPanel();
    panel.setSize(500, 300);
    panel.setLocation(50, 30);
    
    frame.getContentPane().add(panel);
    frame.setVisible(true);
  }

  private BufferedImage sprites;
  private Image flag;
  private Game game;

  public KeyListenerPanel() {
    ListenToKeys listener = new ListenToKeys();
    this.addKeyListener(listener);
    try {
      flag = ImageIO.read(new File("images/flag.gif"));
      sprites = ImageIO.read(new File("images/megaman7.gif"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
   setBackground(Color.WHITE);
    animating = false;
    this.setSize(Globals.WIDTH, Globals.HEIGHT);
    this.setFocusable(true);
    
    p00 = new Polygon(); 
    p00.addPoint(70, 10);
    p00.addPoint(130, 10);
    p00.addPoint(70, 70);
    p00.addPoint(0, 70);
  }
  
  Polygon p00;

  Color color = new Color(2, 165, 60);

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setPaint(color);
    g2.fill(p00);

    g2.drawImage(flag, 20, 20, null);
    
    if (animating) {
      BufferedImage sub = sprites.getSubimage(x, y, 55, 55);
      g2.drawImage(sub, upperLeftX, upperLeftY, null);
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
    game = (Game) theboard;
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