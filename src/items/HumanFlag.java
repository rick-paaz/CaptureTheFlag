package items;

import java.awt.image.BufferedImage;


public class HumanFlag extends Item {

  public HumanFlag(String imageName) {
    super("HumanFlag", imageName);
  }
  
  public char getLetterRepresentation() {
    // TODO Auto-generated method stub
    return 'F';
  }
}