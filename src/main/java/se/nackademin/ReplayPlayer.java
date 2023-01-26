package se.nackademin;

import java.util.List;
import java.util.Iterator;
import java.util.Scanner;

/**
 * A player who only plays the games given to him by a list of {@link se.nackademin.Turn}s.
 */
public class ReplayPlayer implements Player {
	Scanner sc = new Scanner(System.in);
	private Iterator<Turn> turns;
	/**
	 * A player who only plays what you tell him to play.
	 * @param turns the turns he should play.
	 * @see se.nackademin.Turn
	 */
	public ReplayPlayer(List<Turn> turns) {
		this.turns = turns.iterator();
	}

	/**
	 * Gives the cordinates to the play he wants to do.
	 * @return an integer array on the form [column, row].
	 */
	public Point selectPlacement(Board board) {
		Turn turn = turns.next();
		sc.nextLine();
		return new Point(turn.getCol(), turn.getRow());
	}
}
