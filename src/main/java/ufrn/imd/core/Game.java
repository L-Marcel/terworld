package ufrn.imd.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import ufrn.imd.core.abstractions.Node;

public class Game {
  private Queue<Level> levels;
  private Level initialLevel;
  private Level level;
  private List<Node> nodes;
  private Render render;
  private Physics physics;
  private boolean won = false;

  public Game() {
    this.initialLevel = Level.empty();
    this.levels = new LinkedList<>();
    this.loadLevel(this.initialLevel.copy());
  }

  public void nextLevel() {
    if (this.levels.isEmpty()) {
      this.won = true;
      return;
    }

    this.initialLevel = this.levels.poll();
    this.loadLevel(this.initialLevel.copy());
  }

  private void loadLevel(Level level) {
    this.level = level;
    this.render = new Render(level);
    this.physics = new Physics(level);
    this.nodes = level.nodes();
  }

  public void process(Action action) {
    if (this.won)
      return;

    if (this.level.player().isDead()) {
      this.loadLevel(this.initialLevel.copy());
    } else if (this.level.player().hasCompletedLevel()) {
      this.nextLevel();
    } else {
      this.level.player().tickDamageProtection();

      for (Node node : this.nodes) {
        if (!node.isFreePending())
          node.process(action, this.physics);
      }

      for (Node node : this.nodes) {
        if (!node.isFreePending()) {
          for (Node child : node.getChildren())
            this.process(child, action);
        }
      }

      for (Node node : this.nodes) {
        this.freeQueued(node);
      }

      this.nodes.removeIf(Node::isFreePending);
    }

    this.render.reset();
    this.render.render(this.nodes);
  }

  private void process(Node node, Action action) {
    if (node.isFreePending())
      return;

    node.process(action, this.physics);
    for (Node child : node.getChildren())
      this.process(child, action);
  }

  private void freeQueued(Node node) {
    for (Node child : List.copyOf(node.getChildren())) {
      this.freeQueued(child);
      if (child.isFreePending())
        node.removeChild(child);
    }
  }

  public Window getWindow(int columns, int rows) {
    return this.render.getWindow(columns, rows, this.won);
  }

  public boolean isWon() {
    return this.won;
  }

  public void setLevels(Queue<Level> levels) {
    this.levels = levels;
    this.won = false;
  }
}
