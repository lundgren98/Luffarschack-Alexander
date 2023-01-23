package se.nackademin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import se.nackademin.Board.Square;

public class showReplay extends GenericPlay {
	public showReplay() {
		this.fio = new FileIO();
		this.board = new Board(5);
		this.playerList = new ArrayList<Player>();
	}
	public void run() {
		this.preparePlayers();
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
			int[] pos = player.selectPlacement(this.board);
			this.board.tryToPlace(pos[0], pos[1], square);
			firstPlayerTurn = !firstPlayerTurn;
			System.out.println(this.board);
		}
	}
	private void preparePlayers() {
		List<Turn> allTurns;
		try {
			allTurns = fio.ReadTurnsFromFile();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		List<Turn> firstPlayerTurns = allTurns.stream()
			.filter(t -> t.getPiece() == Square.CIRCLE)
			.toList();
		List<Turn> secondPlayerTurns = allTurns.stream()
			.filter(t -> t.getPiece() == Square.CROSS)
			.toList();
		this.playerList.add(new ReplayPlayer(firstPlayerTurns));
		this.playerList.add(new ReplayPlayer(secondPlayerTurns));
	}
}
