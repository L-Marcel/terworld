package ufrn.imd.core.nodes;

import java.util.ArrayList;
import java.util.List;
import ufrn.imd.core.Vector2;
import ufrn.imd.core.interfaces.Node;
import ufrn.imd.core.interfaces.Node2D;
import ufrn.imd.inputs.Key;

public class Player implements Node2D {
  private Vector2 position;

  public Player(Vector2 position) {
    this.position = position;
  }

  @Override
  public void process(Key key) {
    switch (key.code()) {
      case 'w':
        this.position = this.position.sum(0, -1);
        break;
      case 's':
        this.position = this.position.sum(0, 1);
        break;
      case 'a':
        this.position = this.position.sum(-1, 0);
        break;
      case 'd':
        this.position = this.position.sum(1, 0);
        break;
      default:
        break;
    }
  }

  @Override
  public Vector2 getPosition() {
    return this.position;
  }

  @Override
  public char getShape() {
    return 'O';
  }

  @Override
  public List<Node> getChildrens() {
    return new ArrayList<>();
  }
}
