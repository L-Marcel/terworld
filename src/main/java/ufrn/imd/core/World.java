package ufrn.imd.core;

import ufrn.imd.core.abstractions.Fighter;
import ufrn.imd.core.abstractions.Node;
import ufrn.imd.core.nodes.Player;
import ufrn.imd.core.physics.CollisionMap;
import ufrn.imd.core.physics.Physics;
import ufrn.imd.core.rendering.Render;
import ufrn.imd.core.rendering.Window;
import ufrn.imd.core.resolvers.CombatResolver;

public class World {
  private final Player player;
  private final Scene scene;
  private final Physics physics;
  private final Render render;
  private final CombatResolver combat;

  public World(Level initialLevel) {
    Level level = initialLevel.copy();
    this.player = level.player();
    this.scene = new Scene(level.nodes());

    CollisionMap collisions = new CollisionMap(level, this.scene);
    this.physics = new Physics(collisions);
    this.combat = new CombatResolver(collisions);

    this.render = new Render(level, this.scene);
  }

  public void process(Controls controls) {
    for (Node node : this.scene.getNodes()) {
      if (node instanceof Fighter fighter)
        fighter.beginTick();
    }

    this.scene.processRoots(controls, this.physics);
    this.scene.processChildren(controls, this.physics);
    this.combat.resolve(this.scene);
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
