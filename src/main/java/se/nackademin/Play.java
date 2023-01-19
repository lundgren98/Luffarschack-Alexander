package se.nackademin;

import java.util.ArrayList;

import se.nackademin.Board.PlacementState;
import se.nackademin.Board.Square;

public class Play extends GenericPlay {

	public Play() {
		this.board = new Board(5);
		this.playerList = new ArrayList<Player>();
		this.playerList.add(new Human());
		this.playerList.add(new Human());
	}

	public void run() {
		boolean firstPlayerTurn = true;
		int[] pos = { -1, -1 };
		while (true) {
			for (Player player : this.playerList) {
				System.out.println(this.board);
				Square square = (firstPlayerTurn) ? Square.CIRCLE : Square.CROSS;
				firstPlayerTurn = !firstPlayerTurn;
				do {
					pos = player.selectPlacement();
				} while (this.board.tryToPlace(
						pos[0],
						pos[1],
						square) != PlacementState.SUCCESS);
			}
		}
	}
}
