package ufrn.imd.core;

import java.util.ArrayList;
import java.util.List;
import ufrn.imd.core.interfaces.Node2D;

public class Render {
  private final Level level;
  private char[][] map;

  public Render(Level level) {
    this.level = level;
  }

  public void render(Node2D node2d) {
    this.put(
      node2d.getPosition(),
      node2d.getShape()
    );
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
    List<String> screen = new ArrayList<>(this.level.height());

    for (int row = 0; row < this.level.height(); row++) {
      StringBuilder builder = new StringBuilder();

      for (int column = 0; column < this.level.width(); column++) {
        builder.append(this.map[row][column]);
      }

      screen.add(builder.toString());
    }

    return new Window(this.level.width(), this.level.height(), screen);
  }
}
