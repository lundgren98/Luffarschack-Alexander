package se.nackademin;

import java.util.ArrayList;
import java.util.List;

public class Menu implements Runnable {
	private Play game = new Play();
	private showReplay replay = new showReplay();
	private List<GenericPlay> choiceList = new ArrayList<GenericPlay>();
	Menu() {
		choiceList.add(game);
		choiceList.add(replay);
	}
	public void run() {}
	private String showChoices() { return ""; }
	private void selectChoices() {}
}
