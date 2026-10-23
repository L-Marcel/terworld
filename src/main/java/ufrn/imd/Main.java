package ufrn.imd;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;

import ufrn.imd.core.timers.Clock;
import ufrn.imd.inputs.KeyListener;

public class Main {
    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        AtomicLong turn = new AtomicLong(0);
        Clock clock = new Clock(1);
       
        try (Terminal terminal = TerminalBuilder.terminal()) {
            KeyListener keyListener = new KeyListener(executor, terminal);
            keyListener.listen(key -> {});

            while (true) {
                if (clock.isTick()) {
                    terminal.puts(Capability.clear_screen);
                    terminal.flush();

                    int columns = terminal.getColumns();
                    int rows = terminal.getRows();

                    terminal.writer().println(columns + " x " + rows);
                    terminal.writer().println("Tick interval: " + clock.getTickInterval());
                    terminal.writer().println("Last tick: " + clock.getLastTick());
                    terminal.writer().println("Turn: " +turn);
                    terminal.writer().flush();

                    turn.getAndIncrement();
                }
            }
        }
    }
}