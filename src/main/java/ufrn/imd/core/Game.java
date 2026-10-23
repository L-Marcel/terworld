package ufrn.imd.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import ufrn.imd.core.interfaces.Node;
import ufrn.imd.core.interfaces.Node2D;
import ufrn.imd.inputs.Key;

public class Game {
  private Queue<Level> levels;
  private List<Node> nodes;
  private Level current;

  public void nextLevel() {
    if (!this.levels.isEmpty())
      this.current = this.levels.poll();

    if (this.current != null) {
      // this.current.player();
      this.nodes = this.current.nodes();
    }
  }

  public void process(Key key) {
    for (Node node : this.nodes) {
      node.process(key);
      if (node instanceof Node2D node2d)
        this.render(node2d);
    }
  }

  public void render(Node2D node2d) {

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
