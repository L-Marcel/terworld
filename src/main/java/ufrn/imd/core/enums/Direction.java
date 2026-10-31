package ufrn.imd.core.enums;

public enum Direction {
  LEFT(-1), RIGHT(1);

  private final int step;

  Direction(int step) {
    this.step = step;
  }

  public int step() {
    return this.step;
  }

  public Direction opposite() {
    return this == LEFT ? RIGHT : LEFT;
  }
}
