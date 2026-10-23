package ufrn.imd;

import java.util.concurrent.locks.ReentrantLock;

public class Clock {
  private final ReentrantLock reetrantLock;
  private final double forcedTickInterval;
  private long lastForcedTick;
  private final double tickInterval;
  private long lastTick;
  private boolean forceTick;

  public Clock(double ticksPerSecond, double forcedTicksPerSecond) {
    long now = System.nanoTime();
    this.reetrantLock = new ReentrantLock();
    this.tickInterval = 1000.0 / ticksPerSecond;
    this.forcedTickInterval = 1000.0 / forcedTicksPerSecond;
    this.lastTick = now - (long) Math.ceil(this.tickInterval * 1_000_000.0);
    this.lastForcedTick = now - (long) Math.ceil(this.forcedTickInterval * 1_000_000.0);
    this.forceTick = false;
  }
 
  public void lock() {
    this.reetrantLock.lock();
  }

  public void unlock() {
    this.reetrantLock.unlock();
  }

  public void forceTick() {
    this.lock();

    try {
      long now = System.nanoTime();
      double diff = (now - this.lastForcedTick) / 1_000_000.0;

      if (diff >= this.forcedTickInterval) {
        this.forceTick = true;
        this.lastForcedTick = now;
      }
    } finally {
      this.unlock();
    }
  }

  public boolean isTick() {
    long now = System.nanoTime();

    if (this.forceTick) {
      this.forceTick = false;
      this.lastTick = now;
      return true;
    }

    double diff = (now - this.lastTick) / 1_000_000.0;

    if (diff >= this.tickInterval) {
      this.lastTick = now;
      return true;
    }

    return false;
  }

  public double getTickInterval() {
    return this.tickInterval;
  }

  public double getLastTick() {
    return this.lastTick / 1_000_000.0;
  }
}
