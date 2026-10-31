package ufrn.imd.core;

import java.util.LinkedList;
import java.util.Queue;
import ufrn.imd.core.constants.Levels;
import ufrn.imd.core.constants.Ticks;
import ufrn.imd.core.rendering.Window;

public class Game {
  private boolean won = false;
  private Queue<Level> levels;
  private Level initialLevel;
  private World world;
  private double transitionSeconds;

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
    this.transitionSeconds = 0;
  }

  public void process(Controls controls) {
    if (this.won)
      return;

    if (this.world.isDead() || this.world.isCompleted()) {
      this.transitionSeconds += Ticks.STEP_SECONDS;
      double delay = this.world.isDead() ? Levels.DEFEAT_DELAY_SECONDS : Levels.COMPLETION_DELAY_SECONDS;
      if (this.transitionSeconds + Ticks.TIME_EPSILON >= delay) {
        if (this.world.isDead())
          this.restartLevel();
        else
          this.nextLevel();
      }
    } else {
      this.world.process(controls);
    }

    this.world.redraw();
  }

  private void restartLevel() {
    this.world = new World(this.initialLevel);
    this.transitionSeconds = 0;
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
