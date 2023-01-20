package se.nackademin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.nackademin.Board.PlacementState;
import se.nackademin.Board.Square;

/**
 * A class to play a game of four in a row.
 */
public class Play extends GenericPlay {

	public Play() {
		this.board = new Board(5);
		this.playerList = new ArrayList<Player>();
		this.playerList.add(new AI());
		this.playerList.add(new AI());
	}

	public enum PlayerWin {
		FALSE,
		CIRCLE,
		CROSS,
		TIE
	}

	private List<Turn> turns = new ArrayList<Turn>();
	private int numberOfTurns = 0;

	/**
	 * Run the game
	 */
	public void run() {
		PlayerWin pw = PlayerWin.FALSE;
		while ((pw = playersTakeTurns()) == PlayerWin.FALSE);
		try {
			fio.saveToFile(pw, numberOfTurns);
			fio.saveToFile(turns);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Let each player take a turn
	 * @return a boolean indicating in the game should continue.
	 */
	private PlayerWin playersTakeTurns() {
		this.numberOfTurns++;
		boolean firstPlayerTurn = true;
		for (Player player : this.playerList) {
			Square square = (firstPlayerTurn)
				? Square.CIRCLE
				: Square.CROSS;
			if (aPlayerTurn(player, square))
				return (square == Square.CIRCLE)
					? PlayerWin.CIRCLE
					: PlayerWin.CROSS;
			firstPlayerTurn = !firstPlayerTurn;
		}
		return PlayerWin.FALSE;
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
		this.turns.add(new Turn(square, pos[1], pos[0]));
		return this.board.hasWon(square);
	}
}
