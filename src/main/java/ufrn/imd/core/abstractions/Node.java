package ufrn.imd.core.abstractions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import ufrn.imd.core.Controls;
import ufrn.imd.core.physics.Physics;

public abstract class Node {
  //@ spec_public
  private final List<Node> children;

  //@ spec_public
  private boolean freeIsPending;

  //@ spec_public
  //@ nullable
  private Node parent;

  //@ public invariant children.values.length >= 0;
  //@ public invariant \typeof(children) == \type(ArrayList<Node>);

  //@ protected normal_behavior
  //@ ensures freeIsPending == false;
  //@ ensures children.values.length == 0;
  //@ ensures parent == null;
  //@ pure
  protected Node() {
    this.freeIsPending = false;
    this.children = new ArrayList<>(0);
  }

  //@ public normal_behavior
  //@ ensures this instanceof Node;
  //@ ensures \typeof(this) <:= \type(Node);
  //@ ensures freeIsPending == true;
  //@ ensures \forall int i; 0 <= i < children.values.length;
  //@   children.values[i].freeIsPending;
  //@ ensures \forall Node n; n != null;
  //@   n.children == \old(n.children) && n.children.values == \old(n.children.values);
  //@ ensures \forall Node n; n != null && \old(n.freeIsPending);
  //@   n.freeIsPending;
  //@ ensures \old(\invariant_for(this)) ==> \invariant_for(this);
  //@ assignable \everything;
  //@ helper
  public final void queueFree() {
    this.freeIsPending = true;
    //@ loop_invariant 0 <= \count;
    //@ loop_invariant \count == 0 || \count <= this.children.values.length;
    //@ loop_invariant this.freeIsPending == true;
    //@ loop_invariant this.children.values == \old(this.children.values);
    //@ loop_invariant \forall Node n; n != null;
    //@   n.children == \old(n.children) && n.children.values == \old(n.children.values);
    //@ loop_invariant \forall Node n; n != null && \old(n.freeIsPending);
    //@   n.freeIsPending;
    //@ loop_invariant \forall int i; 0 <= i < \count;
    //@   this.children.values[i] instanceof Node
    //@     && \typeof(this.children.values[i]) <:= \type(Node)
    //@     && this.children.values[i].freeIsPending;
    //@ loop_assignable \everything;
    //@ decreases this.children.values.length - \count;
    for (Node child : this.children) {
      child.queueFree();
    }
  }

  //@ public normal_behavior
  //@ ensures \result == freeIsPending;
  //@ pure
  public final boolean isFreePending() {
    return this.freeIsPending;
  }

  //@ public normal_behavior
  //@ requires node.parent == null || \invariant_for(node.parent);
  //@ requires node.parent == null || node.parent == this || node.parent.children != this.children;
  //@ {|
  //@   requires node.parent != null;
  //@   assignable children.values, 
  //@     node.parent, node.parent.children.values, 
  //@     this.children.values;
  //@   also
  //@   requires node.parent == null;
  //@   assignable children.values, node.parent, 
  //@     this.children.values;
  //@ |}
  //@ ensures (\old(node.parent) != null && \old(node.parent) != this) ==> (
  //@   !\exists int i; 0 <= i < \old(node.parent).children.values.length;
  //@     \old(node.parent).children.values[i] == node
  //@ );
  //@ ensures node.parent == this;
  //@ ensures this.children.values[this.children.values.length - 1] == node;
  public final void addChild(Node node) {
    if (node.parent != null)
      node.parent.removeChild(node);
    this.children.add(node);
    node.parent = this;
  }

  //@ public normal_behavior
  //@ ensures !this.children.contains(node);
  //@ ensures (
  //@   \exists int i; 0 <= i < \old(this.children.values.length);
  //@     \old(this.children.values[i]) == node
  //@ ) ==> node.parent == null;
  //@ assignable children.values, node.parent;
  public final void removeChild(Node node) {
    //@ ghost \bigint count = 0;
    //@ loop_invariant 0 <= count <= \old(this.children.values.length);
    //@ loop_invariant this.children.values.length == \old(this.children.values.length) - count;
    //@ loop_invariant count > 0 ==> node.parent == null;
    //@ loop_invariant (count > 0) | (this.children.values == \old(this.children.values));
    //@ loop_assignable this.children.values, 
    //@   node.parent, count;
    //@ decreases \old(this.children.values.length) - count;
    while (this.children.remove(node)) {
      node.parent = null;
      //@ set count = count + 1;
    }
  }

  public Optional<Node> getParent() {
    return Optional.ofNullable(this.parent);
  }

  public List<Node> getChildren() {
    return Collections.unmodifiableList(this.children);
  }

  public void process(Controls controls, Physics physics) {}
}
