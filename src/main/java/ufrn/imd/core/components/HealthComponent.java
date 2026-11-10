package ufrn.imd.core.components;

import ufrn.imd.core.constants.Health;
import ufrn.imd.core.constants.Ticks;

public class HealthComponent {
  private double protectionSeconds = 0; //@ in hasProtection;

  //@ spec_public
  private final int maxHp;

  //@ spec_public
  private int hp;

  //@ public model boolean hasProtection;
  //@ private represents hasProtection = (protectionSeconds > Ticks.TIME_EPSILON);

  //@ public invariant 0 <= hp <= maxHp;
  //@ public invariant 1 <= maxHp;

  //@ public normal_behavior
  //@ ensures this.hp == this.maxHp;
  //@ pure
  public HealthComponent(int maxHp) {
    maxHp = Math.max(1, maxHp);
    this.maxHp = maxHp;
    this.hp = maxHp;
  }

  //@ public normal_behavior
  //@ assignable hasProtection;
  public void beginTick() {
    this.protectionSeconds = Math.max(
      0,
      this.protectionSeconds - Ticks.STEP_SECONDS
    );
  }

  //@ public normal_behavior
  //@ assignable hasProtection, hp;
  //@ ensures \result == (
  //@   (\old(this.hp) > 0) && !\old(hasProtection)
  //@ );
  public boolean takeDamage() {
    if (this.isDead() || this.isProtected())
      return false;

    this.hp--;
    if (!this.isDead())
      this.protectionSeconds = Health.DAMAGE_PROTECTION_SECONDS;
    return true;
  }

  //@ public normal_behavior
  //@ ensures \result == this.hp;
  //@ pure
  public int getHp() {
    return this.hp;
  }

  //@ public normal_behavior
  //@ ensures \result == this.maxHp;
  //@ pure
  public int getMaxHp() {
    return this.maxHp;
  }

  //@ public normal_behavior
  //@ ensures \result == (this.hp == 0);
  //@ pure
  public boolean isDead() {
    return this.hp == 0;
  }

  //@ public normal_behavior
  //@ ensures \result == hasProtection;
  //@ pure
  public boolean isProtected() {
    return this.protectionSeconds > Ticks.TIME_EPSILON;
  }
}
