package ufrn.imd.inputs;

import ufrn.imd.core.Controls;

public class Keyboard {
  private boolean left;
  private boolean right;
  private boolean down;
  private int lastDirection;
  private long leftExpires;
  private long rightExpires;
  private long downExpires;
  private boolean jump;
  private boolean attack;
  private boolean exit;

  public void accept(Key key, long now) {
    boolean released = key.event() == KeyEvent.RELEASE;
    long expires = key.event() == KeyEvent.TEXT ? now + Keys.MOVEMENT_PULSE_NANOS : Long.MAX_VALUE;

    if (key.is(3) && !released)
      this.exit = true;

    switch (key.code()) {
      case 'a':
        this.left = !released;
        this.leftExpires = expires;
        if (!released)
          this.lastDirection = -1;
        break;
      case 'd':
        this.right = !released;
        this.rightExpires = expires;
        if (!released)
          this.lastDirection = 1;
        break;
      case 's':
        this.down = !released;
        this.downExpires = expires;
        break;
      case 'k':
        if (!released && key.event() != KeyEvent.REPEAT)
          this.jump = true;
        break;
      case 'j':
        if (!released)
          this.attack = true;
        break;
      default:
        break;
    }
  }

  public Controls poll(long now) {
    this.left &= now < this.leftExpires;
    this.right &= now < this.rightExpires;
    this.down &= now < this.downExpires;

    int horizontal = this.left && this.right ? this.lastDirection : this.left ? -1 : this.right ? 1 : 0;
    Controls controls = new Controls(horizontal, this.jump, this.attack, this.down);

    this.jump = false;
    this.attack = false;
    return controls;
  }

  public Controls read(KeyListener listener) {
    long now = System.nanoTime();

    for (Key key = listener.poll(); key.isValid(); key = listener.poll())
      this.accept(key, now);

    return this.poll(now);
  }

  public boolean isExitRequested() {
    return this.exit;
  }
}
