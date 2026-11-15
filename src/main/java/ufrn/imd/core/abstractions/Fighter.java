package ufrn.imd.core.abstractions;

import ufrn.imd.core.components.AttackComponent;
import ufrn.imd.core.components.HealthComponent;
import ufrn.imd.core.enums.Direction;
import ufrn.imd.core.nodes.Sword;
import ufrn.imd.core.physics.Vector2;

public abstract class Fighter extends Body2D {
  //@ spec_public
  private Direction facing;
  //@ spec_public
  private final HealthComponent health;
  //@ spec_public
  private final AttackComponent attack;
  //@ spec_public
  private final Sword sword;

  //@ protected normal_behavior
  //@ requires Integer.MIN_VALUE <= position.x() + 2 * Direction.RIGHT.step() <= Integer.MAX_VALUE;
  //@ ensures this.position == position;
  //@ ensures this.layer == layer;
  //@ ensures this.facing == Direction.RIGHT;
  //@ ensures \fresh(this.health);
  //@ ensures \fresh(this.sword);
  //@ ensures \fresh(children);
  //@ ensures \fresh(movement);
  //@ ensures this.health.hp == Math.max(1, maxHp);
  //@ ensures this.health.maxHp == Math.max(1, maxHp);
  //@ ensures !this.health.hasProtection;
  //@ ensures this.attack == attack;
  //@ ensures this.sword.owner == this;
  //@ ensures children.size() == 1;
  //@ ensures children.get(0) == this.sword;
  //@ ensures this.sword.parent == this;
  //@ ensures parent == null;
  //@ ensures !freeIsPending;
  //@ ensures movement.horizontalSpeed == 0.0;
  //@ ensures movement.verticalSpeed == 0.0;
  //@ ensures movement.horizontalDelay == 0.0;
  //@ pure
  protected Fighter(Vector2 position, int layer, int maxHp, AttackComponent attack) {
    super(position, layer);
    this.facing = Direction.RIGHT;
    this.health = new HealthComponent(maxHp);
    this.attack = attack;
    this.sword = new Sword(this);
    this.addChild(this.sword);
  }

  //@ public normal_behavior
  //@ ensures \result == facing;
  //@ pure
  public final Direction getFacing() {
    return this.facing;
  }

  //@ protected normal_behavior
  //@ ensures this.facing == facing;
  //@ assignable this.facing;
  protected final void face(Direction facing) {
    if (facing != null)
      this.facing = facing;
  }

  //@ protected normal_behavior
  //@ ensures !canAttack();
  //@ assignable attack.bufferedSeconds, attack.state, attack.remainingSeconds;
  protected final void attack() {
    this.attack.request();
  }

  //@ public normal_behavior
  //@ ensures \result == attack.isIdle();
  //@ pure
  public final boolean canAttack() {
    return this.attack.isIdle();
  }

  //@ public normal_behavior
  //@ ensures \result == attack.isPreparing();
  //@ pure
  public final boolean isPreparing() {
    return this.attack.isPreparing();
  }

  //@ public normal_behavior
  //@ ensures \result == attack.isAttacking();
  //@ pure
  public final boolean isAttacking() {
    return this.attack.isAttacking();
  }

  //@ public normal_behavior
  //@ ensures \result == health.getHp();
  //@ pure
  public final int getHp() {
    return this.health.getHp();
  }

  //@ public normal_behavior
  //@ ensures \result == health.getMaxHp();
  //@ pure
  public final int getMaxHp() {
    return this.health.getMaxHp();
  }

  //@ public normal_behavior
  //@ ensures \result == health.isDead();
  //@ pure
  public final boolean isDead() {
    return this.health.isDead();
  }

  //@ public normal_behavior
  //@ ensures \result == health.isProtected();
  //@ pure
  public final boolean isProtected() {
    return this.health.isProtected();
  }

  //@ public normal_behavior
  //@ assignable health.hasProtection, attack.bufferedSeconds, attack.state, attack.remainingSeconds;
  public final void beginTick() {
    this.health.beginTick();
    this.attack.beginTick();
  }

  //@ public normal_behavior
  //@ requires freeIsPending || health.hp == 0 || health.hasProtection;
  //@ ensures health.hp == \old(health.hp);
  //@ ensures health.hasProtection == \old(health.hasProtection);
  //@ assignable \nothing;
  //@ also
  //@ public normal_behavior
  //@ requires !freeIsPending && health.hp > 1 && !health.hasProtection;
  //@ ensures health.hp == \old(health.hp) - 1;
  //@ ensures health.hasProtection;
  //@ ensures !freeIsPending;
  //@ assignable health.hp, health.hasProtection;
  //@ also
  //@ public normal_behavior
  //@ requires !freeIsPending && health.hp == 1 && !health.hasProtection;
  //@ ensures freeIsPending;
  //@ assignable \everything;
  public void takeDamage() {
    if (this.isFreePending() || !this.health.takeDamage())
      return;

    if (this.isDead())
      this.queueFree();
  }
}
