package se.nackademin;

public class Human implements Player {
	public int[] selectPlacement() { return new int[] {0,0}; }
	private String getUserInput() { return ""; }
	private boolean isInputValid(String str) { return false; }
	private int[] stringToCordinates(String str) { return new int[] {0,0}; }
}
