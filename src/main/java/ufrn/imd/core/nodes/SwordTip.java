package ufrn.imd.core.nodes;

import ufrn.imd.core.abstractions.Node2D;
import ufrn.imd.core.enums.Direction;
import ufrn.imd.core.physics.Vector2;

public class SwordTip extends Node2D {
  private final Sword sword;

  public SwordTip(Sword sword) {
    super(sword.getPosition().sum(sword.getFacing().step(), 0));
    this.sword = sword;
  }

  public void setPosition(Vector2 position) {
    this.position = position;
  }

  @Override
  public char getShape() {
    return this.sword.getFacing() == Direction.LEFT ? '<' : '>';
  }
}
