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
	 * enum indicating if a square is has a piece on it or is available.
	 */
	public enum Square {
		AVAILABLE,
		CIRCLE,
		CROSS;
		boolean isOccupied() {
			return (this != AVAILABLE);
		}
		@Override
		public String toString() {
			switch (this) {
				case AVAILABLE: return ".";
				case CIRCLE: return "O";
				case CROSS: return "X";
				default: return "Something went awfully wrong!";
			}
		}
	}

	private static final int WINNING_STREAK = 4;

	private int size = 1;
	private Square[][] grid;

	/**
	 * Creates a new gameboard.
	 * @param size the horizontal and vertical size of the board.
	 */
	Board(int size) {
		this.size = size;
		this.prepareGrid();
	}

	/**
	 * Creates a two-dimensional array of Squares,
	 * all of which are available.
	 */
	private void prepareGrid() {
		this.grid = new Square[this.size][this.size];
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++)
				this.grid[y][x] = Square.AVAILABLE;
	}

	@Override
	public String toString() {
		String retVal = "";
		for (Square[] row : this.grid) {
			for (Square square : row) {
				retVal += square.toString();
			}
			retVal += "\n";
		}
		return retVal;
	}

	/**
	 * Tries to place a piece on the board.
	 * @param x the column where the piece is tried to be placed.
	 * @param y the row where the piece is tried to be placed.
	 * @param square the type of piece.
	 * @return PlacementState indicating its success.
	 */
	public PlacementState tryToPlace(int x, int y, Square square) {
		if (x < 0 || y < 0 || x >= this.size || y >= this.size)
			return PlacementState.OUT_OF_BOUNDS;
		if (this.grid[y][x].isOccupied())
			return PlacementState.OCCUPIED;
		this.grid[y][x] = square;
		return PlacementState.SUCCESS;
	}

	/**
	 * Checks wether the board has consecutively WINNING_STREAK pieces
	 * in any line, where line is either horizontal, vertical, or diagonal.
	 * @param square the type of piece.
	 * @return a boolean indicating if such is the case.
	 */
	public boolean hasWon(Square square) {
		return nonDiagonalWin(square) || DiagonalWin(square);
	}

	/**
	 * Checks wether the board has consecutively WINNING_STREAK
	 * pieces in any horizontal or vertical line.
	 * @return a boolean indicating if such is the case
	 */
	private boolean nonDiagonalWin(Square square) {
		int rowStreak = 0;
		int columnStreak = 0;
		for (int x = 0; x < this.size; x++) {
			for (int y = 0; y < this.size; y++) {
				if (this.grid[y][x] == square)
					columnStreak++;
				else
					columnStreak = 0;
				if (this.grid[x][y] == square)
					rowStreak++;
				else
					rowStreak = 0;
				if (columnStreak >= WINNING_STREAK ||
				    rowStreak >= WINNING_STREAK)
					return true;
			}
		}
		return false;
	}

	/**
	 * Checks wether the board has consecutively
	 * WINNING_STREAK pieces in any diagonal line.
	 * @param square the type of piece.
	 * @return a boolean indicating if such is the case
	 */
	private boolean DiagonalWin(Square square) {
		// Check SouthWest half for a positively traversing diagonal
		for (int i = 0; i <= this.size - WINNING_STREAK; i++)
			if (checkDiagonal(square, 0, i, true))
				return true;
		// Check NorthEast half for a positively traversing diagonal
		for (int i = 0; i <= this.size - WINNING_STREAK; i++)
			if (checkDiagonal(square, i, 0, true))
				return true;
		// Check NorthWest half for a negatively traversing diagonal
		for (int i = WINNING_STREAK - 1; i < this.size; i++)
			if (checkDiagonal(square, 0, i, false))
				return true;
		// Check SouthEast half for a negatively traversing diagonal
		for (int i = 0; i <= this.size - WINNING_STREAK; i++)
			if (checkDiagonal(square, i, this.size - 1, false))
				return true;
		return false;
	}

	/**
	 * Checks wether the board has WINNING_STREAK pieces in a particular
	 * diagonal line.
	 * @param square the type of piece.
	 * @param startX the starting column of the diagonal traversion
	 * @param startY the starting row of the diagonal traversion
	 * @param traversePositively indicated wether the diagonal traverses
	 * positively
	 * @return a boolean indicating if this diagonal has consecutively
	 * WINNING_STREAK pieces in it.
	 */
	private boolean checkDiagonal(
			Square square,
			int startX, int startY,
			boolean traversePositively) {
		int dy = (traversePositively) ? 1 : -1;
		int diagonalStreak = 0;
		for (int x = startX, y = startY;
				x < this.size &&
				0 <= y &&
				y < this.size; x++, y += dy) {
			if (this.grid[y][x] == square)
				diagonalStreak++;
			else
				diagonalStreak = 0;
			if (diagonalStreak >= WINNING_STREAK)
				return true;
		}
		return false;
	}

}
