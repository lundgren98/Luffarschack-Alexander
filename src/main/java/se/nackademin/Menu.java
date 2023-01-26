package se.nackademin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A menu giving the option to play a game or watch a previous one.
 */
public class Menu implements Runnable {
	class Choice {
		public GenericPlay play;
		public String description;
		public Choice(GenericPlay play, String description) {
			this.play = play;
			this.description = description;
		}
	}

	private Scanner sc = new Scanner(System.in);
	private Play game = new Play();
	private ShowReplay replay = new ShowReplay();
	private List<Choice> choices = new ArrayList<Choice>();
	Menu() {
		choices.add(new Choice(game, "New Game"));
		choices.add(new Choice(replay, "Watch a Previous Game"));
	}
	/**
	 * Start the menu.
	 */
	public void run() {
		showStats();
		System.out.println(getChoiceDescriptions());
		selectChoices();
	}
	private String getChoiceDescriptions() {
		String descriptions = "";
		for (int i = 0; i < choices.size(); i++) {
			descriptions += Integer.toString(i + 1) + "\t" + choices.get(i).description + "\n";
		}
		return descriptions;
	}
	private void selectChoices() {
		String input;
		do {
			input = sc.nextLine();
		} while (!isValidInput(input));
		choices.get(Integer.parseInt(input) - 1).play.run();
	}
	private boolean isValidInput(String input) {
		try {
			int i = Integer.parseInt(input);
			choices.get(i - 1);
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
			data = fio.readStatsFromFile();
		} catch (IOException e) {
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
		System.out.printf("Circle wins: %d out of %d\n", circleWins, totalNumberOfGames);
	}
}
