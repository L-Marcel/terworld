package ufrn.imd.core.timers;

public class Clock {
  private int frame = 0;

  public boolean isTick() {
    if (System.currentTimeMillis() % 16 == 0) {
      frame += 1;
      frame %= 60;
      return true;
    }

    return false;
  }

  public int getFrame() {
    return this.frame;
  }
}
