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

	/**
	 * Create a new game between a Human and an AI.
	 */
	public Play() {
		this.fio = new FileIO();
		this.board = new Board(5);
		this.playerList = new ArrayList<Player>();
		this.playerList.add(new Human());
		this.playerList.add(new AI(Square.CROSS));
	}

	/**
	 * Indicates the state game.
	 * {@see #FALSE}
	 * {@see #CIRCLE}
	 * {@see #CROSS}
	 * {@see #TIE}
	 */
	public enum PlayerWin {
		/**
		 * No one has won.
		 */
		FALSE,
		/**
		 * Circle has won.
		 */
		CIRCLE,
		/**
		 * Cross has won.
		 */
		CROSS,
		/**
		 * There is a tie between the players.
		 */
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
			PlayerWin pw = aPlayerTurn(player, square);
			if (pw != PlayerWin.FALSE)
				return pw;
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
	private PlayerWin aPlayerTurn(Player player, Square square) {
		int[] pos;
		PlacementState placement;
		System.out.println(this.board);
		do {
			pos = player.selectPlacement(this.board);
			placement = this.board.tryToPlace(pos[0], pos[1], square);
		} while (placement != PlacementState.SUCCESS);
		this.turns.add(new Turn(square, pos[1], pos[0]));
		if (this.board.isTied())
			return PlayerWin.TIE;
		if (this.board.hasWon(square))
			return (square == Square.CIRCLE)
				? PlayerWin.CIRCLE
				: PlayerWin.CROSS;
		return PlayerWin.FALSE;
	}
}
