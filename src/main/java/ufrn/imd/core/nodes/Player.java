package ufrn.imd.core.nodes;

import ufrn.imd.core.Controls;
import ufrn.imd.core.abstractions.Fighter;
import ufrn.imd.core.components.AttackComponent;
import ufrn.imd.core.components.JumpComponent;
import ufrn.imd.core.constants.Attacks;
import ufrn.imd.core.constants.Forces;
import ufrn.imd.core.constants.Health;
import ufrn.imd.core.enums.Direction;
import ufrn.imd.core.physics.Physics;
import ufrn.imd.core.physics.Vector2;

public class Player extends Fighter {
  private boolean levelCompleted = false;
  private final JumpComponent jump;

  public Player(Vector2 position) {
    super(
      position, 2, Health.PLAYER_MAX_HP, new AttackComponent(
        Attacks.PLAYER_PREPARATION_SECONDS, Attacks.PLAYER_ACTIVE_SECONDS, Attacks.PLAYER_RECOVERY_SECONDS
      )
    );
    this.jump = new JumpComponent();
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
  public void process(Controls controls, Physics physics) {
    this.setHorizontalSpeed(controls.horizontal() * Forces.PLAYER_SPEED);
    if (controls.horizontal() != 0)
      this.face(controls.horizontal() < 0 ? Direction.LEFT : Direction.RIGHT);

    boolean grounded = physics.isGrounded(this) && this.getVerticalSpeed() >= 0;
    if (this.jump.process(controls.jump(), grounded))
      this.jump();
    if (controls.drop())
      this.drop();
    if (controls.attack())
      this.attack();

    super.process(controls, physics);
  }

  @Override
  public char getShape() {
    return this.isProtected() ? 'o' : 'O';
  }
}
