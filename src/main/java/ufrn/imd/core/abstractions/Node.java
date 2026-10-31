package ufrn.imd.core.abstractions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import ufrn.imd.core.enums.Action;
import ufrn.imd.core.physics.Physics;

public abstract class Node {
  private boolean freeIsPending;
  private final List<Node> children;
  private Node parent;

  protected Node() {
    this.freeIsPending = false;
    this.children = new ArrayList<>(0);
  }

  public final void queueFree() {
    this.freeIsPending = true;
    for (Node child : this.children)
      child.queueFree();
  }

  public final boolean isFreePending() {
    return this.freeIsPending;
  }

  public final void addChild(Node node) {
    if (node.parent != null)
      node.parent.removeChild(node);
    this.children.add(node);
    node.parent = this;
  }

  public final void removeChild(Node node) {
    if (this.children.remove(node))
      node.parent = null;
  }

  public Optional<Node> getParent() {
    return Optional.ofNullable(this.parent);
  }

  public List<Node> getChildren() {
    return Collections.unmodifiableList(this.children);
  }

  public void process(Action action, Physics physics) {}
}
