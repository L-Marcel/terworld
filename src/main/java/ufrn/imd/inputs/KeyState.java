package ufrn.imd.inputs;

public enum KeyState {
  JUST_PRESSED,
  PRESSED,
  JUST_RELEASED,
  NONE;

  public boolean isPressed() {
    return this == JUST_PRESSED || this == PRESSED;
  }
}
