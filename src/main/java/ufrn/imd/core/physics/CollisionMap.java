package ufrn.imd.core.physics;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ufrn.imd.core.Level;
import ufrn.imd.core.Scene;
import ufrn.imd.core.abstractions.Body2D;
import ufrn.imd.core.abstractions.Node;
import ufrn.imd.core.abstractions.Node2D;
import ufrn.imd.core.nodes.Spike;

public class CollisionMap {
  private final int width;
  private final int height;
  private final char[][] content;
  private final Scene scene;

  public CollisionMap(Level level, Scene scene) {
    this.width = level.width();
    this.height = level.height();
    this.content = level.content();
    this.scene = scene;
  }

  public boolean isSolid(Vector2 position) {
    return position.x() < 0
      || position.y() < 0
      || position.x() >= this.width
      || position.y() >= this.height
      || this.content[position.y()][position.x()] == '#';
  }

  public boolean isBlocked(Vector2 position, Body2D ignored) {
    return this.isSolid(position) || this.getBodyAt(position, ignored).isPresent();
  }

  public boolean isDangerous(Vector2 position) {
    for (Node node : this.scene.getNodes()) {
      if (node instanceof Spike spike &&
        spike.getPosition().equals(position))
        return true;
    }

    return false;
  }

  public Optional<Body2D> getBodyAt(Vector2 position, Body2D ignored) {
    for (Node node : this.scene.getNodes()) {
      if (node != ignored &&
        node instanceof Body2D body &&
        body.getPosition().equals(position))
        return Optional.of(body);
    }

    return Optional.empty();
  }

  public List<Node2D> getNodesAt(Vector2 position) {
    List<Node2D> nodes = new ArrayList<>();

    for (Node node : this.scene.getNodes()) {
      if (node instanceof Node2D node2d
        && node2d.getPosition().equals(position))
        nodes.add(node2d);
    }

    return List.copyOf(nodes);
  }
}
