package ufrn.imd;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;
import ufrn.imd.core.Game;
import ufrn.imd.core.Window;
import ufrn.imd.core.constants.Ticks;
import ufrn.imd.inputs.Key;
import ufrn.imd.inputs.KeyListener;
import ufrn.imd.inputs.Keys;

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

              game.process(listener.getPressedKey().toAction());

              int columns = terminal.getColumns();
              int rows = terminal.getRows();
              boolean showHelp = rows >= 3;

              Window window = game.getWindow(
                columns,
                rows - (showHelp ? 1 : 0)
              );

              List<String> lines = new ArrayList<>(
                window.content()
              );

              if (showHelp)
                lines.add(
                  Keys.HELP.substring(
                    0,
                    Math.max(
                      0,
                      Math.min(
                        columns,
                        Keys.HELP.length()
                      )
                    )
                  )
                );

              for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (i < lines.size() - 1) {
                  terminal.writer().println(line);
                } else {
                  terminal.writer().print(line);
                }
              }

              terminal.writer().flush();
              listener.setPressedKey(Key.none());

              if (game.isWon())
                break;
            }
          } finally {
            clock.unlock();
          }

          clock.awaitNextTick();
        }
      } finally {
        terminal.puts(Capability.cursor_normal);
        terminal.puts(Capability.exit_ca_mode);
        terminal.flush();
      }
    }

    if (game.isWon())
      System.out.println("Voce venceu todas as fases!");
  }
}
