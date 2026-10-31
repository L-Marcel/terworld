package ufrn.imd.core.resolvers;

import java.util.ArrayList;
import java.util.List;
import ufrn.imd.core.Scene;
import ufrn.imd.core.abstractions.Body2D;
import ufrn.imd.core.abstractions.Fighter;
import ufrn.imd.core.abstractions.Node;
import ufrn.imd.core.nodes.Enemy;
import ufrn.imd.core.nodes.Player;
import ufrn.imd.core.nodes.Sword;
import ufrn.imd.core.physics.CollisionMap;
import ufrn.imd.core.physics.Vector2;

public class CombatResolver {
  private final CollisionMap collisions;

  public CombatResolver(CollisionMap collisions) {
    this.collisions = collisions;
  }

  public void resolve(Scene scene) {
    List<Sword> swords = new ArrayList<>();

    for (Node node : scene.getNodes()) {
      if (node instanceof Sword sword)
        swords.add(sword);
    }

    for (Sword sword : swords) {
      if (sword.getOwner() instanceof Player)
        this.resolve(sword);
    }

    for (Sword sword : swords) {
      if (!(sword.getOwner() instanceof Player))
        this.resolve(sword);
    }
  }

  private void resolve(Sword sword) {
    if (sword.isFreePending() || sword.getOwner().isFreePending())
      return;

    Fighter owner = sword.getOwner();
    for (Vector2 position : sword.getCells()) {
      Body2D target = this.collisions.getBodyAt(position, owner).orElse(null);
      if (!(target instanceof Fighter fighter))
        continue;

      boolean opponents = owner instanceof Player && fighter instanceof Enemy
        || owner instanceof Enemy && fighter instanceof Player;

      if (opponents && sword.markHit(fighter))
        fighter.takeDamage();
    }
  }
}
