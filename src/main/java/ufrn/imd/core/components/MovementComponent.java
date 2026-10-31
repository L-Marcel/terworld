package ufrn.imd.core.components;

import ufrn.imd.core.constants.Forces;
import ufrn.imd.core.constants.Ticks;
import ufrn.imd.core.physics.Vector2;

public class MovementComponent {
  private double horizontalSpeed;
  private double verticalSpeed;
  private double horizontalDelay;
  private double verticalRemainder;

  public void setHorizontalSpeed(double speed) {
    this.horizontalSpeed = speed;
  }

  public double getVerticalSpeed() {
    return this.verticalSpeed;
  }

  public void jump() {
    this.verticalSpeed = Forces.JUMP_SPEED;
    this.verticalRemainder = 0;
  }

  public void drop() {
    this.verticalSpeed = Math.max(Forces.DROP_SPEED, this.verticalSpeed);
  }

  public void stopVertical() {
    this.verticalSpeed = 0;
    this.verticalRemainder = 0;
  }

  public Vector2 step(boolean grounded) {
    int horizontal = 0;
    this.horizontalDelay = Math.max(0, this.horizontalDelay - Ticks.STEP_SECONDS);
    if (this.horizontalSpeed != 0 && this.horizontalDelay <= Ticks.TIME_EPSILON) {
      horizontal = this.horizontalSpeed > 0 ? 1 : -1;
      this.horizontalDelay += 1.0 / Math.abs(this.horizontalSpeed);
    }

    if (grounded && this.verticalSpeed >= 0)
      this.stopVertical();
    else
      this.verticalSpeed += Forces.GRAVITY * Ticks.STEP_SECONDS;

    this.verticalRemainder += this.verticalSpeed * Ticks.STEP_SECONDS;
    int vertical = (int) Math.round(this.verticalRemainder);
    this.verticalRemainder -= vertical;
    return new Vector2(horizontal, vertical);
  }
}
