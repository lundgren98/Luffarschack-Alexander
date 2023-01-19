package se.nackademin;

import java.util.Scanner;
import java.util.Arrays;

public class Human implements Player {
	/**
	 * Lets the user select a square on the board to place his piece on.
	 * @return the cordinates for the piece.
	 */
	public int[] selectPlacement() {
		String userInput;
		do {
			userInput = getUserInput();
		} while (!isInputValid(userInput));
		return stringToCordinates(userInput);
	}

	/**
	 * Read input from user
	 * @return the string the user wrote
	 */
	private String getUserInput() {
		Scanner sc = new Scanner(System.in);
		String userInput = sc.nextLine();
		sc.close();
		return userInput;
	}

	/**
	 * Checks wether the string is of the form "x:y" where x and y
	 * are integers
	 * @param str the string which is checked.
	 * @return a boolean indicating if such is the case.
	 */
	private boolean isInputValid(String str) {
		String[] splt = str.split(":");
		if (splt.length != 2)
			return false;
		try {
			for (String s : splt) {
				Integer.parseInt(s);
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Converts strings of the form "x:y" to an integer array { x, y }
	 * @param str the string to be converted.
	 * @return an integer array of length 2 indicating the cordinates.
	 */
	private int[] stringToCordinates(String str) {
		return Arrays.asList(str.split(":"))
			.stream()
			.map(s -> Integer.parseInt(s))
			.mapToInt(i -> i)
			.toArray();
	}
}
