package ufrn.imd.core.nodes;

import java.util.List;
import ufrn.imd.core.abstractions.Body2D;
import ufrn.imd.core.abstractions.Node2D;
import ufrn.imd.core.components.AttackComponent;
import ufrn.imd.core.enums.Action;
import ufrn.imd.core.enums.Direction;
import ufrn.imd.core.physics.CollisionMap;
import ufrn.imd.core.physics.Physics;
import ufrn.imd.core.physics.Vector2;
import ufrn.imd.core.resolvers.CombatResolver;

public class Sword extends Node2D {
  private final Body2D owner;
  private final AttackComponent attack = new AttackComponent();
  private final CombatResolver combat = new CombatResolver();
  private Direction facing = Direction.RIGHT;
  private final SwordTip tip;

  public Sword(Body2D owner) {
    super(owner.getPosition().sum(1, 0));
    this.owner = owner;
    this.tip = new SwordTip(this.position.sum(1, 0));
  }

  public void attack() {
    this.attack.request();
  }

  @Override
  public void process(Action action, Physics physics) {
    this.attack.process();
    CollisionMap collisions = physics.getCollisionMap();
    List<Vector2> cells = this.attack.isAttacking() ? this.reachableCells(collisions) : List.of();
    this.updateTip(cells);

    for (Vector2 position : cells) {
      Body2D target = collisions.getBodyAt(position, this.owner).orElse(null);
      this.combat.resolve(this.owner, target);
    }
  }

  private List<Vector2> reachableCells(CollisionMap collisions) {
    if (collisions.isSolid(this.position))
      return List.of();

    Vector2 tipPosition = this.position.sum(this.facing.step(), 0);
    if (collisions.isSolid(tipPosition))
      return List.of(this.position);

    return List.of(this.position, tipPosition);
  }

  private void updateTip(List<Vector2> cells) {
    this.removeChild(this.tip);
    this.tip.setPosition(this.position.sum(this.facing.step(), 0));
    this.tip.setDirection(this.facing);
    if (cells.size() == 2)
      this.addChild(this.tip);
  }

  public void follow(Direction facing) {
    this.facing = facing;
    this.position = this.owner.getPosition().sum(facing.step(), 0);
  }

  @Override
  public char getShape() {
    if (this.attack.isAttacking())
      return '-';
    return this.facing == Direction.LEFT ? '\\' : '/';
  }
}
