package se.nackademin;

import java.util.List;

public abstract class GenericPlay implements Runnable {
	protected FileIO fio;
	protected List<Player> playerList;
	protected Board board;
}
