package ufrn.imd.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import ufrn.imd.inputs.Key;

public class Game {
  private Queue<Level> levels;
  private Level current;

  public void nextLevel() {
    if (!this.levels.isEmpty())
      this.current = this.levels.poll();
  }

  public void process(Key key) {

  }

  public Screen getScreen(int columns, int rows) {
    List<String> screen = new ArrayList<>(rows);

    if (this.current != null)
      for (char[] row : this.current.content()) {
        screen.add(new String(row));
      }

    return new Screen(columns, rows, screen);
  }

  public void setLevels(Queue<Level> levels) {
    this.levels = levels;
  }
}
