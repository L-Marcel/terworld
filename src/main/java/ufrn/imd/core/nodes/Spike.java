package ufrn.imd.core.nodes;

import ufrn.imd.core.Vector2;
import ufrn.imd.core.abstractions.Body2D;
import ufrn.imd.core.abstractions.Node2D;

public class Spike extends Node2D {
  public Spike(Vector2 position) {
    super(position, Integer.MAX_VALUE);
  }

  @Override
  public void onContact(Body2D body) {
    if (body instanceof Player player)
      player.takeDamage();
  }

  @Override
  public char getShape() {
    return '!';
  }
}
