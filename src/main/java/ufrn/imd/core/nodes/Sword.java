package ufrn.imd.core.nodes;

import java.util.List;

import ufrn.imd.core.Vector2;
import ufrn.imd.core.interfaces.Node;
import ufrn.imd.core.interfaces.Node2D;
import ufrn.imd.inputs.Key;

public class Sword implements Node2D {
  private boolean flipped = false;
  private Vector2 position;

  public Sword(Vector2 position) {
    this.position = position;
  }

  @Override
  public void process(Key key) {}

  @Override
  public List<Node> getChildren() {
    return List.of();
  }

  @Override
  public Vector2 getPosition() {
    return this.position;
  }

  public void setPosition(Vector2 position) {
    this.position = position;
  }

  public void setFlipped(boolean flipped) {
    this.flipped = flipped;
  }

  @Override
  public char getShape() {
    return this.flipped ? '\\' : '/';
  }
}
