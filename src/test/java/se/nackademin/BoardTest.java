package se.nackademin;

import se.nackademin.Board.Square;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class BoardTest {
	@Test
	public void tryToPlaceFreeSpaceShouldReturnSUCCESS() {
		Board board = new Board(1);
		Board.PlacementState state = board.tryToPlace(0,0, Square.CIRCLE);
		assertEquals(state, Board.PlacementState.SUCCESS);
	}

	@Test
	public void tryToPlaceTakenSpaceShouldReturnOCCUPIED() {
		Board board = new Board(1);
		board.tryToPlace(0,0, Square.CIRCLE);
		Board.PlacementState state = board.tryToPlace(0,0, Square.CIRCLE);
		assertEquals(state, Board.PlacementState.OCCUPIED);
	}

	@Test
	public void tryToPlaceOutOfBoundsShouldReturnOUT_OF_BOUNDS() {
		Board board = new Board(1);
		Board.PlacementState state = board.tryToPlace(1,0, Square.CIRCLE);
		assertEquals(state, Board.PlacementState.OUT_OF_BOUNDS);
	}

	@Test
	public void hasWonHorizontalShouldReturnTrue() {
		Board board = new Board(4);
		board.tryToPlace(0,0, Square.CIRCLE);
		board.tryToPlace(1,0, Square.CIRCLE);
		board.tryToPlace(2,0, Square.CIRCLE);
		board.tryToPlace(3,0, Square.CIRCLE);
		boolean won = board.hasWon(Square.CIRCLE);
		assertTrue(won);
	}

	@Test
	public void hasWonVerticalShouldReturnTrue() {
		Board board = new Board(4);
		board.tryToPlace(0,0, Square.CIRCLE);
		board.tryToPlace(0,1, Square.CIRCLE);
		board.tryToPlace(0,2, Square.CIRCLE);
		board.tryToPlace(0,3, Square.CIRCLE);
		boolean won = board.hasWon(Square.CIRCLE);
		assertTrue(won);
	}

	@Test
	public void hasWonPosDiagShouldReturnTrue() {
		Board board = new Board(4);
		board.tryToPlace(0,0, Square.CIRCLE);
		board.tryToPlace(1,1, Square.CIRCLE);
		board.tryToPlace(2,2, Square.CIRCLE);
		board.tryToPlace(3,3, Square.CIRCLE);
		boolean won = board.hasWon(Square.CIRCLE);
		assertTrue(won);
	}

	@Test
	public void hasWonNegDiagShouldReturnTrue() {
		Board board = new Board(4);
		board.tryToPlace(3,0, Square.CIRCLE);
		board.tryToPlace(2,1, Square.CIRCLE);
		board.tryToPlace(1,2, Square.CIRCLE);
		board.tryToPlace(0,3, Square.CIRCLE);
		boolean won = board.hasWon(Square.CIRCLE);
		assertTrue(won);
	}

	@Test
	public void hasWonShouldReturnFalse() {
		Board board = new Board(4);
		board.tryToPlace(0,0, Square.CIRCLE);
		board.tryToPlace(1,1, Square.CIRCLE);
		board.tryToPlace(2,2, Square.CIRCLE);
		board.tryToPlace(3,3, Square.CROSS);
		boolean won = board.hasWon(Square.CIRCLE);
		assertFalse(won);
	}

	@Test
	public void isTiedShouldReturnTrueOnFullBoard() {
		Board board = new Board(2);
		board.tryToPlace(0,0, Square.CIRCLE);
		board.tryToPlace(0,1, Square.CIRCLE);
		board.tryToPlace(1,0, Square.CIRCLE);
		board.tryToPlace(1,1, Square.CROSS);
		boolean tie = board.isTied();
		assertTrue(tie);
	}

	@Test
	public void isTiedShouldReturnFalseOnNotFullBoard() {
		Board board = new Board(2);
		board.tryToPlace(0,0, Square.CIRCLE);
		board.tryToPlace(0,1, Square.CIRCLE);
		board.tryToPlace(1,1, Square.CROSS);
		boolean tie = board.isTied();
		assertFalse(tie);
	}

}
