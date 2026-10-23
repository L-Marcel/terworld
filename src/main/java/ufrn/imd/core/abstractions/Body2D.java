package ufrn.imd.core.abstractions;

import ufrn.imd.core.Physics;
import ufrn.imd.core.Vector2;

public abstract class Body2D extends Node2D {
  public Body2D(Vector2 position, int layer) {
    super(position, layer);
  }

  protected final void move(Vector2 displacement, Physics physics) {
    this.position = physics.move(this, displacement);
  }
}
