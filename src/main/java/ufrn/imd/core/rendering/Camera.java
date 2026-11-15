package ufrn.imd.core.rendering;

import java.util.Arrays;
import java.util.List;
import ufrn.imd.core.constants.Rendering;
import ufrn.imd.core.physics.Grid;
import ufrn.imd.core.physics.Vector2;

public class Camera {
  //@ public normal_behavior
  //@ requires 0 <= columns <= Integer.MAX_VALUE;
  //@ requires 0 <= rows <= Integer.MAX_VALUE;
  //@ requires 0 <= target.x() <= Integer.MAX_VALUE;
  //@ requires 0 <= target.y() <= Integer.MAX_VALUE;
  //@ requires 0 <= map.width <= Integer.MAX_VALUE;
  //@ requires 0 <= map.cells.length <= Integer.MAX_VALUE;
  //@ requires (columns > 0) ==> (Integer.MIN_VALUE + columns <= target.x());
  //@ requires (rows > 0) ==> (Integer.MIN_VALUE + rows <= target.y());
  //@ ensures (columns == 0 || rows == 0) ==> (\result.size() == 0);
  //@ ensures (columns > 0 && rows > 0) ==> (\result.size() == rows);
  //@ ensures \forall int i; 0 <= i < \result.size();
  //@     0 <= \result.values[i].chars.length <= Math.min(columns, map.width);
  //@ ensures \fresh(\result);
  //@ assignable \nothing;
  public List<String> capture(
    Grid map,
    Vector2 target,
    int columns,
    int rows
  ) {
    if (columns <= 0 || rows <= 0)
      return Arrays.asList(new String[0]);

    int mapHeight = map.getHeight();
    int mapWidth = map.getWidth();
    int width = Math.min(columns, mapWidth);
    int height = Math.min(rows, mapHeight);
    int offsetX = width / Rendering.WINDOW_HORIZONTAL_DIVISOR;
    int offsetY = Math.max(0, height - 1 - Rendering.WINDOW_BOTTOM_OFFSET);
    int left = Math.max(0, Math.min(target.x() - offsetX, mapWidth - width));
    int top = Math.max(0, Math.min(target.y() - offsetY, mapHeight - height));

    String[] lines = new String[rows];

    //@ loop_invariant 0 <= row <= rows - height;
    //@ loop_invariant \forall int i; 0 <= i < row;
    //@   lines[i].chars.length == width;
    //@ loop_invariant \forall int i; 0 <= i < row;
    //@   (\forall int j; 0 <= j < width; 
    //@     lines[i].chars[j] == ' ');
    //@ loop_assignable row, lines[*];
    //@ decreases (rows - height) - row;
    for (int row = 0; row < rows - height; row++)
      lines[row] = spaces(width);

    //@ loop_invariant top <= row <= top + height;
    //@ loop_invariant \forall int i; 0 <= i < rows - height;
    //@   lines[i].chars.length == width;
    //@ loop_invariant \forall int i; 0 <= i < rows - height;
    //@   (\forall int j; 0 <= j < width; 
    //@     lines[i].chars[j] == ' ');
    //@ loop_invariant \forall int i; rows - height <= i < (rows - height) + (row - top);
    //@   0 <= lines[i].chars.length <= width;
    //@ loop_assignable row, lines[*];
    //@ decreases (top + height) - row;
    for (int row = top; row < top + height; row++)
      lines[(rows - height) + (row - top)] = map.getRow(row, left, width);

    return Arrays.asList(lines);
  }

  //@ private normal_behavior
  //@ requires 0 <= width <= Integer.MAX_VALUE;
  //@ ensures \result.chars.length == width;
  //@ ensures \forall int i; 0 <= i < width; \result.chars[i] == ' ';
  //@ ensures \fresh(\result);
  //@ spec_pure
  private static String spaces(int width) {
    char[] buffer = new char[width];
    //@ loop_invariant 0 <= i <= width;
    //@ loop_invariant \forall int j; 0 <= j < i; buffer[j] == ' ';
    //@ loop_assignable i, buffer[*];
    //@ decreases width - i;
    for (int i = 0; i < width; i++)
      buffer[i] = ' ';
    return String.valueOf(buffer);
  }
}
