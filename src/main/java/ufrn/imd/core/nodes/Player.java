package ufrn.imd.core.nodes;

import ufrn.imd.core.abstractions.Fighter;
import ufrn.imd.core.constants.Health;
import ufrn.imd.core.enums.Action;
import ufrn.imd.core.enums.Direction;
import ufrn.imd.core.physics.Physics;
import ufrn.imd.core.physics.Vector2;

public class Player extends Fighter {
  private boolean levelCompleted = false;

  public Player(Vector2 position) {
    super(position, 2, Health.PLAYER_MAX_HP);
  }

  public boolean hasCompletedLevel() {
    return this.levelCompleted;
  }

  public void completeLevel() {
    if (!this.isDead())
      this.levelCompleted = true;
  }

  @Override
  public void takeDamage() {
    if (!this.hasCompletedLevel())
      super.takeDamage();
  }

  @Override
  public void process(Action action, Physics physics) {
    Vector2 velocity = this.getVelocity();

    switch (action) {
      case JUMP:
        this.setVelocity(velocity.withX(0));
        this.jump(physics);
        break;
      case DROP:
        this.setVelocity(velocity.withY(1));
        break;
      case LEFT:
        this.setVelocity(velocity.withX(-1));
        this.face(Direction.LEFT);
        break;
      case RIGHT:
        this.setVelocity(velocity.withX(1));
        this.face(Direction.RIGHT);
        break;
      default:
        this.setVelocity(velocity.withX(0));
        break;
    }

    super.process(action, physics);
    if (action == Action.ATTACK)
      this.attack();
  }

  @Override
  public char getShape() {
    return this.isProtected() ? 'o' : 'O';
  }
}
