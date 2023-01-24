package se.nackademin;

import java.util.List;
import java.util.Iterator;

/**
 * A player who only plays the games given to him by a list of {@link se.nackademin.Turn}s.
 */
public class ReplayPlayer implements Player {
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
	public int[] selectPlacement(Board board) {
		Turn turn = turns.next();
		return new int[] { turn.getCol(), turn.getRow() };
	}
}
