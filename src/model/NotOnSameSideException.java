package model;

@SuppressWarnings("serial")
public class NotOnSameSideException extends RuntimeException {

  public NotOnSameSideException() {
    super("The two units are on the same side and cannot challenge each other.");
  }
}