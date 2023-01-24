package se.nackademin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A menu giving the option to play a game or watch a previous one.
 */
public class Menu implements Runnable {
	Scanner sc = new Scanner(System.in);
	private Play game = new Play();
	private showReplay replay = new showReplay();
	private List<GenericPlay> choiceList = new ArrayList<GenericPlay>();
	private List<String> choiceNameList = new ArrayList<String>();
	Menu() {
		choiceList.add(game);
		choiceNameList.add("New Game");
		choiceList.add(replay);
		choiceNameList.add("Watch a Previous Game");
	}
	/**
	 * Start the menu.
	 */
	public void run() {
		showStats();
		System.out.println(showChoices());
		selectChoices();
	}
	private String showChoices() {
		String retVal = "";
		for (int i = 0; i < choiceNameList.size(); i++) {
			retVal += Integer.toString(i + 1) + "\t" + choiceNameList.get(i) + "\n";
		}
		return retVal;
	}
	private void selectChoices() {
		String input;
		do {
			input = sc.nextLine();
		} while (!inputIsValid(input));
		choiceList.get(Integer.parseInt(input) - 1).run();
	}
	private boolean inputIsValid(String input) {
		int i = 0;
		try {
			i = Integer.parseInt(input);
			choiceList.get(i - 1);
		} catch (NumberFormatException e) {
			return false;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}
	private void showStats() {
		FileIO fio = new FileIO();
		List<String[]> data;
		try {
			data = fio.ReadStatsFromFIle();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		int totalNumberOfGames = data.size();
		int circleWins = data.stream()
			.map(arr -> arr[0])
			.filter(s -> s.equals("CIRCLE"))
			.toList()
			.size();
		double avg = data.stream()
			.mapToInt(arr -> Integer.parseInt(arr[1]))
			.average()
			.orElse(-1.0);
		System.out.printf("Average moves per game: %f\n", avg);
		System.out.printf("Circle wins: %d out of %d\n",
				circleWins, totalNumberOfGames);
	}
}
