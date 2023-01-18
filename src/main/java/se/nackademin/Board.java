package se.nackademin;

/**
 * The gameboard where pieces are placed.
 */
public class Board {
	/**
	 * enum indicating the state of a placement on the board.
	 * SUCCESS: A piece can be placed.
	 * OCCUPIED: A piece is already placed.
	 * OUT_OF_BOUNDS: A piece is placed outside of the board.
	 */
	public enum PlacementState {
		SUCCESS,
		OCCUPIED,
		OUT_OF_BOUNDS
	}

	/**
	 * Creates a new gameboard.
	 * @param size the horizontal and vertical size of the board.
	 */
	Board(int size) {
	}

	/**
	 * Tries to place a piece on the board.
	 * @param x the column where the piece is tried to be placed.
	 * @param y the row where the piece is tried to be placed.
	 * @return PlacementState indicating its success.
	 */
	public PlacementState tryToPlace(int x, int y) {
		return PlacementState.OUT_OF_BOUNDS;
	}

	/**
	 * Checks wether the board has 4 pieces in a line,
	 * where line is either horizontal, vertical, or diagonal.
	 * @return a boolean indicating if such is the case.
	 */
	public boolean hasWon() {
		return false;
	}
}
