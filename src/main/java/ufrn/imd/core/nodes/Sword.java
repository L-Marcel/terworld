package ufrn.imd.core.nodes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ufrn.imd.core.Controls;
import ufrn.imd.core.abstractions.Fighter;
import ufrn.imd.core.abstractions.Node2D;
import ufrn.imd.core.enums.Direction;
import ufrn.imd.core.physics.CollisionMap;
import ufrn.imd.core.physics.Physics;
import ufrn.imd.core.physics.Vector2;

public class Sword extends Node2D {
  private final Fighter owner;
  private final SwordTip tip;
  private final Set<Fighter> hitTargets;
  private List<Vector2> cells;
  private boolean wasAttacking;

  public Sword(Fighter owner) {
    super(owner.getPosition().sum(owner.getFacing().step(), 0));
    this.owner = owner;
    this.tip = new SwordTip(this);
    this.hitTargets = new HashSet<>();
    this.cells = List.of();
  }

  public Fighter getOwner() {
    return this.owner;
  }

  public List<Vector2> getCells() {
    return this.cells;
  }

  public boolean markHit(Fighter target) {
    return this.hitTargets.add(target);
  }

  @Override
  public void process(Controls controls, Physics physics) {
    this.position = this.owner.getPosition().sum(this.getFacing().step(), 0);
    boolean attacking = this.owner.isAttacking();
    if (attacking && !this.wasAttacking)
      this.hitTargets.clear();
    this.wasAttacking = attacking;
    this.cells = attacking ? this.reachableCells(physics.getCollisionMap()) : List.of();
    this.updateTip(this.cells);
  }

  private List<Vector2> reachableCells(CollisionMap collisions) {
    if (collisions.isSolid(this.position))
      return List.of();

    Vector2 tipPosition = this.position.sum(this.getFacing().step(), 0);
    if (collisions.isSolid(tipPosition))
      return List.of(this.position);

    return List.of(this.position, tipPosition);
  }

  private void updateTip(List<Vector2> cells) {
    this.removeChild(this.tip);
    this.tip.setPosition(this.position.sum(this.getFacing().step(), 0));
    if (cells.size() == 2)
      this.addChild(this.tip);
  }

  public Direction getFacing() {
    return this.owner.getFacing();
  }

  @Override
  public char getShape() {
    if (this.owner.isAttacking())
      return '-';
    return this.getFacing() == Direction.LEFT ? '\\' : '/';
  }
}
