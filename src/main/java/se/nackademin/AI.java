package se.nackademin;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.lang.Math;

import se.nackademin.Board.Square;

public class AI implements Player {

	private Square me;
	private Square opponent;
	private Streak longestStreak;

	public AI(Square piece) {
		this.me = piece;
		this.opponent = (piece == Square.CIRCLE)
			? Square.CROSS
			: Square.CIRCLE;
	}

	public int[] selectPlacement(Board board) {
		this.calculateStreaks(board);
		if (this.longestStreak.length < 0)
			return getRandomCordinates(board);
		Point p = this.longestStreak.edges.get(0);
		return new int[] { p.x, p.y };
	}

	private class Point {

		public int x;
		public int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public boolean isInBounds(int lower, int upper) {
			return (
				this.x >= lower &&
				this.y >= lower &&
				this.x < upper &&
				this.y < upper
				);
		}

		public boolean isInBounds(int upper) {
			return this.isInBounds(0, upper);
		}

		public int distance(Point other) {
			return Math.max(Math.abs(this.x - other.x),
					Math.abs(this.y - other.y));
		}

		public Point plus(Point other) {
			return new Point(this.x + other.x, this.y + other.y);
		}

		public Point minus(Point other) {
			return new Point(this.x - other.x, this.y - other.y);
		}

		public Point direction(Point other) {
			int dx = (this.x == other.x) ? 0
				: (this.x < other.x) ? 1 : -1;
			int dy = (this.y == other.y) ? 0
				: (this.y < other.y) ? 1 : -1;
			return new Point(dx, dy);
		}

		public boolean equals(Point other) {
			return (this.x == other.x && this.y == other.y);
		}

		public List<Point> getNeighbours() {
			List<Point> retVal = new ArrayList<Point>();
			for (int y = -1; y <= 1; y++) {
				for (int x = -1; x <= 1; x++) {
					if (x == 0 && y == 0)
						continue;
					retVal.add(this.plus(new Point(x,y)));
				}
			}
			return retVal;
		}
	}
	private class Streak implements Comparable<Streak> {
		private int length;
		private List<Point> edges;

		public int getLength() { return this.length; }
		public List<Point> getEdges() { return this.edges; }

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
		this.calculateHorizontalStreaks(board);
		this.calculateVerticalStreaks(board);
		this.calculatePosDiagStreaks(board);
		this.calculateNegDiagStreaks(board);
	}

	private void calculateHorizontalStreaks(Board board) {
		int size = board.getSize();
		Point p1 = new Point(-1,-1);
		Point p2 = new Point(-1,-1);
		Streak localStreak;
		for (int y = 0; y < size; y++) {
			p1 = new Point(-1,-1);
			for (int x = 0; x <= size; x++) {
				if (board.getSquare(x, y) == this.opponent) {
					if (!p1.isInBounds(size))
						p1 = new Point(x,y);
					continue;
				}
				if (board.getSquare(x, y) == this.me) {
					p1 = new Point(-1,-1);
					p2 = new Point(-1,-1);
					continue;
				}
				if (!p1.isInBounds(size))
					continue;
				p2 = new Point(x - 1, y);
				localStreak = new Streak(p1, p2, size);
				localStreak.filterEdges(p -> board.getSquare(p.x, p.y) == Square.AVAILABLE);
				if (this.longestStreak.compareTo(localStreak) < 0 &&
					localStreak.edges.size() > 0)
					this.longestStreak = localStreak;
				p1 = new Point(-1,-1);
			}
		}
	}

	private void calculateVerticalStreaks(Board board) {
		int size = board.getSize();
		Point p1 = new Point(-1,-1);
		Point p2 = new Point(-1,-1);
		Streak localStreak;
		for (int x = 0; x < size; x++) {
			p1 = new Point(-1,-1);
			for (int y = 0; y <= size; y++) {
				if (board.getSquare(x, y) == this.opponent) {
					if (!p1.isInBounds(size))
						p1 = new Point(x,y);
					continue;
				}
				if (board.getSquare(x, y) == this.me) {
					p1 = new Point(-1,-1);
					p2 = new Point(-1,-1);
					continue;
				}
				if (!p1.isInBounds(size))
					continue;
				p2 = new Point(x, y - 1);
				localStreak = new Streak(p1, p2, size);
				localStreak.filterEdges(p -> board.getSquare(p.x, p.y) == Square.AVAILABLE);
				if (this.longestStreak.compareTo(localStreak) < 0 &&
					localStreak.edges.size() > 0)
					this.longestStreak = localStreak;
				p1 = new Point(-1,-1);
			}
		}
	}

	private void calculatePosDiagStreaks(Board board) {
		int size = board.getSize();
		Point p1 = new Point(-1,-1);
		Point p2 = new Point(-1,-1);
		Streak localStreak;
		for (int y = size - 1; y >= 0; y--) {
			p1 = new Point(-1,-1);
			int x;
			for (x = 0; x <= size && y <= size; x++, y++) {
				if (board.getSquare(x, y) == this.opponent) {
					if (!p1.isInBounds(size))
						p1 = new Point(x,y);
					continue;
				}
				if (board.getSquare(x, y) == this.me) {
					p1 = new Point(-1,-1);
					p2 = new Point(-1,-1);
					continue;
				}
				if (!p1.isInBounds(size))
					continue;
				p2 = new Point(x - 1, y - 1);
				localStreak = new Streak(p1, p2, size);
				localStreak.filterEdges(p -> board.getSquare(p.x, p.y) == Square.AVAILABLE);
				if (this.longestStreak.compareTo(localStreak) < 0 &&
					localStreak.edges.size() > 0)
					this.longestStreak = localStreak;
				p1 = new Point(-1,-1);
			}
			y -= x;
		}
	}

	private void calculateNegDiagStreaks(Board board) {
		int size = board.getSize();
		Point p1 = new Point(-1,-1);
		Point p2 = new Point(-1,-1);
		Streak localStreak;
		for (int y = 0; y < size; y++) {
			p1 = new Point(-1,-1);
			int x;
			for (x = 0; x <= size && y >= -1; x++, y--) {
				if (board.getSquare(x, y) == this.opponent) {
					if (!p1.isInBounds(size))
						p1 = new Point(x,y);
					continue;
				}
				if (board.getSquare(x, y) == this.me) {
					p1 = new Point(-1,-1);
					p2 = new Point(-1,-1);
					continue;
				}
				if (!p1.isInBounds(size))
					continue;
				p2 = new Point(x - 1, y + 1);
				localStreak = new Streak(p1, p2, size);
				localStreak.filterEdges(p -> board.getSquare(p.x, p.y) == Square.AVAILABLE);
				if (this.longestStreak.compareTo(localStreak) < 0 &&
					localStreak.edges.size() > 0)
					this.longestStreak = localStreak;
				p1 = new Point(-1,-1);
			}
			y += x;
		}
	}

	private int[] getRandomCordinates(Board board) {
		Random rand = new Random();
		int x = rand.nextInt(board.getSize());
		int y = rand.nextInt(board.getSize());
		return new int[] { x, y };
	}
}
