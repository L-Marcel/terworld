package ufrn.imd.core;

import ufrn.imd.core.enums.Action;
import ufrn.imd.core.nodes.Player;
import ufrn.imd.core.physics.CollisionMap;
import ufrn.imd.core.physics.Physics;
import ufrn.imd.core.rendering.Render;
import ufrn.imd.core.rendering.Window;

public class World {
  private final Player player;
  private final Scene scene;
  private final Physics physics;
  private final Render render;

  public World(Level initialLevel) {
    Level level = initialLevel.copy();
    this.player = level.player();
    this.scene = new Scene(level.nodes());
    this.physics = new Physics(new CollisionMap(level, this.scene));
    this.render = new Render(level, this.scene);
  }

  public void process(Action action) {
    this.player.beginTick();
    this.scene.processRoots(action, this.physics);
    this.scene.processChildren(action, this.physics);
    this.scene.removeQueued();
  }

  public void redraw() {
    this.render.redraw();
  }

  public boolean isDead() {
    return this.player.isDead();
  }

  public boolean isCompleted() {
    return this.player.hasCompletedLevel();
  }

  public Window getWindow(int columns, int rows, boolean won) {
    return this.render.getWindow(columns, rows, won);
  }
}
