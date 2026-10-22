package ufrn.imd.inputs;

import java.util.Objects;

public record Key(
  char character, 
  int keyCode,
  int modifier
) {
  public static Key of(int keyCode) {
    return Key.of(keyCode, 1);
  }

  public static Key of(int keyCode, int modifier) {
    return new Key(
      Character.toLowerCase((char) keyCode), 
      keyCode,
      modifier
    );
  }

  // TODO : Esses modificadores devem estar errados
  public boolean isShift() {
    int mask = this.modifier - 1;
    return (mask & 1) != 0;
  }

  public boolean isAlt() {
    int mask = this.modifier - 1;
    return (mask & 2) != 0;
  }

  public boolean isControl() {
    int mask = this.modifier - 1;
    return (mask & 4) != 0;
  }

  public boolean isSuper() {
    int mask = this.modifier - 1;
    return (mask & 8) != 0;
  }

  public boolean isAltGr() {
    int mask = this.modifier - 1;
    return (mask & 16) != 0;
  }

  public boolean equals(Key other) {
    return this.character == other.character &&
      this.keyCode == other.keyCode &&
      this.modifier == other.modifier;
  }

  public boolean equals(Object other) {
    if (other == null) return false;
    if (this == other) return true;
    if (other instanceof Key key)
      return this.equals(key);
    return false;
  }

  public int hashCode() {
    return Objects.hash(
      this.character, 
      this.keyCode, 
      this.modifier
    );
  }
}
