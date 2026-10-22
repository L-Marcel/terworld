package ufrn.imd.inputs;

import java.io.IOException;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import org.jline.terminal.Attributes;
import org.jline.terminal.Terminal;

public class KeyListener {
  private final ExecutorService executor;
  private final Terminal terminal;

  public KeyListener(
    ExecutorService executor,
    Terminal terminal
  ) {
    this.terminal = terminal;
    this.executor = executor;
  }

  public void listen(Consumer<KeyEvent> callback) {
    this.terminal.setKittyKeyboardMode(EnumSet.of(
      Terminal.KittyKeyboardMode.Disambiguate,
      Terminal.KittyKeyboardMode.ReportEvents
    ));

    this.executor.submit(() -> {
      Attributes config = this.terminal.enterRawMode();

      try {
        this.run(callback);
      } catch (Exception e) {
        Thread.currentThread().interrupt();
      } finally {
        terminal.setAttributes(config);
        terminal.setKittyKeyboardMode(EnumSet.noneOf(Terminal.KittyKeyboardMode.class));
      }
    });
  }

  public void run(Consumer<KeyEvent> callback) throws IOException {
    while (!Thread.currentThread().isInterrupted()) {
      int input = this.terminal.reader().read();
      if (input == -1) break;
      
      if (input == 27) {
        int next = this.terminal.reader().read();
        if (next == '[') {
          StringBuilder builder = new StringBuilder();
          int character;
          while ((character = terminal.reader().read()) != -1) {
            if (character >= 0x40 && character <= 0x7E) {
              if (character == 'u') {
                String seq = builder.toString();
                String[] parts = seq.split(";");

                int keyCode = Integer.parseInt(parts[0].split(":")[0]);
                int modifiers = 1;
                int eventType = 1;

                if (parts.length > 1) {
                  String[] modParts = parts[1].split(":");
                  modifiers = Integer.parseInt(modParts[0]);
                  if (modParts.length > 1) {
                    eventType = Integer.parseInt(modParts[1]);
                  }
                } else if (parts[0].contains(":")) {
                  eventType = Integer.parseInt(parts[0].split(":")[1]);
                }

                callback.accept(
                  new KeyEvent(
                    Key.of(keyCode, modifiers),
                    eventType,
                    true
                  )
                );
              }
              break;
            }
            builder.append((char) character);
          }
        } else if (next != -1) {
          callback.accept(new KeyEvent(Key.of(next), 1, false));
        }
      } else {
        callback.accept(new KeyEvent(Key.of(input), 1, false));
      }
    }
  }
}
