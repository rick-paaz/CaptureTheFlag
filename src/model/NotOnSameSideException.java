package model;

public class NotOnSameSideException extends RuntimeException {

  public NotOnSameSideException() {
    super("The two units are on the same side and cannot challenge each other.");
  }
}