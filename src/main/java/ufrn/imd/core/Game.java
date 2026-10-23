package ufrn.imd.core;

import java.util.ArrayList;
import java.util.List;

import ufrn.imd.inputs.Key;

public class Game {
  int tick = 0;

  public void process(Key key) {
    tick++;
  }

  public Screen getScreen(int columns, int rows) {
    List<String> screen = new ArrayList<>(rows);
    screen.add("Tick: " + this.tick);
    return new Screen(columns, rows, screen);
  }
}
