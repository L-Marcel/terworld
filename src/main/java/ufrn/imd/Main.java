package ufrn.imd;

import java.io.IOException;
import java.net.URISyntaxException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import ufrn.imd.core.Controls;
import ufrn.imd.core.Game;
import ufrn.imd.core.constants.Ticks;
import ufrn.imd.inputs.KeyListener;
import ufrn.imd.inputs.Keyboard;
import ufrn.imd.outputs.Screen;

public class Main {
  public static void main(String[] args) throws IOException, URISyntaxException {
    Clock clock = new Clock(Ticks.UPDATES_PER_SECOND);
    Keyboard keyboard = new Keyboard();
    Loader loader = new Loader();
    Game game = new Game();

    game.setLevels(loader.load());
    game.nextLevel();

    try (
    //@formatter:off
      Terminal terminal = TerminalBuilder.terminal();
      KeyListener listener = new KeyListener(terminal);
      Screen screen = new Screen(terminal)
    //@formatter:on
    ) {
      listener.listen();
      while (!Thread.currentThread().isInterrupted()) {
        clock.awaitNextTick();

        if (Thread.currentThread().isInterrupted())
          break;

        if (clock.isTick()) {
          Controls controls = keyboard.read(listener);

          if (keyboard.isExitRequested())
            break;

          game.process(controls);

          if (game.isWon())
            break;
        }

        if (keyboard.isExitRequested())
          break;

        int columns = terminal.getColumns();
        int rows = terminal.getRows();
        boolean showHelp = rows >= 3;

        screen.draw(
          game.getWindow(
            columns,
            rows - (showHelp ? 1 : 0)
          ),
          showHelp
        );

        if (game.isWon())
          break;
      }
    }

    if (game.isWon())
      System.out.println("Voce venceu todas as fases!");
  }
}
