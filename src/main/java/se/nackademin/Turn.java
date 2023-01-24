package se.nackademin;

import se.nackademin.Board.Square;

/**
 * A class indicating a turn of a game.
 */
public class Turn {
	private Square piece;
	private int row;
	private int col;
	/**
	 * @return the piece wich was played this turn.
	 */
	public Square getPiece() { return this.piece; }

	/**
	 * @return the row on wich a piece was placed on this turn.
	 */
	public int getRow() { return this.row; }

	/**
	 * @return the column on wich a piece was placed on this turn.
	 */
	public int getCol() { return this.col; }

	/**
	 * A piece of history.
	 * @param piece the piece which was played this turn.
	 * @param row the row which the piece was placed on.
	 * @param col the column which the piece was placed on.
	 */
	Turn(Square piece, int row, int col) {
		this.piece = piece;
		this.row = row;
		this.col = col;
	}
}
