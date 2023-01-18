package se.nackademin;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BoardTest {
	@Test
	public void tryToPlaceFreeSpaceShouldReturnSUCCESS() {
		Board board = new Board(1);
		Board.PlacementState state = board.tryToPlace(0,0);
		assertEquals(state, Board.PlacementState.SUCCESS);
	}

	@Test
	public void tryToPlaceTakenSpaceShouldReturnOCCUPIED() {
		Board board = new Board(1);
		board.tryToPlace(0,0);
		Board.PlacementState state = board.tryToPlace(0,0);
		assertEquals(state, Board.PlacementState.OCCUPIED);
	}

	@Test
	public void tryToPlaceOutOfBoundsShouldReturnOUT_OF_BOUNDS() {
		Board board = new Board(1);
		Board.PlacementState state = board.tryToPlace(1,0);
		assertEquals(state, Board.PlacementState.OUT_OF_BOUNDS);
	}

	@Test
	public void hasWonHorizontalShouldReturnTrue() {
		Board board = new Board(4);
		board.tryToPlace(0,0);
		board.tryToPlace(1,0);
		board.tryToPlace(2,0);
		board.tryToPlace(3,0);
		boolean won = board.hasWon();
		assertTrue(won);
	}

	@Test
	public void hasWonVerticalShouldReturnTrue() {
		Board board = new Board(4);
		board.tryToPlace(0,0);
		board.tryToPlace(0,1);
		board.tryToPlace(0,2);
		board.tryToPlace(0,3);
		boolean won = board.hasWon();
		assertTrue(won);
	}

	@Test
	public void hasWonPosDiagShouldReturnTrue() {
		Board board = new Board(4);
		board.tryToPlace(0,0);
		board.tryToPlace(1,1);
		board.tryToPlace(2,2);
		board.tryToPlace(3,3);
		boolean won = board.hasWon();
		assertTrue(won);
	}

	@Test
	public void hasWonNegDiagShouldReturnTrue() {
		Board board = new Board(4);
		board.tryToPlace(3,0);
		board.tryToPlace(2,1);
		board.tryToPlace(1,2);
		board.tryToPlace(0,3);
		boolean won = board.hasWon();
		assertTrue(won);
	}

	@Test
	public void hasWonShouldReturnFalse() {
		Board board = new Board(4);
		board.tryToPlace(0,0);
		board.tryToPlace(1,1);
		board.tryToPlace(2,2);
		boolean won = board.hasWon();
		assertFalse(won);
	}

}
