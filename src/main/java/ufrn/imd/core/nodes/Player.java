package ufrn.imd.core.nodes;

import ufrn.imd.core.abstractions.Body2D;
import ufrn.imd.core.components.HealthComponent;
import ufrn.imd.core.enums.Action;
import ufrn.imd.core.enums.Direction;
import ufrn.imd.core.physics.Physics;
import ufrn.imd.core.physics.Vector2;

public class Player extends Body2D {
  public static final int MAX_HP = 4;
  private final HealthComponent health = new HealthComponent(MAX_HP);
  private boolean levelCompleted = false;
  private Direction facing = Direction.RIGHT;
  private final Sword sword;

  public Player(Vector2 position) {
    super(position, 2);
    this.sword = new Sword(this);
    this.addChild(this.sword);
  }

  public int getHp() {
    return this.health.getHp();
  }

  public int getMaxHp() {
    return this.health.getMaxHp();
  }

  public boolean isDead() {
    return this.health.isDead();
  }

  public boolean hasCompletedLevel() {
    return this.levelCompleted;
  }

  public void completeLevel() {
    if (!this.isDead())
      this.levelCompleted = true;
  }

  public boolean isProtected() {
    return this.health.isProtected();
  }

  public void beginTick() {
    this.health.beginTick();
  }

  public void takeDamage() {
    if (this.hasCompletedLevel() || this.isFreePending())
      return;

    if (!this.health.takeDamage())
      return;

    if (this.isDead())
      this.queueFree();
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
        this.facing = Direction.LEFT;
        break;
      case RIGHT:
        this.setVelocity(velocity.withX(1));
        this.facing = Direction.RIGHT;
        break;
      default:
        this.setVelocity(velocity.withX(0));
        break;
    }

    super.process(action, physics);
    this.sword.follow(this.facing);

    if (action == Action.ATTACK)
      this.sword.attack();
  }

  @Override
  public char getShape() {
    return this.isProtected() ? 'o' : 'O';
  }
}
