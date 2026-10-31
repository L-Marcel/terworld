package ufrn.imd.core.nodes;

import ufrn.imd.core.abstractions.Node2D;
import ufrn.imd.core.physics.Vector2;

public class Block extends Node2D {
  public Block(Vector2 position) {
    super(position, Integer.MAX_VALUE);
  }

  @Override
  public char getShape() {
    return '#';
  }
}
