package ufrn.imd.core.nodes;

import ufrn.imd.core.Vector2;
import ufrn.imd.core.abstractions.Node2D;
import ufrn.imd.inputs.Key;

public class Player extends Node2D {
  private boolean flipped = false;
  private Sword sword;

  public Player(Vector2 position) {
    super(position);
    this.sword = new Sword(position.sum(1, 0));
    this.addChild(this.sword);
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
        this.flipped = true;
        break;
      case 'd':
        this.position = this.position.sum(1, 0);
        this.flipped = false;
        break;
      default:
        break;
    }

    this.sword.setFlipped(this.flipped);
    this.sword.setPosition(
      this.position.sum(this.flipped ? -1 : 1, 0)
    );
  }

  @Override
  public char getShape() {
    return 'O';
  }
}
