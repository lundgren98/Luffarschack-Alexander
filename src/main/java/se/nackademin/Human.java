package se.nackademin;

import java.util.Arrays;
import java.io.IOException;
import java.io.Reader;
import java.util.OptionalInt;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

/**
 * A human player.
 */
public class Human implements Player {
	private Reader read;

	/**
	 * Lets the user select a square on the board to place his piece on.
	 * @return the cordinates for the piece.
	 */
	public int[] selectPlacement(Board board) {
		Terminal term;
		try {
			term = TerminalBuilder.builder()
				.jna(true)
				.system(true)
				.build();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
			return new int[] { -1, -1 };
		}
		term.enterRawMode();
		read = term.reader();

		int x = board.getHighlightX();
		int y = board.getHighlightY();
		int size = board.getSize();
		final int ERROR = -1;
		final int ENTER = 13;
		final int UP = (int)'w';
		final int LEFT = (int)'a';
		final int DOWN = (int)'s';
		final int RIGHT = (int)'d';
		int input;
		while ((input = getUserInput().orElse(ERROR)) != ENTER) {
			switch (input) {
				case UP:
					y += (y > 0) ? -1 : 0;
					break;
				case DOWN:
					y += (y < size - 1) ? 1 : 0;
					break;
				case LEFT:
					x += (x > 0) ? -1 : 0;
					break;
				case RIGHT:
					x += (x < size - 1) ? 1 : 0;
					break;
				default:
					continue;
			}
			board.setHighlight(x, y);
			System.out.println(board.toString());
		}
		try {
			read.close();
			term.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
			return new int[] { -1, -1 };
		}
		return new int[] { x, y };
	}

	/**
	 * Read input from user
	 * @return an Optional for ascii keycode for the key pressed.
	 */
	private OptionalInt getUserInput() {
		try {
			return OptionalInt.of(read.read());
		} catch (IOException e) {
			return OptionalInt.empty();
		}
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
