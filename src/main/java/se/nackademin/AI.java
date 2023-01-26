package se.nackademin;

import se.nackademin.Board.Square;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.List;
import java.util.Random;

/**
 * A computer player for the game.
 */
public class AI implements Player {

	private Square me;
	private Square opponent;
	private Streak longestStreak;

	/**
	 * Create a computer player.
	 * @param piece the type of piece the computer should play with.
	 * Assumes the opponent plays with the opposite piece.
	 */
	public AI(Square piece) {
		this.me = piece;
		this.opponent = (piece == Square.CIRCLE)
			? Square.CROSS
			: Square.CIRCLE;
	}

	/**
	 * Ask the AI for coordinates to place on the board.
	 * @param board the board to be played on.
	 * @return The {@link se.nackademin.Point} the AI placed the board.
	 */
	public Point selectPlacement(Board board) {
		this.calculateStreaks(board);
		if (this.longestStreak.length < 0)
			return getRandomCordinates(board);
		return this.longestStreak.edges.get(0);
	}

	private class Streak implements Comparable<Streak> {
		private int length;
		private List<Point> edges;

		public int getLength() { return this.length; }

		public int compareTo(Streak other) {
			return this.getLength() - other.getLength();
		}

		public Streak() {
			this.length = -1;
			this.edges = new ArrayList<Point>();
		}

		public Streak(Point p1, Point p2, int max) {
			if (!p1.isInBounds(max) || !p2.isInBounds(max)) {
				this.length = -1;
				return;
			}
			if (p1.equals(p2)) {
				this.length = 0;
				this.edges = p1.getNeighbours()
					.stream()
					.filter(p -> p.isInBounds(max))
					.toList();
				return;
			}
			this.length = p1.distance(p2);
			Point dp = p1.direction(p2);
			List<Point> e = new ArrayList<Point>();
			e.add(p1.minus(dp));
			e.add(p2.plus(dp));
			this.edges = e.stream()
				.filter(p -> p.isInBounds(max))
				.toList();

		}

		public void filterEdges(Predicate<? super Point> predicate) {
			this.edges = this.edges.stream()
				.filter(predicate)
				.toList();
		}
	}

	private void calculateStreaks(Board board) {
		this.longestStreak = new Streak();
		int size = board.getSize();
		Point rowPoint2     = new Point();
		Point colPoint2     = new Point();
		Point posDiagPoint2 = new Point();
		Point negDiagPoint2 = new Point();
		Point rowOffset     = new Point(-1, 0);
		Point colOffset     = new Point(0 ,-1);
		Point posDiagOffset = new Point(-1,-1);
		Point negDiagOffset = new Point(-1,+1);
		for (int y = 0; y <= size; y++) {
			Point rowPoint1     = new Point();
			Point colPoint1     = new Point();
			Point posDiagPoint1 = new Point();
			Point negDiagPoint1 = new Point();
			int x;
			int posDiagY = size - y;
			for (x = 0; x <= size; x++, posDiagY++) {
				Point currentRowPoint     = new Point(x, y);
				Point currentColPoint     = new Point(y, x);
				Point currentPosDiagPoint = new Point(x, posDiagY);
				Point currentNegDiagPoint = new Point(x, size - posDiagY);
				this.updateStreak(currentRowPoint,
						rowPoint1, rowPoint2,
						rowOffset, board);
				this.updateStreak(currentColPoint,
						colPoint1, colPoint2,
						colOffset, board);
				this.updateStreak(currentPosDiagPoint,
						posDiagPoint1, posDiagPoint2,
						posDiagOffset, board);
				this.updateStreak(currentNegDiagPoint,
						negDiagPoint1, negDiagPoint2,
						negDiagOffset, board);
			}
		}
	}

	private void updateStreak(Point currentPoint,
			Point p1, Point p2,
			Point offset,
			Board board)
	{
		int x = currentPoint.x;
		int y = currentPoint.y;
		int size = board.getSize();
		if (board.getSquare(x, y) == this.opponent) {
			if (!p1.isInBounds(size))
				p1.set(x,y);
			return;
		}
		if (board.getSquare(x, y) == this.me) {
			p1.invalidate();
			p2.invalidate();
			return;
		}
		if (!p1.isInBounds(size))
			return;
		p2.set(currentPoint.plus(offset));
		Streak localStreak = new Streak(p1, p2, size);
		localStreak.filterEdges(p -> board.getSquare(p.x, p.y) == Square.AVAILABLE);
		if (this.longestStreak.compareTo(localStreak) < 0 &&
			localStreak.edges.size() > 0)
			this.longestStreak = localStreak;
		p1.invalidate();
	}

	private Point getRandomCordinates(Board board) {
		Random rand = new Random();
		int x = rand.nextInt(board.getSize());
		int y = rand.nextInt(board.getSize());
		return new Point(x, y);
	}
}
