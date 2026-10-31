package ufrn.imd.core.resolvers;

import ufrn.imd.core.abstractions.Body2D;
import ufrn.imd.core.abstractions.Fighter;
import ufrn.imd.core.nodes.Enemy;
import ufrn.imd.core.nodes.Player;

public class CombatResolver {
  public void resolve(Body2D owner, Body2D target) {
    if (!(target instanceof Fighter fighter))
      return;

    boolean opponents = owner instanceof Player && fighter instanceof Enemy
      || owner instanceof Enemy && fighter instanceof Player;

    if (opponents)
      fighter.takeDamage();
  }
}
