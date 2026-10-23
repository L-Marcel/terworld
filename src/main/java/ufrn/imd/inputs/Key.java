package ufrn.imd.inputs;

import ufrn.imd.core.constants.Keys;

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

  @Override
  public String toString() {
    if (code == -1)
      return "Key(none)";
    return "Key(" + code + ")";
  }
}
