package view;

/**
 * This program is mean to demo key listeners
 * 
 * A new rectangle will be filled for each arrow click (unless it
 * would cause an exit from the board. 
 * 
 * Pressing the lower case 'a' key begins an animation
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class KeyListenerPanel extends JPanel {

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(WIDTH + 30, HEIGHT + 50);
    frame.setLocation(20, 20);
    frame.setLayout(null);

    KeyListenerPanel panel = new KeyListenerPanel();
    panel.setSize(WIDTH + 2, HEIGHT + 2);
    panel.setLocation(10, 10);

    frame.getContentPane().add(panel);
    frame.setVisible(true);
  }

  public final static int ROWS = 6;
  public final static int COLS = 12;
  public final static int TILE_SIZE = 57;

  public final static int HEIGHT = TILE_SIZE * ROWS;
  public final static int WIDTH = TILE_SIZE * COLS;
  public final static int OFFSET = 15; // move animation to right from upper left

  private BufferedImage sprites;
  private Rectangle2D.Double[][] recs;

  public KeyListenerPanel() {
    ListenToKeys listener = new ListenToKeys();
    this.addKeyListener(listener);
    try {
      sprites = ImageIO.read(new File("images/megaman7.gif"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    setBackground(Color.GREEN);
    animatingFight = false;
    this.setSize(WIDTH, HEIGHT);
    this.setFocusable(true);

    recs = new Rectangle2D.Double[6][12];
    for (int r = 0; r < 6; r++) {
      for (int c = 0; c < 12; c++) {
        recs[r][c] = new Rectangle2D.Double(c * TILE_SIZE, r * TILE_SIZE,
            TILE_SIZE, TILE_SIZE);
      }
    }

    // Arbitrary picked as starting location
    selectedRow = 2;
    selectedCol = 5;

    setUpFightAnimationData();
  }

  private void setUpFightAnimationData() {
    fightSubimages = new ArrayList<BufferedImage>();
    fightSubimages.add(sprites.getSubimage(505, 0, 50, 50));
    fightSubimages.add(sprites.getSubimage(505, 50, 50, 50));
    fightSubimages.add(sprites.getSubimage(505, 104, 50, 50));
    fightSubimages.add(sprites.getSubimage(505, 155, 50, 50));
    // get two from the last column of sprite sheet
    fightSubimages.add(sprites.getSubimage(505, 0, 50, 50));
    fightSubimages.add(sprites.getSubimage(505, 50, 50, 50));
    standingImage = sprites.getSubimage(110, 47, 50, 50);
  }

  private int selectedRow, selectedCol;
  private List<BufferedImage> fightSubimages;
  private BufferedImage standingImage;

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    for (int r = 0; r < 6; r++) {
      for (int c = 0; c < 12; c++) {
        g2.draw(recs[r][c]);
      }
    }

    drawAttackPossibilities(g2);

    if (animatingFight) {
      if (tic == fightSubimages.size()) {
        // We're done with the battle scene
        g2.drawImage(standingImage, upperLeftX, upperLeftY + 5, null);
        animatingFight = false;
      } else
        g2.drawImage(fightSubimages.get(tic), upperLeftX + 6, upperLeftY, null);
    } else
      g2.drawImage(standingImage, TILE_SIZE * selectedCol, TILE_SIZE
          * selectedRow, null);
  }

  private void drawAttackPossibilities(Graphics2D g2) {
    int r = selectedRow;
    int c = selectedCol;
    g2.setColor(Color.CYAN);
    if (r > 0 )
      g2.fill(recs[r-1][c]);
    if ( r < ROWS - 1)
      g2.fill(recs[r+1][c]);
   
    if ( c < COLS - 1)
      g2.fill(recs[r][c+1]);
 
    if ( c > 0)
      g2.fill(recs[r][c-1]);
 
    
    g2.setColor(Color.BLACK);
  }

  int y;

  private class AnimationListener implements ActionListener {

    public void actionPerformed(ActionEvent arg0) {
      repaint();
      y += fightSubimages.get(tic).getHeight();
      tic++;
      if (tic >= fightSubimages.size()) {
        timer.stop();
      }
    }
  }

  int tic;
  Timer timer;
  private boolean animatingFight;
  public int upperLeftX, upperLeftY;
  public static final int ms_delay_in_timer = 75;

  private void animateAnAttack() {
    timer = new Timer(ms_delay_in_timer, new AnimationListener());
    tic = 0;
    upperLeftX = selectedCol * TILE_SIZE;
    upperLeftY = selectedRow * TILE_SIZE;
    repaint();
    timer.start();
    animatingFight = true;
  }

  private class ListenToKeys implements KeyListener {

    public void keyPressed(KeyEvent key) {
      if (animatingFight) // Don't move current location until the fight is over
        return;
      int keyCode = key.getKeyCode();

      if (keyCode == KeyEvent.VK_UP) {
        System.out.println("Up arrow");
        if (selectedRow > 0)
          selectedRow -= 1;
      }

      if (keyCode == KeyEvent.VK_DOWN) {
        System.out.println("Down arrow");
        if (selectedRow < ROWS - 1)
          selectedRow += 1;
      }

      if (keyCode == KeyEvent.VK_LEFT) {
        System.out.println("Left arrow");
        if (selectedCol > 0)
          selectedCol -= 1;
      }

      if (keyCode == KeyEvent.VK_RIGHT) {
        System.out.println("Right arrow");
        if (selectedCol < COLS - 1)
          selectedCol += 1;
      }

      // redraw the whole panel with a new selected position
      repaint();
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
      // TODO Auto-generated method stub
    }

    @Override
    public void keyTyped(KeyEvent key) {
      char ch = key.getKeyChar();
      // Only start a fight if not in a fight, ignore the key press
      if (!animatingFight && ch == 'a')
        animateAnAttack();
    }

  }
}