package ufrn.imd.core.abstractions;

import ufrn.imd.core.physics.Vector2;

public abstract class Tile extends Node2D {
  private final char shape;

  protected Tile(Vector2 position, int layer, char shape) {
    super(position, layer);
    this.shape = shape;
  }

  @Override
  public final char getShape() {
    return this.shape;
  }
}
