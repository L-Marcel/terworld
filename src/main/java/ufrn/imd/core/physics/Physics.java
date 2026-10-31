package ufrn.imd.core.physics;

import ufrn.imd.core.abstractions.Body2D;
import ufrn.imd.core.resolvers.ContactResolver;

public class Physics {
  private final CollisionMap collisions;
  private final ContactResolver contacts;

  public Physics(CollisionMap collisions) {
    this.collisions = collisions;
    this.contacts = new ContactResolver(collisions);
  }

  public Vector2 move(Body2D body, Vector2 velocity) {
    Vector2 position = body.getPosition();

    if (!this.contacts.resolveAt(body, position))
      return position;

    position = this.resolveAxis(body, position, velocity.x(), true);
    if (!this.contacts.canContinue(body))
      return position;

    return this.resolveAxis(body, position, velocity.y(), false);
  }

  private Vector2 resolveAxis(Body2D body, Vector2 position, int distance, boolean horizontal) {
    int step = Integer.signum(distance);

    for (int remaining = distance; remaining != 0; remaining -= step) {
      Vector2 next = position.sum(horizontal ? step : 0, horizontal ? 0 : step);

      if (this.collisions.isBlocked(next, body))
        break;

      position = next;
      if (!this.contacts.resolveAt(body, position))
        break;
    }

    return position;
  }

  public boolean isGrounded(Body2D body) {
    return !body.isFreePending() && this.collisions.isBlocked(body.getPosition().sum(0, 1), body);
  }

  public CollisionMap getCollisionMap() {
    return this.collisions;
  }
}
