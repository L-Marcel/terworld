package ufrn.imd.core.abstractions;

import ufrn.imd.core.Controls;
import ufrn.imd.core.components.MovementComponent;
import ufrn.imd.core.physics.Physics;
import ufrn.imd.core.physics.Vector2;

public abstract class Body2D extends Node2D {
  //@ spec_public
  private final MovementComponent movement;

  //@ protected normal_behavior
  //@ ensures this.position == position;
  //@ ensures this.layer == layer;
  //@ ensures !freeIsPending;
  //@ ensures children.size() == 0;
  //@ ensures \fresh(children);
  //@ ensures parent == null;
  //@ ensures \fresh(this.movement);
  //@ ensures this.movement.horizontalSpeed == 0.0;
  //@ ensures this.movement.verticalSpeed == 0.0;
  //@ ensures this.movement.horizontalDelay == 0.0;
  //@ pure
  protected Body2D(Vector2 position, int layer) {
    super(position, layer);
    this.movement = new MovementComponent();
  }

  //@ protected normal_behavior
  //@ ensures this.movement.verticalSpeed == ufrn.imd.core.constants.Forces.JUMP_SPEED;
  //@ assignable this.movement.verticalSpeed;
  protected final void jump() {
    this.movement.jump();
  }

  //@ protected normal_behavior
  //@ ensures (\old(this.movement.verticalSpeed) > ufrn.imd.core.constants.Forces.DROP_SPEED) ==>
  //@   this.movement.verticalSpeed == \old(this.movement.verticalSpeed);
  //@ ensures (\old(this.movement.verticalSpeed) <= ufrn.imd.core.constants.Forces.DROP_SPEED) ==>
  //@   this.movement.verticalSpeed == ufrn.imd.core.constants.Forces.DROP_SPEED;
  //@ assignable this.movement.verticalSpeed;
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

  //@ protected normal_behavior
  //@ requires -Double.MAX_VALUE <= speed <= Double.MAX_VALUE;
  //@ ensures this.movement.horizontalSpeed == speed;
  //@ assignable this.movement.horizontalSpeed;
  protected final void setHorizontalSpeed(double speed) {
    this.movement.setHorizontalSpeed(speed);
  }

  //@ public normal_behavior
  //@ ensures \result == this.movement.verticalSpeed;
  //@ pure
  public final double getVerticalSpeed() {
    return this.movement.getVerticalSpeed();
  }
}
