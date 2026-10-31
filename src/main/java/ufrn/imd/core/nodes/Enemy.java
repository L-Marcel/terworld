package ufrn.imd.core.nodes;

import ufrn.imd.core.abstractions.Fighter;
import ufrn.imd.core.constants.Health;
import ufrn.imd.core.enums.Action;
import ufrn.imd.core.enums.Direction;
import ufrn.imd.core.physics.CollisionMap;
import ufrn.imd.core.physics.Physics;
import ufrn.imd.core.physics.Vector2;

public class Enemy extends Fighter {
  public Enemy(Vector2 position) {
    super(position, 1, Health.ENEMY_MAX_HP);
  }

  @Override
  public void process(Action action, Physics physics) {
    CollisionMap collisions = physics.getCollisionMap();
    boolean attacking = this.attackIfPlayerInRange(collisions);
    this.patrol(physics, attacking);
    super.process(action, physics);

    if (!attacking)
      this.attackIfPlayerInRange(collisions);
  }

  private void patrol(Physics physics, boolean attacking) {
    Vector2 velocity = this.getVelocity().withX(0);
    if (!attacking && physics.isGrounded(this)) {
      CollisionMap collisions = physics.getCollisionMap();
      Vector2 next = this.position.sum(this.getFacing().step(), 0);

      if (collisions.isBlocked(next, this)
        || collisions.isDangerous(next)
        || !collisions.isSolid(next.sum(0, 1)))
        this.face(this.getFacing().opposite());
      else
        velocity = velocity.withX(this.getFacing().step());
    }

    this.setVelocity(velocity);
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
    return 'E';
  }
}
