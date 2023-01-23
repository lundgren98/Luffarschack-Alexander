package se.nackademin;

import se.nackademin.Play.PlayerWin;
import se.nackademin.Board.Square;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FileIO {
	private final String STATS_DIR = "stats/";
	private final String STATS_FILE = STATS_DIR + "stat.csv";
	private final String SAVES_DIR = "saves/";
	private final String SAVES_FILE_PREFIX = SAVES_DIR + "save";
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
	public void saveToFile(List<Turn> turns) throws IOException {
		Path dir = Paths.get(SAVES_DIR);
		Files.createDirectories(dir);
		int fileNumber = dir.toFile().list().length;
		Path path = Paths.get(
				SAVES_FILE_PREFIX
				+ Integer.toString(fileNumber)
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
	public List<Turn> ReadTurnsFromFile() throws IOException, IndexOutOfBoundsException {
		Path file = Paths.get(SAVES_FILE_PREFIX + ".csv");
		return Files.readAllLines(file)
			.stream()
			.map(s -> s.split(","))
			.map(arr -> strArrToTurn(arr))
			.toList();
	}
	private Turn strArrToTurn(String[] arr) throws IndexOutOfBoundsException {
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
}
