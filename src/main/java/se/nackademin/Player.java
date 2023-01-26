package se.nackademin;

/**
 * A generic player to play on a gameboard.
 */
public interface Player {
	/**
	 * A player makes his decisions depending on the state of the board.
	 * @param board the game board.
	 * @return a {@link se.nackademin.Point} indicating a place on the board.
	 * @see se.nackademin.Board
	 */
	public Point selectPlacement(Board board);
}
