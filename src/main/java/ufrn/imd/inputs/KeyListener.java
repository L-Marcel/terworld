package ufrn.imd.inputs;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jline.terminal.Attributes;
import org.jline.terminal.Terminal;
import org.jline.utils.NonBlockingReader;

public class KeyListener implements AutoCloseable {
  private final ExecutorService executor;
  private final Terminal terminal;
  private final Attributes originalAttributes;
  private final Queue<Key> keys;
  private final KeyDecoder decoder;
  private volatile boolean running;

  public KeyListener(Terminal terminal) {
    this.terminal = terminal;
    this.originalAttributes = terminal.enterRawMode();
    this.executor = Executors.newVirtualThreadPerTaskExecutor();
    this.keys = new ConcurrentLinkedQueue<>();
    this.decoder = new KeyDecoder();
  }

  public void listen() {
    this.running = true;
    this.executor.submit(this::run);
  }

  private void run() {
    try {
      while (this.running && !Thread.currentThread().isInterrupted()) {
        int code = this.terminal.reader().read(100);

        if (code == NonBlockingReader.EOF) {
          this.keys.add(Key.of(3));
          break;
        }

        if (code == NonBlockingReader.READ_EXPIRED) {
          this.decoder.reset();
          continue;
        }

        Key key = this.decoder.accept(code);
        if (key.isValid())
          this.keys.add(key);
      }
    } catch (Exception e) {
      this.keys.add(Key.of(3));
      Thread.currentThread().interrupt();
    }
  }

  public Key poll() {
    Key key = this.keys.poll();
    return key == null ? Key.none() : key;
  }

  @Override
  public void close() {
    this.running = false;
    this.executor.shutdownNow();
    try {
      this.executor.close();
    } finally {
      this.terminal.setAttributes(this.originalAttributes);
    }
  }
}
