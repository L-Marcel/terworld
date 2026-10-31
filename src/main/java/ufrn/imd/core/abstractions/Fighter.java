package ufrn.imd.core.abstractions;

import ufrn.imd.core.components.AttackComponent;
import ufrn.imd.core.components.HealthComponent;
import ufrn.imd.core.enums.Direction;
import ufrn.imd.core.nodes.Sword;
import ufrn.imd.core.physics.Vector2;

public abstract class Fighter extends Body2D {
  private Direction facing;
  private final HealthComponent health;
  private final AttackComponent attack;
  private final Sword sword;

  protected Fighter(Vector2 position, int layer, int maxHp, AttackComponent attack) {
    super(position, layer);
    this.facing = Direction.RIGHT;
    this.health = new HealthComponent(maxHp);
    this.attack = attack;
    this.sword = new Sword(this);
    this.addChild(this.sword);
  }

  public final Direction getFacing() {
    return this.facing;
  }

  protected final void face(Direction facing) {
    if (facing != null)
      this.facing = facing;
  }

  protected final void attack() {
    this.attack.request();
  }

  public final boolean canAttack() {
    return this.attack.isIdle();
  }

  public final boolean isPreparing() {
    return this.attack.isPreparing();
  }

  public final boolean isAttacking() {
    return this.attack.isAttacking();
  }

  public final int getHp() {
    return this.health.getHp();
  }

  public final int getMaxHp() {
    return this.health.getMaxHp();
  }

  public final boolean isDead() {
    return this.health.isDead();
  }

  public final boolean isProtected() {
    return this.health.isProtected();
  }

  public final void beginTick() {
    this.health.beginTick();
    this.attack.beginTick();
  }

  public void takeDamage() {
    if (this.isFreePending() || !this.health.takeDamage())
      return;

    if (this.isDead())
      this.queueFree();
  }
}
