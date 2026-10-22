package ufrn.imd.core.timers;

public class Clock {
  private int frame = 0;

  private long lastTick = 0;

  public boolean isTick() {
    long now = System.currentTimeMillis();
    if (now - this.lastTick >= 16) {
      this.lastTick = now;
      this.frame = (this.frame + 1) % 60;
      return true;
    }

    return false;
  }

  public int getFrame() {
    return this.frame;
  }
}
