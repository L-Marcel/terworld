package ufrn.imd.core.components;

import ufrn.imd.core.constants.Health;
import ufrn.imd.core.constants.Ticks;

public class HealthComponent {
  private double protectionSeconds = 0;
  private final int maxHp;
  private int hp;

  public HealthComponent(int maxHp) {
    maxHp = Math.max(1, maxHp);
    this.maxHp = maxHp;
    this.hp = maxHp;
  }

  public void beginTick() {
    this.protectionSeconds = Math.max(
      0,
      this.protectionSeconds - Ticks.STEP_SECONDS
    );
  }

  public boolean takeDamage() {
    if (this.isDead() || this.isProtected())
      return false;

    this.hp--;
    if (!this.isDead())
      this.protectionSeconds = Health.DAMAGE_PROTECTION_SECONDS;
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
    return this.protectionSeconds > Ticks.TIME_EPSILON;
  }
}
