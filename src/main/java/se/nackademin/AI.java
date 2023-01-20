package se.nackademin;

import java.util.Random;
import java.util.stream.IntStream;

public class AI implements Player {
	public int[] selectPlacement(Board board) {
		return getRandomCordinates(board);
	}
	private int[] getRandomCordinates(Board board) {
		Random rand = new Random();
		int x = rand.nextInt(board.getSize());
		int y = rand.nextInt(board.getSize());
		return new int[] { x, y };
	}
}
