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
    Vector2 displacement = Vector2.ZERO;

    switch (key.code()) {
      case 'w':
        displacement = new Vector2(0, -1);
        break;
      case 's':
        displacement = new Vector2(0, 1);
        break;
      case 'a':
        displacement = new Vector2(-1, 0);
        this.flipped = true;
        break;
      case 'd':
        displacement = new Vector2(1, 0);
        this.flipped = false;
        break;
      default:
        break;
    }

    this.move(displacement, physics);
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
