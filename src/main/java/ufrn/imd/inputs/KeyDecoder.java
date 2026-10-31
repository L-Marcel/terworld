package ufrn.imd.inputs;

public class KeyDecoder {
  private final StringBuilder sequence;

  public KeyDecoder() {
    this.sequence = new StringBuilder();
  }

  public Key accept(int code) {
    if (code == 3) {
      this.reset();
      return Key.of(3);
    }

    if (this.sequence.isEmpty()) {
      if (code == 27) {
        this.sequence.append((char) code);
        return Key.none();
      }
      return Key.of(code);
    }

    if (this.sequence.length() == 1 && code != '[') {
      this.reset();
      return Key.of(code);
    }

    this.sequence.append((char) code);
    if (code == 'u') {
      Key key = this.decode();
      this.reset();
      return key;
    }

    if (this.sequence.length() > Keys.MAX_SEQUENCE_LENGTH
      || this.sequence.length() > 2 && code >= '@' && code <= '~')
      this.reset();

    return Key.none();
  }

  private Key decode() {
    String[] fields = this.sequence.substring(2, this.sequence.length() - 1).split(";", -1);

    int code = number(fields[0].split(":", -1)[0]);
    String[] modifiers = fields.length > 1 ? fields[1].split(":", -1) : new String[] { "1" };

    int modifier = number(modifiers[0]);
    int event = modifiers.length > 1 ? number(modifiers[1]) : 1;

    if (modifier < 1)
      return Key.none();

    if (code == 'c' && ((modifier - 1) & 4) != 0)
      code = 3;

    KeyEvent type = switch (event) {
      case 1 -> KeyEvent.PRESS;
      case 2 -> KeyEvent.REPEAT;
      case 3 -> KeyEvent.RELEASE;
      default -> KeyEvent.TEXT;
    };

    return code < 0 || event < 1 || event > 3 ? Key.none() : new Key(code, type);
  }

  private static int number(String value) {
    if (value.isEmpty())
      return -1;

    int result = 0;
    for (int i = 0; i < value.length(); i++) {
      char digit = value.charAt(i);
      if (digit < '0' || digit > '9' || result > 100_000)
        return -1;
      result = result * 10 + digit - '0';
    }

    return result;
  }

  public void reset() {
    this.sequence.setLength(0);
  }
}
