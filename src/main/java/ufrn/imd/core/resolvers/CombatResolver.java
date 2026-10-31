package ufrn.imd.core.resolvers;

import ufrn.imd.core.abstractions.Body2D;
import ufrn.imd.core.nodes.Enemy;
import ufrn.imd.core.nodes.Player;

public class CombatResolver {
  public void resolve(Body2D owner, Body2D target) {
    if (owner instanceof Player && target instanceof Enemy)
      target.queueFree();
    else if (owner instanceof Enemy && target instanceof Player player)
      player.takeDamage();
  }
}
