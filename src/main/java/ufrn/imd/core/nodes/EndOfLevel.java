package ufrn.imd.core.nodes;

import ufrn.imd.core.abstractions.Tile;
import ufrn.imd.core.physics.Vector2;

public class EndOfLevel extends Tile {
  //@ public normal_behavior
  //@ ensures this.position == position;
  //@ ensures this.layer == 0;
  //@ ensures getShape() == '?';
  //@ ensures !freeIsPending;
  //@ ensures children.size() == 0;
  //@ ensures parent == null;
  //@ pure
  public EndOfLevel(Vector2 position) {
    super(position, 0, '?');
  }
}
