package ufrn.imd.core.abstractions;

import java.util.ArrayList;
import java.util.List;
import ufrn.imd.inputs.Key;

public abstract class Node {
  protected List<Node> children = new ArrayList<>(0);
  private boolean freeIsPending = false;

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
  }

  public final void removeChild(Node node) {
    this.children.remove(node);
  }

  public List<Node> getChildren() {
    return this.children;
  };

  public void process(Key key) {};
}
