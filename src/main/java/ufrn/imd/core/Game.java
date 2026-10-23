package ufrn.imd.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import ufrn.imd.core.interfaces.Node;
import ufrn.imd.core.interfaces.Node2D;
import ufrn.imd.inputs.Key;

public class Game {
  private Queue<Level> levels;
  private List<Node> nodes;
  private Render render;

  public Game() {
    this.render = new Render(Level.empty());
    this.levels = new LinkedList<>();
    this.nodes = new LinkedList<>();
  }

  public void nextLevel() {
    Level level = Level.empty();

    if (!this.levels.isEmpty())
      level = this.levels.poll();

    this.nodes = level.nodes();
    this.render = new Render(level);
  }

  public void process(Key key) {
    this.render.reset();
    for (Node node : this.nodes) {
      node.process(key);
      if (node instanceof Node2D node2d)
        this.render.render(node2d);
    }
  }

  public Window getWindow(int columns, int rows) {
    return this.render.getWindow(columns, rows);
  }

  public void setLevels(Queue<Level> levels) {
    this.levels = levels;
  }
}
