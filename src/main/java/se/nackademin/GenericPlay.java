package se.nackademin;

import java.util.List;

/**
 * A generic class for a game to be played.
 */
public abstract class GenericPlay implements Runnable {
	/**
	 * A game should be able to read and/or write about how the game went.
	 * {@see se.nackademin.FileIO}
	 */
	protected FileIO fio;
	/**
	 * Two players need to be able to play against eachother.
	 * {@see se.nackademin.Player}
	 */
	protected List<Player> playerList;
	/**
	 * A game board for the game to be played on.
	 * {@see se.nackademin.Board}
	 */
	protected Board board;
}
