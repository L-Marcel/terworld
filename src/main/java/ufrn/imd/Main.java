package ufrn.imd;

import java.io.IOException;
import java.net.URISyntaxException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;
import ufrn.imd.core.Camera;
import ufrn.imd.core.Game;
import ufrn.imd.core.Screen;
import ufrn.imd.inputs.Key;
import ufrn.imd.inputs.KeyListener;

public class Main {
  public static void main(String[] args) throws IOException, URISyntaxException {
    Clock clock = new Clock(1, 4);
    Loader loader = new Loader();
    Game game = new Game();

    game.setLevels(loader.load());
    game.nextLevel();

    try (
    //@formatter:off
      Terminal terminal = TerminalBuilder.terminal();
      KeyListener listener = new KeyListener(terminal)
    //@formatter:on
    ) {
      listener.listen(key -> {
        clock.lock();

        try {
          listener.setPressedKey(key);
          clock.forceTick();
        } finally {
          clock.unlock();
        }
      });

      terminal.puts(Capability.enter_ca_mode);

      while (!Thread.currentThread().isInterrupted()) {
        clock.lock();

        try {
          if (clock.isTick()) {
            terminal.puts(Capability.clear_screen);
            terminal.flush();

            if (listener.getPressedKey().is(3))
              break;

            game.process(listener.getPressedKey());

            Screen screen = game.getScreen(
              terminal.getColumns(),
              terminal.getRows()
            );

            screen.content().forEach(terminal.writer()::println);
            terminal.writer().flush();

            listener.setPressedKey(Key.none());
          }
        } finally {
          clock.unlock();
        }
      }
    }
  }
}
