package model;

public class CannotChallengeException extends RuntimeException {
	public CannotChallengeException() {
		super("Units are not next to each other or are in the same space");

	}
}
