package ufrn.imd.core.nodes;

import ufrn.imd.core.abstractions.Tile;
import ufrn.imd.core.physics.Vector2;

public class Spike extends Tile {
  public Spike(Vector2 position) {
    super(position, Integer.MAX_VALUE, '!');
  }
}
