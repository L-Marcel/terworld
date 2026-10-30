package ufrn.imd.core.abstractions;

import ufrn.imd.core.Vector2;

public abstract class Node2D extends Node {
  protected int layer;
  protected Vector2 position;

  public Node2D(Vector2 position) {
    this(position, 0);
  }

  public Node2D(Vector2 position, int layer) {
    this.position = position;
    this.layer = layer;
  }

  public int getLayer() {
    return this.layer;
  }

  public Vector2 getPosition() {
    return this.position;
  }

  public void onContact(Body2D body) {}

  public abstract char getShape();
}
