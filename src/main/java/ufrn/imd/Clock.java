package ufrn.imd;

import java.util.concurrent.locks.LockSupport;
import ufrn.imd.core.constants.Ticks;

public class Clock {
  private final long intervalNanos;
  private long nextTick;

  public Clock(double ticksPerSecond) {
    this.intervalNanos = Math.max(
      1,
      Math.round(
        1_000_000_000.0 /
          Math.max(1, ticksPerSecond)
      )
    );

    this.nextTick = System.nanoTime();
  }

  public boolean isTick() {
    long now = System.nanoTime();
    if (now < this.nextTick)
      return false;

    if (now - this.nextTick > this.intervalNanos * Ticks.MAX_CATCH_UP_STEPS)
      this.nextTick = now;

    this.nextTick += this.intervalNanos;
    return true;
  }

  public void awaitNextTick() {
    while (!Thread.currentThread().isInterrupted()) {
      long remaining = this.nextTick - System.nanoTime();
      if (remaining <= 0)
        return;
      LockSupport.parkNanos(remaining);
    }
  }
}
