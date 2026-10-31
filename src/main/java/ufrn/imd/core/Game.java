package ufrn.imd.core;

import java.util.LinkedList;
import java.util.Queue;
import ufrn.imd.core.enums.Action;
import ufrn.imd.core.rendering.Window;

public class Game {
  private Queue<Level> levels;
  private Level initialLevel;
  private World world;
  private boolean won = false;

  public Game() {
    this.initialLevel = Level.empty();
    this.levels = new LinkedList<>();
    this.world = new World(this.initialLevel);
  }

  public void nextLevel() {
    if (this.levels.isEmpty()) {
      this.won = true;
      return;
    }

    this.initialLevel = this.levels.poll();
    this.world = new World(this.initialLevel);
  }

  public void process(Action action) {
    if (this.won)
      return;

    if (this.world.isDead()) {
      this.restartLevel();
    } else if (this.world.isCompleted()) {
      this.nextLevel();
    } else {
      this.world.process(action);
    }

    this.world.redraw();
  }

  private void restartLevel() {
    this.world = new World(this.initialLevel);
  }

  public Window getWindow(int columns, int rows) {
    return this.world.getWindow(columns, rows, this.won);
  }

  public boolean isWon() {
    return this.won;
  }

  public void setLevels(Queue<Level> levels) {
    this.levels = levels;
    this.won = false;
  }
}
