package ufrn.imd.core.components;

import ufrn.imd.core.constants.Attacks;
import ufrn.imd.core.constants.Ticks;
import ufrn.imd.core.enums.AttackState;

public class AttackComponent {
  //@ spec_public
  private final double preparationSeconds;

  //@ spec_public
  private final double activeSeconds;

  //@ spec_public
  private final double recoverySeconds;

  //@ spec_public
  private double remainingSeconds;

  //@ spec_public
  private double bufferedSeconds;

  //@ spec_public
  private AttackState state;

  //@ public invariant 0.0 <= preparationSeconds <= Double.MAX_VALUE;
  //@ public invariant Ticks.STEP_SECONDS <= activeSeconds <= Double.MAX_VALUE;
  //@ public invariant Ticks.STEP_SECONDS <= recoverySeconds <= Double.MAX_VALUE;
  //@ public invariant 0.0 <= bufferedSeconds <= Attacks.INPUT_BUFFER_SECONDS;
  //@ public invariant -Double.MAX_VALUE <= remainingSeconds <= Double.MAX_VALUE;

  //@ public normal_behavior
  //@ requires 0.0 <= preparationSeconds <= Double.MAX_VALUE;
  //@ requires 0.0 <= activeSeconds <= Double.MAX_VALUE;
  //@ requires 0.0 <= recoverySeconds <= Double.MAX_VALUE;
  //@ ensures this.preparationSeconds >= 0;
  //@ ensures this.preparationSeconds >= preparationSeconds;
  //@ ensures this.activeSeconds >= Ticks.STEP_SECONDS;
  //@ ensures this.activeSeconds >= activeSeconds;
  //@ ensures this.recoverySeconds >= Ticks.STEP_SECONDS;
  //@ ensures this.recoverySeconds >= recoverySeconds;
  //@ ensures this.remainingSeconds == 0;
  //@ ensures this.bufferedSeconds == 0;
  //@ ensures this.state == AttackState.IDLE;
  //@ pure
  public AttackComponent(
    double preparationSeconds,
    double activeSeconds,
    double recoverySeconds
  ) {
    this.preparationSeconds = Math.max(0, preparationSeconds);
    this.activeSeconds = Math.max(Ticks.STEP_SECONDS, activeSeconds);
    this.recoverySeconds = Math.max(Ticks.STEP_SECONDS, recoverySeconds);
    this.remainingSeconds = 0;
    this.bufferedSeconds = 0;
    this.state = AttackState.IDLE;
  }

  //@ public normal_behavior
  //@ ensures \result == (\old(this.state) == AttackState.IDLE);
  //@ ensures (!\result) ==> (this.bufferedSeconds == Attacks.INPUT_BUFFER_SECONDS);
  //@ assignable this.bufferedSeconds, this.state, this.remainingSeconds;
  public boolean request() {
    if (this.isIdle()) {
      this.start();
      return true;
    }

    this.bufferedSeconds = Attacks.INPUT_BUFFER_SECONDS;
    return false;
  }

  //@ private normal_behavior
  //@ ensures this.bufferedSeconds == 0.0;
  //@ ensures (this.preparationSeconds > 0) ==> this.state == AttackState.PREPARING;
  //@ ensures (this.preparationSeconds == 0) ==> this.state == AttackState.ACTIVE;
  //@ ensures (this.preparationSeconds > 0) ==> this.remainingSeconds == this.preparationSeconds;
  //@ ensures (this.preparationSeconds == 0) ==> this.remainingSeconds == this.activeSeconds;
  //@ assignable this.bufferedSeconds, this.state, this.remainingSeconds;
  private void start() {
    this.bufferedSeconds = 0.0;
    this.state = this.preparationSeconds > 0 ? AttackState.PREPARING : AttackState.ACTIVE;
    this.remainingSeconds = this.preparationSeconds > 0 ? this.preparationSeconds : this.activeSeconds;
  }

  //@ public normal_behavior
  //@ assignable bufferedSeconds, state, remainingSeconds;
  public void beginTick() {
    if (!this.isIdle()) {
      this.remainingSeconds -= Ticks.STEP_SECONDS;
      //@ loop_invariant -Double.MAX_VALUE <= remainingSeconds <= Double.MAX_VALUE;
      //@ loop_assignable state, remainingSeconds;
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

  //@ public normal_behavior
  //@ ensures \result == (this.state == AttackState.IDLE);
  //@ pure
  public boolean isIdle() {
    return this.state == AttackState.IDLE;
  }

  //@ public normal_behavior
  //@ ensures \result == (this.state == AttackState.PREPARING);
  //@ pure
  public boolean isPreparing() {
    return this.state == AttackState.PREPARING;
  }

  //@ public normal_behavior
  //@ ensures \result == (this.state == AttackState.ACTIVE);
  //@ pure
  public boolean isAttacking() {
    return this.state == AttackState.ACTIVE;
  }
}
