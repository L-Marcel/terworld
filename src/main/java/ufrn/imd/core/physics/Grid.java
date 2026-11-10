package ufrn.imd.core.physics;

public class Grid {
  //@ spec_public
  private final int width;

  //@ spec_public
  private final char[][] cells;

  //@ public invariant 0 <= width <= Integer.MAX_VALUE;
  //@ public invariant 0 <= cells.length <= Integer.MAX_VALUE;
  //@ public invariant \forall int i; 0 <= i < cells.length;
  //@  cells[i].length == width;
  //@ public invariant \forall int i, j; 0 <= i < j < cells.length;
  //@  cells[i] != cells[j];

  //@ public normal_behavior
  //@ requires width >= 0;
  //@ requires height >= 0;
  //@ ensures this.width == width;
  //@ ensures this.cells.length == height;
  //@ ensures \fresh(this.cells);
  //@ ensures \forall int i; 0 <= i < this.cells.length;
  //@   \fresh(this.cells[i]);
  public Grid(int width, int height) {
    this.width = Math.max(0, width);
    this.cells = new char[Math.max(0, height)][this.width];
    //@ loop_invariant 0 <= row <= this.cells.length;
    //@ loop_assignable row, this.cells[*][*];
    for (int row = 0; row < this.cells.length; row++) {
      //@ loop_invariant 0 <= col <= this.width;
      //@ loop_assignable col, this.cells[row][*];
      for (int col = 0; col < this.width; col++)
        this.cells[row][col] = ' ';
    }
  }

  //@ public normal_behavior
  public Grid(char[][] cells) {
    this(widthOf(cells), cells);
  }

  //@ private normal_behavior
  //@ ensures \forall int i; 0 <= i < cells.length;
  //@   \result >= cells[i].length;
  //@ ensures (cells.length > 0) ==> \exists int i; 0 <= i < cells.length;
  //@   \result == cells[i].length;
  //@ ensures \result >= 0;
  //@ spec_pure
  private static int widthOf(char[][] cells) {
    int width = 0;
    //@ ghost int widest = 0;

    if (cells != null) {
      //@ loop_invariant 0 <= row <= cells.length;
      //@ loop_invariant width >= 0;
      //@ loop_invariant \forall int i; 0 <= i < row;
      //@     width >= cells[i].length;
      //@ loop_invariant (row > 0) ==> (
      //@   0 <= widest < row && width == cells[widest].length
      //@ );
      //@ loop_assignable row, width, widest;
      for (int row = 0; row < cells.length; row++) {
        if (row == 0 || cells[row].length > width) {
          width = cells[row].length;
          //@ set widest = row;
        }
      }
    }

    return width;
  }

  //@ private normal_behavior
  //@ requires width >= 0;
  //@ assignable \nothing;
  //@ ensures this.width == width && this.cells.length == cells.length;
  //@ ensures \forall int r; 0 <= r < cells.length;
  //@   (\forall int c; 0 <= c < Math.min(width, cells[r].length);
  //@     this.cells[r][c] == cells[r][c]);
  private Grid(int width, char[][] cells) {
    this.width = width;
    this.cells = new char[cells.length][width];
    //@ loop_invariant 0 <= row <= cells.length;
    //@ loop_invariant \forall int r; 0 <= r < row;
    //@   (\forall int c; 0 <= c < Math.min(this.width, cells[r].length);
    //@     this.cells[r][c] == cells[r][c]);
    //@ loop_assignable row, this.cells[*][*];
    for (int row = 0; row < cells.length; row++) {
      int maxCols = this.width;

      //@ loop_invariant 0 <= col <= maxCols;
      //@ loop_invariant \forall int r; 0 <= r < row;
      //@   (\forall int c; 0 <= c < Math.min(this.width, cells[r].length);
      //@     this.cells[r][c] == cells[r][c]);
      //@ loop_invariant \forall int c; 0 <= c < Math.min(col, cells[row].length);
      //@   this.cells[row][c] == cells[row][c];
      //@ loop_assignable col, this.cells[row][*];
      for (int col = 0; col < maxCols; col++) {
        this.cells[row][col] = col < cells[row].length ? cells[row][col] : ' ';
      }
    }
  }

  //@ public normal_behavior
  //@ ensures \result == this.width;
  //@ spec_pure
  public int getWidth() {
    return this.width;
  }

  //@ public normal_behavior
  //@ ensures \result == this.cells.length;
  //@ spec_pure
  public int getHeight() {
    return this.cells.length;
  }

  //@ public normal_behavior
  //@ ensures \result == (
  //@   0 <= position.x() && position.x() < this.width
  //@   && 0 <= position.y() && position.y() < this.cells.length
  //@ );
  //@ spec_pure
  public boolean contains(Vector2 position) {
    return position != null
      && position.x() >= 0 && position.y() >= 0
      && position.x() < this.width && position.y() < this.getHeight();
  }

  //@ public normal_behavior
  //@ spec_pure
  public char get(Vector2 position) {
    if (!this.contains(position))
      return ' ';

    return this.cells[position.y()][position.x()];
  }

  //@ public normal_behavior
  //@ assignable this.cells[*][*];
  public boolean put(Vector2 position, char value) {
    if (!this.contains(position))
      return false;

    this.cells[position.y()][position.x()] = value;
    return true;
  }

  //@ public normal_behavior
  //@ requires 0 <= row < this.cells.length;
  //@ requires this.cells[row].length == this.width;
  //@ requires columns > 0;
  //@ ensures 0 <= \result.length() <= columns;
  public String getRow(int row, int left, int columns) {
    if (row < 0 || row >= this.getHeight() || columns <= 0)
      return "";

    int start = Math.max(0, Math.min(left, this.width));
    int length = Math.min(columns, this.width - start);
    return new String(this.cells[row]).substring(start, start + length);
  }

  //@ public normal_behavior
  //@ ensures \result.width == this.width;
  //@ ensures \forall int i; 0 <= i < \result.cells.length;
  //@     \result.cells[i].length == this.cells[i].length;
  //@ ensures \forall int i; 0 <= i < \result.cells.length;
  //@   \forall int j; 0 <= j < \result.cells[i].length;
  //@     \result.cells[i][j] == this.cells[i][j];
  public Grid copy() {
    return new Grid(this.width, this.cells);
  }
}
