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
  private Level level;

  private char[][] map;

  public void nextLevel() {
    Level level = Level.empty();

    if (!this.levels.isEmpty())
      level = this.levels.poll();

    this.level = level;
    this.map = level.content().clone();
    this.nodes = level.nodes();
  }

  public void process(Key key) {
    this.map = this.level.content().clone();
    for (Node node : this.nodes) {
      node.process(key);
      if (node instanceof Node2D node2d)
        this.render(node2d);
    }
  }

  public void render(Node2D node2d) {
    Vector2 position = node2d.getPosition();
    char shape = node2d.getShape();

    int height = this.level.height();
    int width = this.level.width();

    boolean isValidX = position.x() >= 0 && position.x() < width;
    boolean isValidY = position.y() >= 0 && position.y() < height;

    if (isValidX && isValidY)
      this.map[position.y()][position.x()] = shape;
  }

  public Screen getScreen(int columns, int rows) {
    int height = this.level.height();
    int width = this.level.width();

    List<String> screen = new ArrayList<>(height);

    for (int row = 0; row < height; row++) {
      StringBuilder builder = new StringBuilder();

      for (int column = 0; column < width; column++) {
        builder.append(this.map[row][column]);
      }

      screen.add(builder.toString());
    }

    return new Screen(width, height, screen);
  }

  public void setLevels(Queue<Level> levels) {
    this.levels = levels;
  }
}
