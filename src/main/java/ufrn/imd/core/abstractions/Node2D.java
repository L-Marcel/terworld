package ufrn.imd.core.abstractions;

import ufrn.imd.core.Vector2;

public abstract class Node2D extends Node {
  protected Vector2 position;

  public Node2D(Vector2 position) {
    this.position = position;
  }

  public Vector2 getPosition() {
    return this.position;
  }

  public abstract char getShape();
}
