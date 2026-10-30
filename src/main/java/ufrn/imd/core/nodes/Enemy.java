package ufrn.imd.core.nodes;

import ufrn.imd.core.Physics;
import ufrn.imd.core.Vector2;
import ufrn.imd.core.abstractions.Body2D;
import ufrn.imd.inputs.Key;

public class Enemy extends Body2D {
  private boolean flipped = false;
  private Sword sword;

  public Enemy(Vector2 position) {
    super(position, 1);
    this.sword = new Sword(position.sum(1, 0));
    this.addChild(this.sword);
  }

  @Override
  public void process(Key key, Physics physics) {
    super.process(key, physics);
    this.attackIfPlayerInRange(physics);
    this.sword.setFlipped(this.flipped);
    this.sword.setPosition(
      this.position.sum(this.flipped ? -1 : 1, 0)
    );
  }

  private void attackIfPlayerInRange(Physics physics) {
    for (int direction = -1; direction <= 1; direction += 2) {
      for (int distance = 1; distance <= 2; distance++) {
        Vector2 position = this.position.sum(direction * distance, 0);

        if (physics.isSolid(position))
          break;

        if (physics.getBodyAt(position, this).filter(Player.class::isInstance).isPresent()) {
          this.flipped = direction < 0;
          this.sword.attack();
          return;
        }
      }
    }
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
