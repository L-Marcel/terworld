package ufrn.imd.core.timers;

import java.util.concurrent.locks.ReentrantLock;

public class Clock {
  private final ReentrantLock reetrantLock;
  private final double forcedTickInterval;
  private double lastForcedTick;
  private final double tickInterval;
  private double lastTick;
  private boolean forceTick;

  public Clock(double ticksPerSecond, double forcedTicksPerSecond) {
    this.reetrantLock = new ReentrantLock();
    this.tickInterval = 1000.0 / ticksPerSecond;
    this.forcedTickInterval = 1000.0 / forcedTicksPerSecond;
    this.lastTick = 0;
    this.lastForcedTick = 0;
    this.forceTick = false;
  }

  public void tryLock() {
    while (!this.reetrantLock.tryLock());
  }

  public void lock() {
    this.reetrantLock.lock();
  }

  public void unlock() {
    this.reetrantLock.unlock();
  }
  
  public void forceTick() {
    this.lock();

    long now = System.currentTimeMillis();
    double diff = now - this.lastForcedTick;

    if (diff >= this.forcedTickInterval) {
      this.forceTick = true;
      this.lastForcedTick = now;
    }

    this.unlock();
  }

  public boolean isTick() {
    long now = System.currentTimeMillis();

    if (this.forceTick) {
      this.forceTick = false;
      this.lastTick = now;
      return true;
    }

    double diff = now - this.lastTick;

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
    return this.lastTick;
  }
}
