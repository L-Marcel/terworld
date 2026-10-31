package ufrn.imd.core.nodes;

import ufrn.imd.core.abstractions.Tile;
import ufrn.imd.core.physics.Vector2;

public class EndOfLevel extends Tile {
  public EndOfLevel(Vector2 position) {
    super(position, 0, '?');
  }
}
