package ufrn.imd.inputs;

public record Key(
  char character, 
  int code
) {
  public static Key of(int code) {
    return new Key(Character.toLowerCase((char) code), code);
  }

  public boolean equals(char character) {
    return this.character == character;
  }

  public boolean equals(int code) {
    return this.code == code;
  }

  public boolean equals(Key other) {
    return this.equals(other.character) && 
      this.equals(other.code);
  }
}
