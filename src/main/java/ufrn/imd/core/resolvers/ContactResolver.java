package ufrn.imd.core.resolvers;

import ufrn.imd.core.abstractions.Body2D;
import ufrn.imd.core.abstractions.Node2D;
import ufrn.imd.core.nodes.EndOfLevel;
import ufrn.imd.core.nodes.Player;
import ufrn.imd.core.nodes.Spike;
import ufrn.imd.core.physics.CollisionMap;
import ufrn.imd.core.physics.Vector2;

public class ContactResolver {
  private final CollisionMap collisions;

  public ContactResolver(CollisionMap collisions) {
    this.collisions = collisions;
  }

  public boolean canContinue(Body2D body) {
    return !body.isFreePending()
      && !(body instanceof Player player && player.hasCompletedLevel());
  }

  public boolean resolveAt(Body2D body, Vector2 position) {
    if (!this.canContinue(body))
      return false;

    if (body instanceof Player player) {
      for (Node2D node : this.collisions.getNodesAt(position)) {
        if (node.isFreePending())
          continue;

        if (node instanceof Spike)
          player.takeDamage();
        else if (node instanceof EndOfLevel)
          player.completeLevel();

        if (!this.canContinue(body))
          return false;
      }
    }

    return true;
  }
}
