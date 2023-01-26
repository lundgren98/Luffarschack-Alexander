package se.nackademin;

import se.nackademin.Board.Square;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class AITest {

	@Test
	public void selectPlacementShouldReturnOnEmptyBoard() {
		Board board = new Board(5);
		AI ai = new AI(Square.CIRCLE);
		Point got = ai.selectPlacement(board);
		Point expected = new Point();
		assertFalse(expected.equals(got));
	}

	@Test
	public void selectPlacementShouldReturnSomethingElseOnSinglePieceBoard() {
		Board board = new Board(5);
		board.tryToPlace(2, 2, Square.CROSS);
		AI ai = new AI(Square.CIRCLE);
		Point got = ai.selectPlacement(board);
		Point expected = new Point(2, 2);
		assertFalse(expected.equals(got));
	}

	@Test
	public void selectPlacementShouldStopHorizontalStreak() {
		Board board = new Board(5);
		board.tryToPlace(0,0, Square.CROSS);
		board.tryToPlace(1,0, Square.CROSS);
		board.tryToPlace(2,0, Square.CROSS);
		AI ai = new AI(Square.CIRCLE);
		Point got = ai.selectPlacement(board);
		Point expected = new Point(3, 0);
		assertTrue(expected.equals(got));
	}

	@Test
	public void selectPlacementShouldStopHorizontalStreakAtEnd() {
		Board board = new Board(5);
		board.tryToPlace(2,0, Square.CROSS);
		board.tryToPlace(3,0, Square.CROSS);
		board.tryToPlace(4,0, Square.CROSS);
		AI ai = new AI(Square.CIRCLE);
		Point got = ai.selectPlacement(board);
		Point expected = new Point(1, 0);
		assertTrue(expected.equals(got));
	}

	@Test
	public void selectPlacementShouldStopLargestHorizontalStreak() {
		Board board = new Board(5);
		board.tryToPlace(0,0, Square.CROSS);
		board.tryToPlace(1,0, Square.CROSS);
		board.tryToPlace(0,1, Square.CROSS);
		board.tryToPlace(1,1, Square.CROSS);
		board.tryToPlace(2,1, Square.CROSS);
		AI ai = new AI(Square.CIRCLE);
		Point got = ai.selectPlacement(board);
		Point expected = new Point(3, 1);
		assertTrue(expected.equals(got));
	}

	@Test
	public void selectPlacementShouldStopLargestRelevantHorizontalStreak() {
		Board board = new Board(5);
		board.tryToPlace(0,0, Square.CROSS);
		board.tryToPlace(1,0, Square.CROSS);
		board.tryToPlace(0,2, Square.CROSS);
		board.tryToPlace(1,2, Square.CROSS);
		board.tryToPlace(2,2, Square.CROSS);
		board.tryToPlace(3,2, Square.CIRCLE);
		AI ai = new AI(Square.CIRCLE);
		Point got = ai.selectPlacement(board);
		Point expected = new Point(2, 0);
		assertTrue(expected.equals(got));
	}

	@Test
	public void selectPlacementShouldStopVerticalStreak() {
		Board board = new Board(5);
		board.tryToPlace(0,0, Square.CROSS);
		board.tryToPlace(0,1, Square.CROSS);
		board.tryToPlace(0,2, Square.CROSS);
		AI ai = new AI(Square.CIRCLE);
		Point got = ai.selectPlacement(board);
		Point expected = new Point(0, 3);
		assertTrue(expected.equals(got));
	}

	@Test
	public void selectPlacementShouldStopVerticalStreakAtEnd() {
		Board board = new Board(5);
		board.tryToPlace(0,2, Square.CROSS);
		board.tryToPlace(0,3, Square.CROSS);
		board.tryToPlace(0,4, Square.CROSS);
		AI ai = new AI(Square.CIRCLE);
		Point got = ai.selectPlacement(board);
		Point expected = new Point(0, 1);
		assertTrue(expected.equals(got));
	}

	@Test
	public void selectPlacementShouldStopPosDiagStreak() {
		Board board = new Board(5);
		board.tryToPlace(0,0, Square.CROSS);
		board.tryToPlace(1,1, Square.CROSS);
		board.tryToPlace(2,2, Square.CROSS);
		AI ai = new AI(Square.CIRCLE);
		Point got = ai.selectPlacement(board);
		Point expected = new Point(3, 3);
		assertTrue(expected.equals(got));
	}

	@Test
	public void selectPlacementShouldStopPosDiagStreakAtEnd() {
		Board board = new Board(5);
		board.tryToPlace(2,2, Square.CROSS);
		board.tryToPlace(3,3, Square.CROSS);
		board.tryToPlace(4,4, Square.CROSS);
		AI ai = new AI(Square.CIRCLE);
		Point got = ai.selectPlacement(board);
		Point expected = new Point(1, 1);
		assertTrue(expected.equals(got));
	}

	@Test
	public void selectPlacementShouldStopPosDiagStreakAtEndWithOwnOccupyingPiece() {
		Board board = new Board(5);
		board.tryToPlace(1,1, Square.CROSS);
		board.tryToPlace(2,2, Square.CROSS);
		board.tryToPlace(3,3, Square.CROSS);
		board.tryToPlace(4,4, Square.CIRCLE);
		AI ai = new AI(Square.CIRCLE);
		Point got = ai.selectPlacement(board);
		Point expected = new Point(0, 0);
		assertTrue(expected.equals(got));
	}

	@Test
	public void selectPlacementShouldStopNegDiagStreak() {
		Board board = new Board(5);
		board.tryToPlace(0,4, Square.CROSS);
		board.tryToPlace(1,3, Square.CROSS);
		board.tryToPlace(2,2, Square.CROSS);
		AI ai = new AI(Square.CIRCLE);
		Point got = ai.selectPlacement(board);
		Point expected = new Point(3, 1);
		assertTrue(expected.equals(got));
	}

	@Test
	public void selectPlacementShouldStopNegDiagStreakAtEnd() {
		Board board = new Board(5);
		board.tryToPlace(2,2, Square.CROSS);
		board.tryToPlace(3,1, Square.CROSS);
		board.tryToPlace(4,0, Square.CROSS);
		AI ai = new AI(Square.CIRCLE);
		Point got = ai.selectPlacement(board);
		Point expected = new Point(1, 3);
		assertTrue(expected.equals(got));
	}

	@Test
	public void selectPlacementShouldStopNegDiagStreakWithOwnOccupyingPiece() {
		Board board = new Board(5);
		board.tryToPlace(0,4, Square.CIRCLE);
		board.tryToPlace(1,3, Square.CROSS);
		board.tryToPlace(2,2, Square.CROSS);
		board.tryToPlace(3,1, Square.CROSS);
		AI ai = new AI(Square.CIRCLE);
		Point got = ai.selectPlacement(board);
		Point expected = new Point(4, 0);
		assertTrue(expected.equals(got));
	}
}
