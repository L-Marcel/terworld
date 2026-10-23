package ufrn.imd.core.nodes;

import java.util.ArrayList;
import java.util.List;

import ufrn.imd.core.Vector2;
import ufrn.imd.core.interfaces.Node;
import ufrn.imd.core.interfaces.Node2D;
import ufrn.imd.inputs.Key;

public class Enemy implements Node2D {
  private List<Node> childrens;
  private boolean flipped = false;
  private Vector2 position;
  private Sword sword;

  public Enemy(Vector2 position) {
    this.position = position;
    this.sword = new Sword(position.sum(1, 0));
    this.childrens = new ArrayList<>(1);
    this.childrens.add(this.sword);
  }

  @Override
  public void process(Key key) {
    this.sword.setFlipped(this.flipped);
    this.sword.setPosition(
      this.position.sum(this.flipped ? -1 : 1, 0)
    );
  }

  @Override
  public List<Node> getChildren() {
    return this.childrens;
  }

  @Override
  public Vector2 getPosition() {
    return this.position;
  }

  @Override
  public char getShape() {
    return 'E';
  }
}
