package ufrn.imd.core.components;

public class HealthComponent {
  private static final int DAMAGE_PROTECTION_TICKS = 3;
  private final int maxHp;
  private int hp;
  private int protectionTicks = 0;

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
      this.protectionTicks = DAMAGE_PROTECTION_TICKS;
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
