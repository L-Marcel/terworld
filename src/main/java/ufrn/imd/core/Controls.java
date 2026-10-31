package ufrn.imd.core;

public record Controls(
  int horizontal,
  boolean jump,
  boolean attack,
  boolean drop
) {
  public Controls { horizontal = Integer.signum(horizontal); }

  public static Controls none() {
    return new Controls(0, false, false, false);
  }
}
