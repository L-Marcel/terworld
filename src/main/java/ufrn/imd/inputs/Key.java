package ufrn.imd.inputs;

import ufrn.imd.core.enums.Action;

public record Key(int code) {

  public static Key none() {
    return new Key(-1);
  }

  public static Key of(int code) {
    return new Key(code);
  }

  public static Key of(char character) {
    return new Key(
      (int) character
    );
  }

  public boolean is(char character) {
    return this.is((int) character);
  }

  public boolean is(int code) {
    return this.code == code;
  }

  public boolean isValid() {
    return Keys.VALIDS.contains(this.code);
  }

  public Action toAction() {
    return switch (this.code) {
      case 'a' -> Action.LEFT;
      case 'd' -> Action.RIGHT;
      case 'k' -> Action.JUMP;
      case 's' -> Action.DROP;
      case 'j' -> Action.ATTACK;
      default -> Action.NONE;
    };
  }

  @Override
  public String toString() {
    if (code == -1)
      return "Key(none)";
    return "Key(" + code + ")";
  }
}
