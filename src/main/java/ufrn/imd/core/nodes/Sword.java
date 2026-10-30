package ufrn.imd.core.nodes;

import java.util.List;
import ufrn.imd.core.Action;
import ufrn.imd.core.Physics;
import ufrn.imd.core.Vector2;
import ufrn.imd.core.abstractions.Body2D;
import ufrn.imd.core.abstractions.Node2D;

public class Sword extends Node2D {
  private boolean flipped = false;
  private boolean attacking = false;
  private boolean attackRequested = false;
  private int recoveryTicks = 0;
  private final SwordTip tip;

  public Sword(Vector2 position) {
    super(position);
    this.tip = new SwordTip(position.sum(1, 0));
  }

  public void attack() {
    this.attackRequested = true;
  }

  @Override
  public void process(Action action, Physics physics) {
    this.tip.setPosition(this.position.sum(this.flipped ? -1 : 1, 0));
    this.tip.setFlipped(this.flipped);
    this.attacking = this.attackRequested && this.recoveryTicks == 0;
    this.attackRequested = false;

    if (this.attacking)
      this.recoveryTicks = 1;
    else if (this.recoveryTicks > 0)
      this.recoveryTicks--;

    this.removeChild(this.tip);

    if (!this.attacking || physics.isSolid(this.position))
      return;

    if (!physics.isSolid(this.tip.getPosition()))
      this.addChild(this.tip);

    if (!(this.getParent().orElse(null) instanceof Body2D owner))
      return;

    for (Vector2 position : List.of(this.position, this.tip.getPosition())) {
      if (physics.isSolid(position))
        break;

      Body2D target = physics.getBodyAt(position, owner).orElse(null);

      if (owner instanceof Player && target instanceof Enemy)
        target.queueFree();
      else if (owner instanceof Enemy && target instanceof Player player)
        player.takeDamage();
    }
  }

  public void setPosition(Vector2 position) {
    this.position = position;
  }

  public void setFlipped(boolean flipped) {
    this.flipped = flipped;
  }

  @Override
  public char getShape() {
    return this.attacking ? '-' : this.flipped ? '\\' : '/';
  }
}
