package ufrn.imd.core.rendering;

import java.util.ArrayList;
import java.util.List;
import ufrn.imd.core.Level;
import ufrn.imd.core.Scene;
import ufrn.imd.core.abstractions.Node;
import ufrn.imd.core.abstractions.Node2D;
import ufrn.imd.core.constants.Comparators;
import ufrn.imd.core.physics.Vector2;

public class Render {
  private final Level level;
  private final Scene scene;
  private final Camera camera;
  private final Hud hud;
  private char[][] map;

  public Render(Level level, Scene scene) {
    this.level = level;
    this.scene = scene;
    this.camera = new Camera();
    this.hud = new Hud();
    this.reset();
  }

  public void redraw() {
    this.reset();
    List<Node2D> visible = new ArrayList<>();

    for (Node node : this.scene.getNodes()) {
      if (node instanceof Node2D node2d)
        visible.add(node2d);
    }

    visible.sort(Comparators.BY_LAYER);
    for (Node2D node : visible)
      this.put(node.getPosition(), node.getShape());
  }

  private void reset() {
    char[][] content = this.level.content();
    this.map = new char[content.length][];
    for (int row = 0; row < content.length; row++)
      this.map[row] = content[row].clone();
  }

  private void put(Vector2 position, char shape) {
    boolean validX = position.x() >= 0 && position.x() < this.level.width();
    boolean validY = position.y() >= 0 && position.y() < this.level.height();
    if (validX && validY)
      this.map[position.y()][position.x()] = shape;
  }

  public Window getWindow(int columns, int rows, boolean won) {
    if (columns <= 0 || rows <= 0)
      return new Window(0, 0, List.of());

    List<String> lines = new ArrayList<>(rows);
    lines.add(this.hud.render(this.level.player(), won, columns));
    lines.addAll(this.camera.capture(this.map, this.level.player().getPosition(), columns, rows - 1));
    return new Window(columns, rows, lines);
  }
}
