package ufrn.imd.core.physics;

public record Vector2(
  int x,
  int y
) {
  //@ public normal_behavior
  //@ ensures this.x == x;
  //@ ensures this.y == y;
  //@ pure
  public Vector2 {};

  //@ public invariant Integer.MIN_VALUE <= x <= Integer.MAX_VALUE;
  //@ public invariant Integer.MIN_VALUE <= y <= Integer.MAX_VALUE;

  public static Vector2 ZERO = new Vector2(0, 0);

  //@ public static invariant ZERO.x == 0;
  //@ public static invariant ZERO.y == 0;

  //@ public normal_behavior
  //@ requires Integer.MIN_VALUE <= this.x + x <= Integer.MAX_VALUE;
  //@ requires Integer.MIN_VALUE <= this.y + y <= Integer.MAX_VALUE;
  //@ ensures \result.x == this.x + x;
  //@ ensures \result.y == this.y + y;
  public Vector2 sum(int x, int y) {
    return this.sum(new Vector2(x, y));
  }

  //@ public normal_behavior
  //@ requires Integer.MIN_VALUE <= this.x + vector.x <= Integer.MAX_VALUE;
  //@ requires Integer.MIN_VALUE <= this.y + vector.y <= Integer.MAX_VALUE;
  //@ ensures \result.x == this.x + vector.x;
  //@ ensures \result.y == this.y + vector.y;
  public Vector2 sum(Vector2 vector) {
    return new Vector2(
      this.x + vector.x,
      this.y + vector.y
    );
  }

  //@ public normal_behavior
  //@ requires Integer.MIN_VALUE <= x <= Integer.MAX_VALUE;
  //@ ensures \result.x == x;
  //@ ensures \result.y == this.y;
  public Vector2 withX(int x) {
    return new Vector2(x, this.y);
  }

  //@ public normal_behavior
  //@ requires Integer.MIN_VALUE <= y <= Integer.MAX_VALUE;
  //@ ensures \result.x == this.x;
  //@ ensures \result.y == y;
  public Vector2 withY(int y) {
    return new Vector2(this.x, y);
  }

  //@ public normal_behavior
  //@ requires Integer.MIN_VALUE <= x <= Integer.MAX_VALUE;
  //@ requires Integer.MIN_VALUE <= y <= Integer.MAX_VALUE;
  //@ ensures \result.x == x;
  //@ ensures \result.y == y;
  public Vector2 of(int x, int y) {
    return new Vector2(x, y);
  }
}