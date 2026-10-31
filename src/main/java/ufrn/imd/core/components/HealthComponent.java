package ufrn.imd.core.components;

import ufrn.imd.core.constants.Health;

public class HealthComponent {
  private int protectionTicks = 0;
  private final int maxHp;
  private int hp;

  public HealthComponent(int maxHp) {
    maxHp = Math.max(1, maxHp);
    this.maxHp = maxHp;
    this.hp = maxHp;
  }

  public void beginTick() {
    if (this.protectionTicks > 0)
      this.protectionTicks--;
  }

  public boolean takeDamage() {
    if (this.isDead() || this.isProtected())
      return false;

    this.hp--;
    if (!this.isDead())
      this.protectionTicks = Health.DAMAGE_PROTECTION_TICKS;
    return true;
  }

  public int getHp() {
    return this.hp;
  }

  public int getMaxHp() {
    return this.maxHp;
  }

  public boolean isDead() {
    return this.hp == 0;
  }

  public boolean isProtected() {
    return this.protectionTicks > 0;
  }
}
