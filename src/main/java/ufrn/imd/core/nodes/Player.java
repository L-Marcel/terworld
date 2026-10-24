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
    Vector2 velocity = this.getVelocity();

    switch (key.code()) {
      case 'k':
        this.setVelocity(velocity.withX(0));
        this.jump(physics);
        break;
      case 's':
        this.setVelocity(velocity.withY(1));
        break;
      case 'a':
        this.setVelocity(velocity.withX(-1));
        this.flipped = true;
        break;
      case 'd':
        this.setVelocity(velocity.sum(0, 1));
        this.flipped = false;
        break;
      default:
        this.setVelocity(velocity.withX(0));
        break;
    }

    super.process(key, physics);
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
