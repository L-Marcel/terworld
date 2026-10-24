package ufrn.imd.core;

public record Vector2(
  int x,
  int y
) {
  public static Vector2 ZERO = new Vector2(0, 0);

  public Vector2 sum(int x, int y) {
    return this.sum(new Vector2(x, y));
  }

  public Vector2 sum(Vector2 vector) {
    return new Vector2(
      this.x + vector.x,
      this.y + vector.y
    );
  }

  public Vector2 withX(int x) {
    return new Vector2(x, this.y);
  }

  public Vector2 withY(int y) {
    return new Vector2(this.x, y);
  }

  public Vector2 of(int x, int y) {
    return new Vector2(x, y);
  }
}