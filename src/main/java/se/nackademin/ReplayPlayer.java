package se.nackademin;

import java.util.List;
import java.util.Iterator;

public class ReplayPlayer implements Player {
	private Iterator<Turn> turns;
	public ReplayPlayer(List<Turn> turns) {
		this.turns = turns.iterator();
	}
	public int[] selectPlacement(Board board) {
		Turn turn = turns.next();
		return new int[] { turn.getCol(), turn.getRow() };
	}
}
