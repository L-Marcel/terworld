package ufrn.imd.core.enums;

public enum Direction {
  LEFT(-1), RIGHT(1);

  //@ spec_public
  private final int step;

  //@ normal_behavior
  //@ requires step == -1 || step == 1;
  //@ ensures this.step == step;
  //@ pure
  Direction(int step) {
    this.step = step;
  }

  //@ public normal_behavior
  //@ ensures \result == this.step;
  //@ spec_pure
  public int step() {
    return this.step;
  }

  //@ public normal_behavior
  //@ ensures (this == LEFT) ==> (\result == RIGHT);
  //@ ensures (this == RIGHT) ==> (\result == LEFT);
  //@ spec_pure
  public Direction opposite() {
    return this == LEFT ? RIGHT : LEFT;
  }
}
