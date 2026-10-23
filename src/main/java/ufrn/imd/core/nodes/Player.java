package ufrn.imd.core.nodes;

import ufrn.imd.core.Physics;
import ufrn.imd.core.Vector2;
import ufrn.imd.core.abstractions.Body2D;
import ufrn.imd.inputs.Key;

public class Player extends Body2D {
  private boolean flipped = false;
  private Sword sword;

  public Player(Vector2 position) {
    super(position, 2);
    this.sword = new Sword(position.sum(1, 0));
    this.addChild(this.sword);
  }

  @Override
  public void process(Key key, Physics physics) {
    Vector2 velocity = Vector2.ZERO;

    switch (key.code()) {
      case 'w':
        velocity = new Vector2(0, -1);
        break;
      case 's':
        velocity = new Vector2(0, 1);
        break;
      case 'a':
        velocity = new Vector2(-1, 0);
        this.flipped = true;
        break;
      case 'd':
        velocity = new Vector2(1, 0);
        this.flipped = false;
        break;
      default:
        break;
    }

    this.move(velocity, physics);
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
