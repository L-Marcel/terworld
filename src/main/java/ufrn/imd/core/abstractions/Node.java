package ufrn.imd.core.abstractions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ufrn.imd.inputs.Key;

public abstract class Node {
  private boolean freeIsPending = false;
  protected List<Node> children = new ArrayList<>(0);
  private Optional<Node> parent = Optional.empty();

  public final void queueFree() {
    this.freeIsPending = true;
    for (Node child : this.children)
      child.queueFree();
  }

  public final boolean isFreePending() {
    return this.freeIsPending;
  }

  public final void addChild(Node node) {
    this.children.add(node);
    node.parent.ifPresent(p -> p.removeChild(node));
    node.parent = Optional.of(this);
  }

  public final void removeChild(Node node) {
    if (this.children.remove(node))
      node.parent = Optional.empty();
  }

  public Optional<Node> getParent() {
    return this.parent;
  }

  public List<Node> getChildren() {
    return this.children;
  }

  public void process(Key key) {};
}
