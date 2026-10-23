package ufrn.imd.inputs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import org.jline.terminal.Attributes;
import org.jline.terminal.Terminal;
import org.jline.utils.NonBlockingReader;

public class KeyListener implements AutoCloseable {
  private final ExecutorService executor;
  private final Terminal terminal;
  private Key pressed;

  public KeyListener(Terminal terminal) {
    this.pressed = Key.none();
    this.terminal = terminal;
    this.executor = Executors.newVirtualThreadPerTaskExecutor();
  }

  public void listen(Consumer<Key> callback) {
    this.executor.submit(() -> this.run(callback));
  }

  private void run(Consumer<Key> callback) {
    Attributes config = this.terminal.enterRawMode();

    try {
      while (!Thread.currentThread().isInterrupted()) {
        int code = this.terminal.reader().read(100);
        if (code == NonBlockingReader.EOF) break;
        else if (code == NonBlockingReader.READ_EXPIRED) continue;

        Key key = Key.of(code);
        
        if (key.isValid()) 
          callback.accept(key);
      }
    } catch (Exception e) {
      Thread.currentThread().interrupt();
    } finally {
      terminal.setAttributes(config);
    }
  }

  public Key getPressedKey() {
    return this.pressed;
  }

  public void setPressedKey(Key key) {
    this.pressed = key;
  }

  @Override
  public void close() {
    this.executor.shutdownNow();
    this.executor.close();
  }
}
