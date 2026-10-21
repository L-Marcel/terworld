package ufrn.imd.inputs;

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

  public void listen(Consumer<Key> callback) {
    this.executor.submit(() -> {
      this.run(callback);
    });
  }

  public void run(Consumer<Key> callback) {
    try {
      Attributes config = this.terminal.enterRawMode();

      try {
        while (!Thread.currentThread().isInterrupted()) {
          int code = terminal.reader().read();
          if (code == -1) break;
          callback.accept(Key.of(code)); 
        }
      } finally {
        terminal.setAttributes(config);
      }
    } catch (Exception e) {
      Thread.currentThread().interrupt();
    }
  }
}
