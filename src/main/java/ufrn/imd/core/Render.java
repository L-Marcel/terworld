package ufrn.imd.core;

import java.util.ArrayList;
import java.util.List;
import ufrn.imd.core.abstractions.Node;
import ufrn.imd.core.abstractions.Node2D;
import ufrn.imd.core.constants.Comparators;
import ufrn.imd.core.constants.Rendering;
import ufrn.imd.core.nodes.Player;

public class Render {
  private final Level level;
  private char[][] map;

  public Render(Level level) {
    this.level = level;
  }

  public void render(List<Node> nodes) {
    List<Node2D> visible = new ArrayList<>();

    for (Node node : nodes)
      this.collectVisible(node, visible);

    visible.sort(Comparators.BY_LAYER);
    for (Node2D node : visible)
      this.put(node.getPosition(), node.getShape());
  }

  public void render(Node node) {
    this.render(List.of(node));
  }

  private void collectVisible(Node node, List<Node2D> visible) {
    if (node.isFreePending())
      return;

    if (node instanceof Node2D node2d)
      visible.add(node2d);

    for (Node child : node.getChildren())
      this.collectVisible(child, visible);
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
    if (columns <= 0 || rows <= 0)
      return new Window(0, 0, List.of());

    int width = Math.min(columns, this.level.width());
    int height = Math.min(rows - 1, this.level.height());

    Vector2 origin = this.level.player().getPosition();

    int offsetX = width / 3;
    int offsetY = Math.max(0, height - 1 - Rendering.WINDOW_BOTTOM_OFFSET);

    int left = Math.max(0, Math.min(origin.x() - offsetX, this.level.width() - width));
    int top = Math.max(0, Math.min(origin.y() - offsetY, this.level.height() - height));

    int paddingTop = rows - 1 - height;

    List<String> screen = new ArrayList<>(rows);
    String status = this.getStatus();
    screen.add(status.substring(0, Math.min(columns, status.length())));

    if (paddingTop > 0) {
      String blankLine = " ".repeat(width);
      for (int row = 0; row < paddingTop; row++) {
        screen.add(blankLine);
      }
    }

    for (int row = top; row < top + height; row++) {
      screen.add(new String(this.map[row], left, width));
    }

    return new Window(columns, rows, screen);
  }

  private String getStatus() {
    Player player = this.level.player();
    String status = "HP: " + player.getHp() + "/" + Player.MAX_HP;

    if (player.isDead())
      return status + " | Derrota";
    if (player.isProtected())
      return status + " | Protegido";

    return status;
  }
}
