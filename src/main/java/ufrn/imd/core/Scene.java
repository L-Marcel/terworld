package ufrn.imd.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ufrn.imd.core.abstractions.Node;
import ufrn.imd.core.enums.Action;
import ufrn.imd.core.physics.Physics;

public class Scene {
  private final List<Node> roots;

  public Scene(List<Node> roots) {
    this.roots = new ArrayList<>(roots);
  }

  public void processRoots(Action action, Physics physics) {
    for (Node node : this.roots) {
      if (!node.isFreePending())
        node.process(action, physics);
    }
  }

  public void processChildren(Action action, Physics physics) {
    for (Node node : this.roots) {
      if (!node.isFreePending()) {
        for (Node child : node.getChildren())
          this.process(child, action, physics);
      }
    }
  }

  private void process(Node node, Action action, Physics physics) {
    if (node.isFreePending())
      return;

    node.process(action, physics);
    for (Node child : node.getChildren())
      this.process(child, action, physics);
  }

  public void removeQueued() {
    Iterator<Node> nodes = this.roots.iterator();
    while (nodes.hasNext()) {
      Node node = nodes.next();
      this.removeQueuedChildren(node);
      if (node.isFreePending())
        nodes.remove();
    }
  }

  private void removeQueuedChildren(Node node) {
    for (Node child : List.copyOf(node.getChildren())) {
      this.removeQueuedChildren(child);
      if (child.isFreePending())
        node.removeChild(child);
    }
  }

  public List<Node> getNodes() {
    List<Node> nodes = new ArrayList<>();
    for (Node root : this.roots)
      this.collect(root, nodes);
    return List.copyOf(nodes);
  }

  private void collect(Node node, List<Node> nodes) {
    if (node.isFreePending())
      return;

    nodes.add(node);
    for (Node child : node.getChildren())
      this.collect(child, nodes);
  }
}
