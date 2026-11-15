package ufrn.imd.core;

public record Controls(
  int horizontal,
  boolean jump,
  boolean attack,
  boolean drop
) {
  //@ public invariant -1 <= horizontal <= 1;

  //@ public normal_behavior
  //@ ensures (\old(horizontal) < 0) ==> this.horizontal == -1;
  //@ ensures (\old(horizontal) == 0) ==> this.horizontal == 0;
  //@ ensures (\old(horizontal) > 0) ==> this.horizontal == 1;
  //@ ensures this.jump == jump;
  //@ ensures this.attack == attack;
  //@ ensures this.drop == drop;
  //@ pure
  public Controls { horizontal = Integer.signum(horizontal); }

  //@ public normal_behavior
  //@ ensures \fresh(\result);
  //@ ensures \result.horizontal == 0;
  //@ ensures !\result.jump;
  //@ ensures !\result.attack;
  //@ ensures !\result.drop;
  //@ pure
  public static Controls none() {
    return new Controls(0, false, false, false);
  }
}
