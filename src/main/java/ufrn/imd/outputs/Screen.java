package ufrn.imd.outputs;

import java.util.List;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp.Capability;
import ufrn.imd.core.rendering.Window;
import ufrn.imd.inputs.Keys;

public class Screen implements AutoCloseable {
  private final Terminal terminal;

  public Screen(Terminal terminal) {
    this.terminal = terminal;
    this.terminal.puts(Capability.enter_ca_mode);
    this.terminal.puts(Capability.cursor_invisible);
    this.terminal.puts(Capability.exit_am_mode);
    this.terminal.writer().print(Keys.ENTER_EVENT_MODE);
    this.terminal.flush();
  }

  public void draw(Window window, boolean showHelp) {
    int columns = this.terminal.getColumns();
    int rows = this.terminal.getRows();

    if (columns <= 0 || rows <= 0)
      return;

    List<String> lines = window.content();
    StringBuilder frame = new StringBuilder();
    for (int row = 0; row < rows; row++) {
      String line = showHelp && row == rows - 1 ? Keys.HELP : row < lines.size() ? lines.get(row) : "";
      int length = Math.min(columns, line.length());

      frame.append(line, 0, length);
      frame.append(" ".repeat(columns - length));

      if (row < rows - 1)
        frame.append("\r\n");
    }

    this.terminal.puts(Capability.cursor_home);
    this.terminal.writer().print(frame);
    this.terminal.flush();
  }

  @Override
  public void close() {
    this.terminal.writer().print(Keys.EXIT_EVENT_MODE);
    this.terminal.puts(Capability.enter_am_mode);
    this.terminal.puts(Capability.cursor_normal);
    this.terminal.puts(Capability.exit_ca_mode);
    this.terminal.flush();
  }
}
