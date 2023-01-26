package se.nackademin;

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
	public Point selectPlacement(Board board) {
		Terminal term;
		try {
			term = TerminalBuilder.builder()
				.jna(true)
				.system(true)
				.build();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
			return new Point();
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
			return new Point(-1, -1);
		}
		return new Point(x, y);
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
}
