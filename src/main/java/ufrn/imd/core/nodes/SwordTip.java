package ufrn.imd.core.nodes;

import ufrn.imd.core.Vector2;
import ufrn.imd.core.abstractions.Node2D;

public class SwordTip extends Node2D {
  private boolean flipped = false;

  public SwordTip(Vector2 position) {
    super(position);
  }

  public void setPosition(Vector2 position) {
    this.position = position;
  }

  public void setFlipped(boolean flipped) {
    this.flipped = flipped;
  }

  @Override
  public char getShape() {
    return this.flipped ? '<' : '>';
  }
}
