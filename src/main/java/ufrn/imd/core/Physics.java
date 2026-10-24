package ufrn.imd.core;

import ufrn.imd.core.abstractions.Body2D;
import ufrn.imd.core.abstractions.Node;

public class Physics {
  private final Level level;

  public Physics(Level level) {
    this.level = level;
  }

  public Vector2 move(Body2D body, Vector2 velocity) {
    Vector2 position = body.getPosition();

    if (body.isFreePending())
      return position;

    position = this.resolveAxis(body, position, velocity.x(), true);
    return this.resolveAxis(body, position, velocity.y(), false);
  }

  public boolean isGrounded(Body2D body) {
    return !body.isFreePending() && this.isBlocked(body.getPosition().sum(0, 1), body);
  }

  private Vector2 resolveAxis(
    Body2D body,
    Vector2 position,
    int distance,
    boolean horizontal
  ) {
    int step = Integer.signum(distance);

    for (int remaining = distance; remaining != 0; remaining -= step) {
      Vector2 next = position.sum(horizontal ? step : 0, horizontal ? 0 : step);

      if (this.isBlocked(next, body))
        break;

      position = next;
    }

    return position;
  }

  private boolean isBlocked(Vector2 position, Body2D movingBody) {
    if (position.x() < 0 || position.y() < 0
      || position.x() >= this.level.width() || position.y() >= this.level.height())
      return true;

    if (this.level.content()[position.y()][position.x()] == '#')
      return true;

    for (Node node : this.level.nodes()) {
      if (this.isOccupied(node, position, movingBody))
        return true;
    }

    return false;
  }

  private boolean isOccupied(Node node, Vector2 position, Body2D movingBody) {
    if (node.isFreePending())
      return false;

    if (node != movingBody && node instanceof Body2D body && body.getPosition().equals(position))
      return true;

    for (Node child : node.getChildren()) {
      if (this.isOccupied(child, position, movingBody))
        return true;
    }

    return false;
  }
}
