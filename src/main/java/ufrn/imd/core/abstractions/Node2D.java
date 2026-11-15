package ufrn.imd.core.abstractions;

import ufrn.imd.core.physics.Vector2;

public abstract class Node2D extends Node {
  //@ spec_public
  protected int layer;

  //@ spec_public
  protected Vector2 position;

  //@ protected normal_behavior
  //@ ensures this.position == position;
  //@ ensures this.layer == 0;
  //@ ensures !freeIsPending;
  //@ ensures children.size() == 0;
  //@ ensures parent == null;
  //@ pure
  protected Node2D(Vector2 position) {
    this(position, 0);
  }

  //@ protected normal_behavior
  //@ ensures this.position == position;
  //@ ensures this.layer == layer;
  //@ ensures !freeIsPending;
  //@ ensures children.size() == 0;
  //@ ensures parent == null;
  //@ pure
  protected Node2D(Vector2 position, int layer) {
    super();
    this.position = position;
    this.layer = layer;
  }

  //@ public normal_behavior
  //@ ensures \result == this.layer;
  //@ pure
  public int getLayer() {
    return this.layer;
  }

  //@ public normal_behavior
  //@ ensures \result == this.position;
  //@ pure
  public Vector2 getPosition() {
    return this.position;
  }

  //@ public normal_behavior
  //@ pure
  public abstract char getShape();
}
