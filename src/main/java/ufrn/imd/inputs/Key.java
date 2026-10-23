package ufrn.imd.inputs;

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
}
