package ufrn.imd;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;

import ufrn.imd.core.timers.Clock;
import ufrn.imd.inputs.KeyListener;

public class Main {
    public static void main(String[] args) throws IOException {
        Clock clock = new Clock();

        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        try (Terminal terminal = TerminalBuilder.terminal()) {
            KeyListener keyListener = new KeyListener(executor, terminal);
            keyListener.listen(key -> {
                terminal.writer().println(key.character());
                terminal.writer().flush();
            });

            while (true) {
                if (clock.isTick()) {
                    terminal.puts(Capability.clear_screen);
                    terminal.flush();

                    int largura = terminal.getColumns();
                    int altura = terminal.getRows();

                    terminal.writer().println(largura + " x " + altura);
                    terminal.writer().println(clock.getFrame());
                    terminal.writer().flush();
                }
            }
        }
    }
}