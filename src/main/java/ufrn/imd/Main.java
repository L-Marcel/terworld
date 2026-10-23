package ufrn.imd;

import java.io.IOException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;
import ufrn.imd.core.Game;
import ufrn.imd.core.Screen;
import ufrn.imd.core.timers.Clock;
import ufrn.imd.inputs.Key;
import ufrn.imd.inputs.KeyListener;

public class Main {
    public static void main(String[] args) throws IOException {
        Clock clock = new Clock(1, 4);
        Game game = new Game();

        try (Terminal terminal = TerminalBuilder.terminal()) {
            KeyListener listener = new KeyListener(terminal);
            
            listener.listen(key -> {
                clock.lock();
                listener.setPressedKey(key);
                clock.forceTick();
                clock.unlock();
            });

            while (true) {
                clock.tryLock();

                if (clock.isTick()) {
                    terminal.puts(Capability.clear_screen);
                    terminal.flush();

                    game.process(listener.getPressedKey());

                    Screen screen = game.getScreen(
                        terminal.getColumns(), 
                        terminal.getRows()
                    );

                    screen.content().forEach(terminal.writer()::println);
                    terminal.writer().flush();

                    listener.setPressedKey(Key.none());
                }

                clock.unlock();
            }
        }
    }
}