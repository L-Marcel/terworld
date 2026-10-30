package ufrn.imd.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import ufrn.imd.core.abstractions.Node;
import ufrn.imd.inputs.Key;

public class Game {
  private Queue<Level> levels;
  private Level initialLevel;
  private Level level;
  private List<Node> nodes;
  private Render render;
  private Physics physics;

  public Game() {
    this.initialLevel = Level.empty();
    this.levels = new LinkedList<>();
    this.loadLevel(this.initialLevel.copy());
  }

  public void nextLevel() {
    this.initialLevel = this.levels.isEmpty() ? Level.empty() : this.levels.poll();
    this.loadLevel(this.initialLevel.copy());
  }

  private void loadLevel(Level level) {
    this.level = level;
    this.render = new Render(level);
    this.physics = new Physics(level);
    this.nodes = level.nodes();
  }

  public void process(Key key) {
    if (this.level.player().isDead()) {
      this.loadLevel(this.initialLevel.copy());
    } else {
      this.level.player().tickDamageProtection();

      for (Node node : this.nodes) {
        this.process(node, key);
      }

      for (Node node : this.nodes) {
        this.freeQueued(node);
      }

      this.nodes.removeIf(Node::isFreePending);
    }

    this.render.reset();
    this.render.render(this.nodes);
  }

  private void process(Node node, Key key) {
    if (node.isFreePending())
      return;

    node.process(key, this.physics);
    for (Node child : node.getChildren())
      this.process(child, key);
  }

  private void freeQueued(Node node) {
    for (Node child : List.copyOf(node.getChildren())) {
      this.freeQueued(child);
      if (child.isFreePending())
        node.removeChild(child);
    }
  }

  public Window getWindow(int columns, int rows) {
    return this.render.getWindow(columns, rows);
  }

  public void setLevels(Queue<Level> levels) {
    this.levels = levels;
  }
}
