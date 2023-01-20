package se.nackademin;

import se.nackademin.Play.PlayerWin;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FileIO {
	public void saveToFile(PlayerWin pw, int numberOfTurns) throws IOException {
		Files.createDirectories(Paths.get("stats/"));
		Path path = Paths.get("stats/stat.csv");
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
		Files.createDirectories(Paths.get("saves/"));
		Path path = Paths.get("saves/save.csv");
		List<String> lines = turnListToStringList(turns);
		saveLinesToFile(lines, path);
	}
	public void saveLinesToFile(List<String> lines, Path path) throws IOException {
		for (String line : lines) {
			Files.writeString(path, line,
				StandardOpenOption.CREATE_NEW);
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
		return String.join(",", vals);
	}
	public void ReadFromFile() {}
}
