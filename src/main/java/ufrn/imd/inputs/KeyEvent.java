package ufrn.imd.inputs;

public record KeyEvent(
  Key key, 
  int event,
  boolean explicitRelease
) {
  public KeyEvent(Key key, int event) {
    this(key, event, false);
  }
}
