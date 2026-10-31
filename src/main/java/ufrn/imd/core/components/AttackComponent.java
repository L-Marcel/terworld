package ufrn.imd.core.components;

public class AttackComponent {
  private boolean requested = false;
  private boolean attacking = false;
  private int recoveryTicks = 0;

  public void request() {
    this.requested = true;
  }

  public void process() {
    this.attacking = this.requested && this.recoveryTicks == 0;
    this.requested = false;

    if (this.attacking)
      this.recoveryTicks = 1;
    else if (this.recoveryTicks > 0)
      this.recoveryTicks--;
  }

  public boolean isAttacking() {
    return this.attacking;
  }
}
