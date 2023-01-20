package se.nackademin;

import se.nackademin.Board.Square;

public class Turn {
	private Square piece;
	private int row;
	private int col;
	public Square getPiece() { return this.piece; }
	public int getRow() { return this.row; }
	public int getCol() { return this.col; }
	Turn(Square piece, int row, int col) {
		this.piece = piece;
		this.row = row;
		this.col = col;
	}
}
