package ufrn.imd.core.abstractions;

import ufrn.imd.core.constants.Forces;
import ufrn.imd.core.enums.Action;
import ufrn.imd.core.physics.Physics;
import ufrn.imd.core.physics.Vector2;

public abstract class Body2D extends Node2D {
  private Vector2 velocity;

  public Body2D(Vector2 position, int layer) {
    super(position, layer);
    this.velocity = Vector2.ZERO;
  }

  protected final void move(Physics physics) {
    this.position = physics.move(this, this.velocity);
  }

  protected final void jump(Physics physics) {
    if (this.velocity.y() >= 0 && physics.isGrounded(this))
      this.velocity = this.velocity.withY(Forces.JUMP_HEIGHT);
  }

  @Override
  public void process(Action action, Physics physics) {
    this.move(physics);

    if (this.isFreePending())
      return;

    if (!physics.isGrounded(this))
      this.velocity = this.velocity.sum(0, Forces.GRAVITY);
    else
      this.velocity = this.velocity.withY(0);
  }

  public void setVelocity(Vector2 velocity) {
    this.velocity = velocity;
  }

  public Vector2 getVelocity() {
    return this.velocity;
  }
}
