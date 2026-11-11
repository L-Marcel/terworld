package ufrn.imd.core.components;

import ufrn.imd.core.constants.Forces;
import ufrn.imd.core.constants.Ticks;
import ufrn.imd.core.physics.Vector2;

public class MovementComponent {
  //@ spec_public
  private double horizontalSpeed;

  //@ spec_public
  private double verticalSpeed;

  //@ spec_public
  private double horizontalDelay;

  //@ public invariant -Double.MAX_VALUE <= horizontalSpeed <= Double.MAX_VALUE;
  //@ public invariant Forces.JUMP_SPEED <= verticalSpeed <= Double.MAX_VALUE;
  //@ public invariant 0.0 <= horizontalDelay;

  //@ public normal_behavior
  //@ requires -Double.MAX_VALUE <= speed <= Double.MAX_VALUE;
  //@ ensures this.horizontalSpeed == speed;
  //@ assignable this.horizontalSpeed;
  public void setHorizontalSpeed(double speed) {
    this.horizontalSpeed = speed;
  }

  //@ public normal_behavior
  //@ ensures \result == this.verticalSpeed;
  //@ pure
  public double getVerticalSpeed() {
    return this.verticalSpeed;
  }

  //@ public normal_behavior
  //@ ensures this.verticalSpeed == 0.0;
  //@ assignable this.verticalSpeed;
  public void stopVertical() {
    this.verticalSpeed = 0.0;
  }

  //@ public normal_behavior
  //@ ensures this.verticalSpeed == Forces.JUMP_SPEED;
  //@ assignable this.verticalSpeed;
  public void jump() {
    this.verticalSpeed = Forces.JUMP_SPEED;
  }

  //@ public normal_behavior
  //@ ensures (\old(this.verticalSpeed) > Forces.DROP_SPEED) ==> (
  //@   this.verticalSpeed == \old(this.verticalSpeed)
  //@ );
  //@ ensures (\old(this.verticalSpeed) <= Forces.DROP_SPEED) ==> (
  //@   this.verticalSpeed == Forces.DROP_SPEED
  //@ );
  //@ assignable this.verticalSpeed;
  public void drop() {
    this.verticalSpeed = Math.max(
      Forces.DROP_SPEED,
      this.verticalSpeed
    );
  }

  //@ public normal_behavior
  //@ ensures (this.horizontalSpeed == 0.0) ==> (\result.x() == 0);
  //@ ensures (this.horizontalSpeed > 0.0) ==> (\result.x() >= 0);
  //@ ensures (this.horizontalSpeed < 0.0) ==> (\result.x() <= 0);
  //@ ensures (\result.y() == 0) <==> (this.verticalSpeed == 0.0);
  //@ ensures (\result.y() < 0) <==> (this.verticalSpeed < 0.0);
  //@ ensures (\result.y() > 0)<==> (this.verticalSpeed > 0.0);
  //@ assignable this.horizontalDelay, this.verticalSpeed;
  public Vector2 step(boolean grounded) {
    int horizontal = this.stepHorizontal();
    int vertical = this.stepVertical(grounded);
    return new Vector2(horizontal, vertical);
  }

  //@ private normal_behavior
  //@ ensures (this.horizontalSpeed == 0.0) ==> (\result == 0);
  //@ ensures (this.horizontalSpeed > 0.0) ==> (\result >= 0);
  //@ ensures (this.horizontalSpeed < 0.0) ==> (\result <= 0);
  //@ assignable this.horizontalDelay;
  private int stepHorizontal() {
    int horizontal = 0;

    this.horizontalDelay = Math.max(0, this.horizontalDelay - Ticks.STEP_SECONDS);
    if (this.horizontalSpeed != 0 && this.horizontalDelay <= Ticks.TIME_EPSILON) {
      horizontal = this.horizontalSpeed > 0 ? 1 : -1;
      this.horizontalDelay += 1.0 / Math.abs(this.horizontalSpeed);
    }

    return horizontal;
  }

  //@ private normal_behavior
  //@ ensures (\result == 0) <==> (this.verticalSpeed == 0.0);
  //@ ensures (\result == -1) <==> (this.verticalSpeed < 0.0);
  //@ ensures (\result == 1) <==> (this.verticalSpeed > 0.0);
  //@ assignable this.verticalSpeed;
  private int stepVertical(boolean grounded) {
    int vertical = 0;

    if (grounded && this.verticalSpeed >= 0.0)
      this.verticalSpeed = 0.0;
    else {
      double oldVerticalSpeed = this.verticalSpeed;
      this.verticalSpeed += Forces.GRAVITY * Ticks.STEP_SECONDS;

      if (this.verticalSpeed > 60.0)
        this.verticalSpeed = 60.0;

      if (oldVerticalSpeed < 0.0 && this.verticalSpeed >= 0.0)
        this.verticalSpeed = 0.0;
    }

    if (this.verticalSpeed > 0.0) {
      vertical = 1;
    } else if (this.verticalSpeed < 0.0) {
      vertical = -1;
    }

    return vertical;
  }
}
