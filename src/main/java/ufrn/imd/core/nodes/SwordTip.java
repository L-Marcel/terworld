package ufrn.imd.core.nodes;

import ufrn.imd.core.abstractions.Node2D;
import ufrn.imd.core.enums.Direction;
import ufrn.imd.core.physics.Vector2;

public class SwordTip extends Node2D {
  private Direction facing = Direction.RIGHT;

  public SwordTip(Vector2 position) {
    super(position);
  }

  public void setPosition(Vector2 position) {
    this.position = position;
  }

  public void setDirection(Direction facing) {
    this.facing = facing;
  }

  @Override
  public char getShape() {
    return this.facing == Direction.LEFT ? '<' : '>';
  }
}
