package ufrn.imd.core.components;

import ufrn.imd.core.constants.Jumps;
import ufrn.imd.core.constants.Ticks;

public class JumpComponent {
  private double bufferedSeconds;
  private double coyoteSeconds;

  public boolean process(boolean requested, boolean grounded) {
    this.bufferedSeconds = requested ? Jumps.INPUT_BUFFER_SECONDS
      : Math.max(0, this.bufferedSeconds - Ticks.STEP_SECONDS);
    this.coyoteSeconds = grounded ? Jumps.COYOTE_SECONDS
      : Math.max(0, this.coyoteSeconds - Ticks.STEP_SECONDS);

    if (this.bufferedSeconds <= Ticks.TIME_EPSILON || this.coyoteSeconds <= Ticks.TIME_EPSILON)
      return false;

    this.bufferedSeconds = 0;
    this.coyoteSeconds = 0;
    return true;
  }
}
