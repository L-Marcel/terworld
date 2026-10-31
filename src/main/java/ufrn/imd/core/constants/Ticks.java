package ufrn.imd.core.constants;

public final class Ticks {
  public static final int UPDATES_PER_SECOND = 30;
  public static final double STEP_SECONDS = 1.0 / UPDATES_PER_SECOND;
  public static final int MAX_CATCH_UP_STEPS = 5;
  public static final double TIME_EPSILON = 1e-9;

  private Ticks() {}
}
