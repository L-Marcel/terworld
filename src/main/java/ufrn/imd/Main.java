package ufrn.imd;

import java.io.IOException;
import java.net.URISyntaxException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;
import ufrn.imd.core.Game;
import ufrn.imd.core.Window;
import ufrn.imd.core.constants.Ticks;
import ufrn.imd.inputs.Key;
import ufrn.imd.inputs.KeyListener;

public class Main {
  public static void main(String[] args) throws IOException, URISyntaxException {
    Clock clock = new Clock(
      Ticks.MAX_TICKS_PER_SECOND,
      Ticks.MAX_FORCED_TICKS_PER_SECOND
    );

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

      try {
        terminal.puts(Capability.cursor_invisible);
        terminal.flush();

        while (!Thread.currentThread().isInterrupted()) {
          clock.lock();

          try {
            if (clock.isTick()) {
              terminal.puts(Capability.clear_screen);
              terminal.flush();

              if (listener.getPressedKey().is(3))
                break;

              game.process(listener.getPressedKey());

              Window window = game.getWindow(
                terminal.getColumns(),
                terminal.getRows()
              );

              for (int i = 0; i < window.content().size(); i++) {
                String line = window.content().get(i);
                if (i < window.content().size() - 1) {
                  terminal.writer().println(line);
                } else {
                  terminal.writer().print(line);
                }
              }

              terminal.writer().flush();
              listener.setPressedKey(Key.none());
            }
          } finally {
            clock.unlock();
          }
        }
      } finally {
        terminal.puts(Capability.cursor_normal);
        terminal.flush();
      }
    }
  }
}
