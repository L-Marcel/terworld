package ufrn.imd.core.nodes;

import ufrn.imd.core.Vector2;
import ufrn.imd.core.abstractions.Node2D;

public class Block extends Node2D {
  public Block(Vector2 position) {
    super(position);
  }

  @Override
  public char getShape() {
    return '#';
  }
}
