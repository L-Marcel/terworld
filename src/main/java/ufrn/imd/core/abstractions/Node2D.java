package ufrn.imd.core.abstractions;

import ufrn.imd.core.physics.Vector2;

public abstract class Node2D extends Node {
  protected int layer;
  protected Vector2 position;

  protected Node2D(Vector2 position) {
    this(position, 0);
  }

  protected Node2D(Vector2 position, int layer) {
    super();
    this.position = position;
    this.layer = layer;
  }

  public int getLayer() {
    return this.layer;
  }

  public Vector2 getPosition() {
    return this.position;
  }

  public abstract char getShape();
}
