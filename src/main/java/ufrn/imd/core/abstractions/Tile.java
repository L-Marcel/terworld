package ufrn.imd.core.abstractions;

import ufrn.imd.core.physics.Vector2;

public abstract class Tile extends Node2D {
  //@ spec_public
  private final char shape;

  //@ protected normal_behavior
  //@ ensures this.position == position;
  //@ ensures this.layer == layer;
  //@ ensures this.shape == shape;
  //@ ensures !freeIsPending;
  //@ ensures children.size() == 0;
  //@ ensures parent == null;
  //@ pure
  protected Tile(Vector2 position, int layer, char shape) {
    super(position, layer);
    this.shape = shape;
  }

  //@ also
  //@ public normal_behavior
  //@ ensures \result == this.shape;
  //@ pure
  @Override
  public final char getShape() {
    return this.shape;
  }
}
