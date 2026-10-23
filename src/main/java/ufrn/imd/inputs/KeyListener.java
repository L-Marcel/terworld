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
    this.executor.submit(() -> this.run(callback));
  }

  private void run(Consumer<Key> callback) {
    Attributes config = this.terminal.enterRawMode();

    try {
      while (!Thread.currentThread().isInterrupted()) {
        int code = this.terminal.reader().read();
        Key key = Key.of(code);
        callback.accept(key);
      }
    } catch (Exception e) {
      Thread.currentThread().interrupt();
    } finally {
      terminal.setAttributes(config);
    }
  }
}
