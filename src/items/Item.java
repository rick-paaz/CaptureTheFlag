package items;

/**
 * Still need obstacles, places other times can not enter
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.GamePiece;

public abstract class Item extends GamePiece {

  protected BufferedImage myImage;

  public Item(String name, String imageName) {
    super(name);
    createImage(imageName);
  }

  private void createImage(String imageName) {
    try {
      myImage = ImageIO.read(new File(imageName));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public BufferedImage getImage() {
    return myImage;
  }

}