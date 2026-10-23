package ufrn.imd.inputs;

import java.util.Objects;

public record Key(
  char character, 
  int code
) {
  public static Key of(int code) {
    return new Key(
      (char) code,
      code
    );
  }

  public static Key of(char character) {
    return new Key(
      character,
      (int) character
    );
  }

  public boolean equals(Key other) {
    return this.character == other.character &&
      this.code == other.code;
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
      this.code
    );
  }
}
