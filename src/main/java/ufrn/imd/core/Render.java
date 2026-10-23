package ufrn.imd.core;

import java.util.ArrayList;
import java.util.List;
import ufrn.imd.core.abstractions.Node;
import ufrn.imd.core.abstractions.Node2D;
import ufrn.imd.core.constants.Comparators;
import ufrn.imd.core.constants.Rendering;

public class Render {
  private final Level level;
  private char[][] map;

  public Render(Level level) {
    this.level = level;
  }

  public void render(List<Node> nodes) {
    for (Node node : nodes.stream().sorted(Comparators.BY_LAYER).toList()) {
      this.render(node);
    }
  }

  public void render(Node node) {
    if (node.isFreePending())
      return;

    if (node instanceof Node2D node2d)
      this.put(
        node2d.getPosition(),
        node2d.getShape()
      );

    this.render(node.getChildren());
  }

  private void put(Vector2 position, char shape) {
    int height = this.level.height();
    int width = this.level.width();

    boolean isValidX = position.x() >= 0 && position.x() < width;
    boolean isValidY = position.y() >= 0 && position.y() < height;

    if (isValidX && isValidY)
      this.map[position.y()][position.x()] = shape;
  }

  public void reset() {
    char[][] content = this.level.content();
    this.map = new char[content.length][];

    for (int row = 0; row < content.length; row++) {
      this.map[row] = content[row].clone();
    }
  }

  public Window getWindow(int columns, int rows) {
    int width = Math.max(0, Math.min(columns, this.level.width()));
    int height = Math.max(0, Math.min(rows, this.level.height()));

    if (width == 0 || height == 0)
      return new Window(0, 0, List.of());

    Vector2 origin = this.level.player().getPosition();

    int offsetX = width / 3;
    int offsetY = Math.max(0, height - 1 - Rendering.WINDOW_BOTTOM_OFFSET);

    int left = Math.max(0, Math.min(origin.x() - offsetX, this.level.width() - width));
    int top = Math.max(0, Math.min(origin.y() - offsetY, this.level.height() - height));

    int paddingTop = rows - height;

    List<String> screen = new ArrayList<>(rows);

    if (paddingTop > 0) {
      String blankLine = " ".repeat(width);
      for (int row = 0; row < paddingTop; row++) {
        screen.add(blankLine);
      }
    }

    for (int row = top; row < top + height; row++) {
      screen.add(new String(this.map[row], left, width));
    }

    return new Window(width, rows, screen);
  }
}
