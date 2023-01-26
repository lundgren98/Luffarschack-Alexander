package se.nackademin;

import se.nackademin.Board.Square;
import se.nackademin.Play.PlayerWin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * A helper class for reading and writing gamedata to csv files.
 */
public class FileIO {
	private final String STATS_DIR = "stats/";
	private final String STATS_FILE = STATS_DIR + "stat.csv";
	private final String SAVES_DIR = "saves/";
	private final String SAVES_FILE_PREFIX = SAVES_DIR + "save";

	/**
	 * Save who won the game and how many turns it took.
	 * @param pw a {@link se.nackademin.Play.PlayerWin} enum indicating who won.
	 * @param numberOfTurns the number of turns the game took.
	 * @throws IOException if the file couldn't be written to.
	 */
	public void saveToFile(PlayerWin pw, int numberOfTurns) throws IOException {
		Files.createDirectories(Paths.get(STATS_DIR));
		Path path = Paths.get(STATS_FILE);
		String[] vals = new String[] {
			pw.name(),
			Integer.toString(numberOfTurns)
		};
		String str = toCSV(vals);
		List<String> lines = new ArrayList<String>();
		lines.add(str);
		saveLinesToFile(lines, path);
	}

	/**
	 * Save all turns which were played.
	 * @param turns a list of {@link se.nackademin.Turn}s.
	 * @throws IOException if the file couldn't be written to.
	 */
	public void saveToFile(List<Turn> turns) throws IOException {
		Path dir = Paths.get(SAVES_DIR);
		Files.createDirectories(dir);
		int fileNumber = dir.toFile().list().length;
		Path path = Paths.get(
				SAVES_FILE_PREFIX
				+ ((fileNumber == 0) ? "" : Integer.toString(fileNumber))
				+ ".csv");
		List<String> lines = turnListToStringList(turns);
		saveLinesToFile(lines, path);
	}

	private void saveLinesToFile(List<String> lines, Path path) throws IOException {
		for (String line : lines) {
			StandardOpenOption opt = (path.toFile().exists())
				? StandardOpenOption.APPEND
				: StandardOpenOption.CREATE;
			Files.writeString(path, line, opt);
		}
	}

	private List<String> turnListToStringList(List<Turn> turns) {
		return turns.stream()
			.map(t -> turnToStringArray(t))
			.map(arr -> toCSV(arr))
			.toList();
	}

	private String[] turnToStringArray(Turn turn) {
		return new String[] {
			turn.getPiece().toString(),
			Integer.toString(turn.getCol()),
			Integer.toString(turn.getRow())
		};
	}

	private String toCSV(String[] vals) {
		return String.join(",", vals) + "\n";
	}

	/**
	 * Reads a previous game file.
	 * @return a list of {@link se.nackademin.Turn}s
	 * @throws IOException if the file couldn't be read form.
	 */
	public List<Turn> readTurnsFromFile() throws IOException, IndexOutOfBoundsException {
		Path file = Paths.get(SAVES_FILE_PREFIX + ".csv");
		return Files.readAllLines(file)
			.stream()
			.map(s -> s.split(","))
			.map(arr -> stringArrayToTurn(arr))
			.toList();
	}

	private Turn stringArrayToTurn(String[] arr) throws IndexOutOfBoundsException {
		String pieceStr = arr[0];
		int row = Integer.parseInt(arr[2]);
		int col = Integer.parseInt(arr[1]);
		Square piece = switch (pieceStr) {
			case "X" -> Square.CROSS;
			case "O" -> Square.CIRCLE;
			default -> Square.AVAILABLE;
		};
		return new Turn(piece, row, col);
	};

	/**
	 * Reads the statistics file to get info about previously played games.
	 * @return a list of String arrays on the form indicating a win
	 * First element indicates a {@link se.nackademin.Board.Square} and the
	 * other an int indicating the number of turns the game took.
	 * e.g. {"CROSS", "21"}.
	 * @throws IOException if the file couldn't be read form.
	 */
	public List<String[]> readStatsFromFile() throws IOException {
		Path file = Paths.get(STATS_FILE);
		return Files.readAllLines(file)
			.stream()
			.map(s -> s.split(","))
			.toList();
	}
}
