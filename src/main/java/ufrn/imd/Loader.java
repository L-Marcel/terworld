package ufrn.imd;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;
import ufrn.imd.core.Level;

public class Loader {
  public Queue<Level> load() throws IOException, URISyntaxException {
    Path location = Path.of(
      Loader.class.getProtectionDomain()
        .getCodeSource()
        .getLocation()
        .toURI()
    );

    if (Files.isDirectory(location))
      return this.loadDirectory(location);

    try (FileSystem jar = FileSystems.newFileSystem(location)) {
      return this.loadDirectory(jar.getPath("/"));
    }
  }

  private Queue<Level> loadDirectory(Path directory) throws IOException {
    List<Path> files;

    try (Stream<Path> entries = Files.list(directory)) {
      files = entries.filter(Files::isRegularFile)
        .filter(path -> path.getFileName().toString().endsWith("_level.txt"))
        .sorted(Comparator.comparing(path -> path.getFileName().toString()))
        .toList();
    }

    Queue<Level> levels = new LinkedList<>();

    for (Path file : files) {
      levels.add(this.loadLevel(file));
    }

    return levels;
  }

  private Level loadLevel(Path file) throws IOException {
    List<String> lines = Files.readAllLines(file);

    int width = lines.stream().mapToInt(String::length).max().orElse(0);
    int height = lines.size();

    char[][] content = new char[height][width];

    for (int row = 0; row < height; row++) {
      String line = lines.get(row);
      Arrays.fill(content[row], ' ');
      line.getChars(0, line.length(), content[row], 0);
    }

    return new Level(width, height, content);
  }
}
