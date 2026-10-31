package ufrn.imd.core.physics;

import java.util.Arrays;

public class Grid {
  private final int width;
  private final char[][] cells;

  public Grid(int width, int height) {
    this.width = Math.max(0, width);
    this.cells = new char[Math.max(0, height)][this.width];
    for (char[] row : this.cells)
      Arrays.fill(row, ' ');
  }

  public Grid(char[][] cells) {
    this(widthOf(cells), cells == null ? 0 : cells.length);
    this.copyFrom(cells);
  }

  private static int widthOf(char[][] cells) {
    int width = 0;

    if (cells != null) {
      for (char[] row : cells) {
        if (row != null)
          width = Math.max(width, row.length);
      }
    }

    return width;
  }

  private void copyFrom(char[][] cells) {
    if (cells == null)
      return;

    for (int row = 0; row < Math.min(cells.length, this.getHeight()); row++) {
      if (cells[row] != null)
        System.arraycopy(cells[row], 0, this.cells[row], 0, Math.min(this.width, cells[row].length));
    }
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.cells.length;
  }

  public boolean contains(Vector2 position) {
    return position != null
      && position.x() >= 0 && position.y() >= 0
      && position.x() < this.width && position.y() < this.getHeight();
  }

  public char get(Vector2 position) {
    if (!this.contains(position))
      return ' ';

    return this.cells[position.y()][position.x()];
  }

  public boolean put(Vector2 position, char value) {
    if (!this.contains(position))
      return false;

    this.cells[position.y()][position.x()] = value;
    return true;
  }

  public String getRow(int row, int left, int columns) {
    if (row < 0 || row >= this.getHeight() || columns <= 0)
      return "";

    int start = Math.max(0, Math.min(left, this.width));
    int length = Math.min(columns, this.width - start);
    return new String(this.cells[row], start, length);
  }

  public Grid copy() {
    Grid copy = new Grid(this.width, this.getHeight());
    copy.copyFrom(this.cells);
    return copy;
  }
}
