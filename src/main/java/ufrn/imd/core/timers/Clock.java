package ufrn.imd.core.timers;

public class Clock {
  private final double tickInterval;
  private double lastTick;

  public Clock(double ticksPerSecond) {
    this.tickInterval = 1000.0 / ticksPerSecond;
  }

  public boolean isTick() {
    long now = System.currentTimeMillis();
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
