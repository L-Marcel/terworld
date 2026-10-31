package ufrn.imd.core.nodes;

import ufrn.imd.core.Controls;
import ufrn.imd.core.abstractions.Fighter;
import ufrn.imd.core.components.AttackComponent;
import ufrn.imd.core.constants.Attacks;
import ufrn.imd.core.constants.Forces;
import ufrn.imd.core.constants.Health;
import ufrn.imd.core.enums.Direction;
import ufrn.imd.core.physics.CollisionMap;
import ufrn.imd.core.physics.Physics;
import ufrn.imd.core.physics.Vector2;

public class Enemy extends Fighter {
  public Enemy(Vector2 position) {
    super(
      position, 1, Health.ENEMY_MAX_HP, new AttackComponent(
        Attacks.ENEMY_PREPARATION_SECONDS, Attacks.ENEMY_ACTIVE_SECONDS, Attacks.ENEMY_RECOVERY_SECONDS
      )
    );
  }

  @Override
  public void process(Controls controls, Physics physics) {
    if (!this.canAttack()) {
      this.setHorizontalSpeed(0);
      super.process(controls, physics);
      return;
    }

    CollisionMap collisions = physics.getCollisionMap();
    boolean attacking = this.attackIfPlayerInRange(collisions);
    this.patrol(physics, attacking);
    super.process(controls, physics);

    if (!attacking)
      this.attackIfPlayerInRange(collisions);
  }

  private void patrol(Physics physics, boolean attacking) {
    double speed = 0;
    if (!attacking && physics.isGrounded(this)) {
      CollisionMap collisions = physics.getCollisionMap();
      Vector2 next = this.position.sum(this.getFacing().step(), 0);

      if (collisions.isBlocked(next, this)
        || collisions.isDangerous(next)
        || !collisions.isSolid(next.sum(0, 1)))
        this.face(this.getFacing().opposite());
      else
        speed = this.getFacing().step() * Forces.ENEMY_SPEED;
    }

    this.setHorizontalSpeed(speed);
  }

  private boolean attackIfPlayerInRange(CollisionMap collisions) {
    for (Direction direction : Direction.values()) {
      for (int distance = 1; distance <= 2; distance++) {
        Vector2 position = this.position.sum(direction.step() * distance, 0);

        if (collisions.isSolid(position))
          break;

        if (collisions.getBodyAt(position, this).orElse(null) instanceof Player) {
          this.face(direction);
          this.attack();
          return true;
        }
      }
    }

    return false;
  }

  @Override
  public char getShape() {
    return this.isPreparing() ? 'e' : 'E';
  }
}
