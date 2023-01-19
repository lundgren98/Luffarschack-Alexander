package se.nackademin;

import java.util.ArrayList;

import se.nackademin.Board.PlacementState;
import se.nackademin.Board.Square;

/**
 * A class to play a game of four in a row.
 */
public class Play extends GenericPlay {

	public Play() {
		this.board = new Board(5);
		this.playerList = new ArrayList<Player>();
		this.playerList.add(new Human());
		this.playerList.add(new Human());
	}

	/**
	 * Run the game
	 */
	public void run() {
		while (playersTakeTurns());
	}

	/**
	 * Let each player take a turn
	 * @return a boolean indicating in the game should continue.
	 */
	private boolean playersTakeTurns() {
		boolean firstPlayerTurn = true;
		for (Player player : this.playerList) {
			Square square = (firstPlayerTurn) ? Square.CIRCLE : Square.CROSS;
			if (aPlayerTurn(player, square))
				return false;
			firstPlayerTurn = !firstPlayerTurn;
		}
		return true;
	}

	/**
	 * Let one player play their turn.
	 * @param player the player whose turn it is.
	 * @param square the piece the player places.
	 * @return a boolean indicating if the player has won.
	 */
	private boolean aPlayerTurn(Player player, Square square) {
		int[] pos;
		PlacementState placement;
		System.out.println(this.board);
		do {
			pos = player.selectPlacement();
			placement = this.board.tryToPlace(pos[0], pos[1], square);
		} while (placement != PlacementState.SUCCESS);
		return this.board.hasWon(square);
	}
}
