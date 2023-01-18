package se.nackademin;

public class showReplay extends GenericPlay {
	public void run() {
		this.board = new Board(5);
		System.out.println(this.board);
	}
}
