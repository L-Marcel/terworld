package ufrn.imd.core.abstractions;

import ufrn.imd.core.Controls;
import ufrn.imd.core.components.MovementComponent;
import ufrn.imd.core.physics.Physics;
import ufrn.imd.core.physics.Vector2;

public abstract class Body2D extends Node2D {
  private final MovementComponent movement;

  protected Body2D(Vector2 position, int layer) {
    super(position, layer);
    this.movement = new MovementComponent();
  }

  protected final void jump() {
    this.movement.jump();
  }

  protected final void drop() {
    this.movement.drop();
  }

  @Override
  public void process(Controls controls, Physics physics) {
    Vector2 displacement = this.movement.step(physics.isGrounded(this));
    Vector2 previous = this.position;
    this.position = physics.move(this, displacement);

    if (this.isFreePending())
      return;

    if (this.position.y() - previous.y() != displacement.y()
      || physics.isGrounded(this) && this.getVerticalSpeed() >= 0)
      this.movement.stopVertical();
  }

  protected final void setHorizontalSpeed(double speed) {
    this.movement.setHorizontalSpeed(speed);
  }

  public final double getVerticalSpeed() {
    return this.movement.getVerticalSpeed();
  }
}
