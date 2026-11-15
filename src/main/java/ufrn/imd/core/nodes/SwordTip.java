package ufrn.imd.core.nodes;

import ufrn.imd.core.abstractions.Node2D;
import ufrn.imd.core.enums.Direction;
import ufrn.imd.core.physics.Vector2;

public class SwordTip extends Node2D {
  private final Sword sword;

  //@ public normal_behavior
  //@ requires \invariant_for(sword.owner);
  //@ requires Integer.MIN_VALUE <= sword.position.x() + sword.owner.facing.step() <= Integer.MAX_VALUE;
  //@ ensures position.x() == sword.position.x() + sword.owner.facing.step();
  //@ ensures position.y() == sword.position.y();
  //@ ensures layer == 0;
  //@ ensures \invariant_for(this);
  //@ ensures !freeIsPending;
  //@ ensures children.size() == 0;
  //@ ensures parent == null;
  //@ pure helper
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
