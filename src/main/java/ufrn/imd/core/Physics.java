package ufrn.imd.core;

import java.util.Optional;
import ufrn.imd.core.abstractions.Body2D;
import ufrn.imd.core.abstractions.Node;
import ufrn.imd.core.abstractions.Node2D;
import ufrn.imd.core.nodes.Player;
import ufrn.imd.core.nodes.Spike;

public class Physics {
  private final Level level;

  public Physics(Level level) {
    this.level = level;
  }

  public Vector2 move(Body2D body, Vector2 velocity) {
    Vector2 position = body.getPosition();

    if (this.isStopped(body))
      return position;

    this.contact(body, position);
    if (this.isStopped(body))
      return position;

    position = this.resolveAxis(body, position, velocity.x(), true);
    if (this.isStopped(body))
      return position;

    return this.resolveAxis(body, position, velocity.y(), false);
  }

  private boolean isStopped(Body2D body) {
    return body.isFreePending()
      || body instanceof Player player && player.hasCompletedLevel();
  }

  private void contact(Body2D body, Vector2 position) {
    for (Node node : this.level.nodes())
      this.contact(node, body, position);
  }

  private void contact(Node node, Body2D body, Vector2 position) {
    if (node.isFreePending() || body.isFreePending())
      return;

    if (node != body && node instanceof Node2D node2d && node2d.getPosition().equals(position))
      node2d.onContact(body);

    for (Node child : node.getChildren())
      this.contact(child, body, position);
  }

  public boolean isGrounded(Body2D body) {
    return !body.isFreePending() && this.isBlocked(body.getPosition().sum(0, 1), body);
  }

  public boolean isSolid(Vector2 position) {
    return position.x() < 0 || position.y() < 0
      || position.x() >= this.level.width() || position.y() >= this.level.height()
      || this.level.content()[position.y()][position.x()] == '#';
  }

  public boolean isDangerous(Vector2 position) {
    for (Node node : this.level.nodes()) {
      if (!node.isFreePending() && node instanceof Spike spike && spike.getPosition().equals(position))
        return true;
    }
    return false;
  }

  public Optional<Body2D> getBodyAt(Vector2 position, Body2D ignored) {
    for (Node node : this.level.nodes()) {
      Optional<Body2D> body = this.findBody(node, position, ignored);
      if (body.isPresent())
        return body;
    }

    return Optional.empty();
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
      this.contact(body, position);
      if (this.isStopped(body))
        break;
    }

    return position;
  }

  public boolean isBlocked(Vector2 position, Body2D movingBody) {
    return this.isSolid(position) || this.getBodyAt(position, movingBody).isPresent();
  }

  private Optional<Body2D> findBody(Node node, Vector2 position, Body2D ignored) {
    if (node.isFreePending())
      return Optional.empty();

    if (node != ignored && node instanceof Body2D body && body.getPosition().equals(position))
      return Optional.of(body);

    for (Node child : node.getChildren()) {
      Optional<Body2D> body = this.findBody(child, position, ignored);
      if (body.isPresent())
        return body;
    }

    return Optional.empty();
  }
}
