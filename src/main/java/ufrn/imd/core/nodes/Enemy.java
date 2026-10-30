package ufrn.imd.core.nodes;

import ufrn.imd.core.Action;
import ufrn.imd.core.Physics;
import ufrn.imd.core.Vector2;
import ufrn.imd.core.abstractions.Body2D;

public class Enemy extends Body2D {
  private boolean flipped = false;
  private Sword sword;

  public Enemy(Vector2 position) {
    super(position, 1);
    this.sword = new Sword(position.sum(1, 0));
    this.addChild(this.sword);
  }

  @Override
  public void process(Action action, Physics physics) {
    Vector2 velocity = this.getVelocity().withX(0);
    boolean attacking = this.attackIfPlayerInRange(physics);

    if (!attacking && physics.isGrounded(this)) {
      int direction = this.flipped ? -1 : 1;
      Vector2 next = this.position.sum(direction, 0);

      if (physics.isBlocked(next, this)
        || physics.isDangerous(next)
        || !physics.isSolid(next.sum(0, 1)))
        this.flipped = !this.flipped;
      else
        velocity = velocity.withX(direction);
    }

    this.setVelocity(velocity);
    super.process(action, physics);

    if (!attacking)
      this.attackIfPlayerInRange(physics);
    this.sword.setFlipped(this.flipped);
    this.sword.setPosition(
      this.position.sum(this.flipped ? -1 : 1, 0)
    );
  }

  private boolean attackIfPlayerInRange(Physics physics) {
    for (int direction = -1; direction <= 1; direction += 2) {
      for (int distance = 1; distance <= 2; distance++) {
        Vector2 position = this.position.sum(direction * distance, 0);

        if (physics.isSolid(position))
          break;

        if (physics.getBodyAt(position, this).filter(Player.class::isInstance).isPresent()) {
          this.flipped = direction < 0;
          this.sword.attack();
          return true;
        }
      }
    }

    return false;
  }

  @Override
  public Vector2 getPosition() {
    return this.position;
  }

  @Override
  public char getShape() {
    return 'E';
  }
}
