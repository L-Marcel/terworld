package ufrn.imd.core.components;

import ufrn.imd.core.constants.Attacks;
import ufrn.imd.core.constants.Ticks;
import ufrn.imd.core.enums.AttackState;

public class AttackComponent {
  private final double preparationSeconds;
  private final double activeSeconds;
  private final double recoverySeconds;
  private AttackState state;
  private double remainingSeconds;
  private double bufferedSeconds;

  public AttackComponent(double preparationSeconds, double activeSeconds, double recoverySeconds) {
    this.preparationSeconds = Math.max(0, preparationSeconds);
    this.activeSeconds = Math.max(Ticks.STEP_SECONDS, activeSeconds);
    this.recoverySeconds = Math.max(Ticks.STEP_SECONDS, recoverySeconds);
    this.state = AttackState.IDLE;
  }

  public boolean request() {
    if (this.isIdle()) {
      this.start();
      return true;
    }

    this.bufferedSeconds = Attacks.INPUT_BUFFER_SECONDS;
    return false;
  }

  private void start() {
    this.bufferedSeconds = 0;
    this.state = this.preparationSeconds > 0 ? AttackState.PREPARING : AttackState.ACTIVE;
    this.remainingSeconds = this.preparationSeconds > 0 ? this.preparationSeconds : this.activeSeconds;
  }

  public void beginTick() {
    if (!this.isIdle()) {
      this.remainingSeconds -= Ticks.STEP_SECONDS;
      while (!this.isIdle() && this.remainingSeconds <= Ticks.TIME_EPSILON) {
        switch (this.state) {
          case PREPARING:
            this.state = AttackState.ACTIVE;
            this.remainingSeconds += this.activeSeconds;
            break;
          case ACTIVE:
            this.state = AttackState.RECOVERING;
            this.remainingSeconds += this.recoverySeconds;
            break;
          case RECOVERING:
            this.state = AttackState.IDLE;
            break;
          default:
            break;
        }
      }
    }

    if (this.isIdle() && this.bufferedSeconds > Ticks.TIME_EPSILON)
      this.start();
    else
      this.bufferedSeconds = Math.max(0, this.bufferedSeconds - Ticks.STEP_SECONDS);
  }

  public boolean isIdle() {
    return this.state == AttackState.IDLE;
  }

  public boolean isPreparing() {
    return this.state == AttackState.PREPARING;
  }

  public boolean isAttacking() {
    return this.state == AttackState.ACTIVE;
  }
}
