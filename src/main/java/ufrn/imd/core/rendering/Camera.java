package ufrn.imd.core.rendering;

import java.util.ArrayList;
import java.util.List;
import ufrn.imd.core.constants.Rendering;
import ufrn.imd.core.physics.Grid;
import ufrn.imd.core.physics.Vector2;

public class Camera {
  public List<String> capture(Grid map, Vector2 target, int columns, int rows) {
    if (columns <= 0 || rows <= 0)
      return List.of();

    int mapHeight = map.getHeight();
    int mapWidth = map.getWidth();
    int width = Math.min(columns, mapWidth);
    int height = Math.min(rows, mapHeight);
    int offsetX = width / Rendering.WINDOW_HORIZONTAL_DIVISOR;
    int offsetY = Math.max(0, height - 1 - Rendering.WINDOW_BOTTOM_OFFSET);

    int left = Math.max(0, Math.min(target.x() - offsetX, mapWidth - width));
    int top = Math.max(0, Math.min(target.y() - offsetY, mapHeight - height));

    List<String> lines = new ArrayList<>(rows);
    for (int row = 0; row < rows - height; row++)
      lines.add(" ".repeat(width));

    for (int row = top; row < top + height; row++)
      lines.add(map.getRow(row, left, width));

    return lines;
  }
}
