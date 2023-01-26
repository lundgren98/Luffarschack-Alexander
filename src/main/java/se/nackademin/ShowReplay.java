package se.nackademin;

import se.nackademin.Board.Square;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A game played between two {@link se.nackademin.ReplayPlayer}s.
 */
public class ShowReplay extends GenericPlay {
	/**
	 * A game played between two {@link se.nackademin.ReplayPlayer}s.
	 */
	public ShowReplay() {
		this.fio = new FileIO();
		this.board = new Board(5);
		this.playerList = new ArrayList<Player>();
	}

	/**
	 * Run the replay.
	 */
	public void run() {
		if (!this.preparePlayers())
			return;
		try {
			while (true) {
				this.playersTakeTurns();
			}
		} catch (NoSuchElementException e) { }
	}

	private void playersTakeTurns() {
		boolean firstPlayerTurn = true;
		for (Player player : this.playerList) {
			Square square = (firstPlayerTurn)
				? Square.CIRCLE
				: Square.CROSS;
			Point pos = player.selectPlacement(this.board);
			this.board.tryToPlace(pos.x, pos.y, square);
			firstPlayerTurn = !firstPlayerTurn;
			System.out.println(this.board);
		}
	}
	private boolean preparePlayers() {
		List<Turn> allTurns;
		try {
			allTurns = fio.readTurnsFromFile();
		} catch (IOException e) {
			System.out.println("Could not read game data.");
			return false;
		}
		List<Turn> firstPlayerTurns = allTurns.stream()
			.filter(t -> t.getPiece() == Square.CIRCLE)
			.toList();
		List<Turn> secondPlayerTurns = allTurns.stream()
			.filter(t -> t.getPiece() == Square.CROSS)
			.toList();
		this.playerList.add(new ReplayPlayer(firstPlayerTurns));
		this.playerList.add(new ReplayPlayer(secondPlayerTurns));
		return true;
	}
}
