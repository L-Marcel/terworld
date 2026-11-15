package ufrn.imd.core.nodes;

import ufrn.imd.core.abstractions.Tile;
import ufrn.imd.core.physics.Vector2;

public class Block extends Tile {
  //@ public normal_behavior
  //@ ensures this.position == position;
  //@ ensures this.layer == Integer.MAX_VALUE;
  //@ ensures getShape() == '#';
  //@ ensures !freeIsPending;
  //@ ensures children.size() == 0;
  //@ ensures parent == null;
  //@ pure
  public Block(Vector2 position) {
    super(position, Integer.MAX_VALUE, '#');
  }
}
