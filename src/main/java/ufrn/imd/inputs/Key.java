package ufrn.imd.inputs;

import java.util.Set;

public record Key(int code) {
  private static final Set<Integer> VALID_CODES = Set.of(
    3, // Control + C
    (int) 'a',
    (int) 's',
    (int) 'd',
    (int) 'j',
    (int) 'k',
    (int) 'l'
  );

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
    return VALID_CODES.contains(this.code);
  }

  @Override
  public String toString() {
    if (code == -1)
      return "Key(none)";
    return "Key(" + code + ")";
  }
}
