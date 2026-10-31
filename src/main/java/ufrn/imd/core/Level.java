package ufrn.imd.core;

import java.util.ArrayList;
import java.util.List;
import ufrn.imd.core.abstractions.Node;
import ufrn.imd.core.nodes.Block;
import ufrn.imd.core.nodes.EndOfLevel;
import ufrn.imd.core.nodes.Enemy;
import ufrn.imd.core.nodes.Player;
import ufrn.imd.core.nodes.Spike;
import ufrn.imd.core.physics.Grid;
import ufrn.imd.core.physics.Vector2;

public record Level(
  Grid content,
  Player player,
  List<Node> nodes
) {
  public int width() {
    return this.content.getWidth();
  }

  public int height() {
    return this.content.getHeight();
  }

  public Level copy() {
    Player player = new Player(this.player.getPosition());
    List<Node> nodes = new ArrayList<>();

    for (Node node : this.nodes) {
      nodes.add(switch (node) {
        case Player _ -> player;
        case Enemy enemy -> new Enemy(enemy.getPosition());
        case Spike spike -> new Spike(spike.getPosition());
        case EndOfLevel end -> new EndOfLevel(end.getPosition());
        case Block block -> new Block(block.getPosition());
        default -> node;
      });
    }

    return new Level(
      this.content.copy(),
      player,
      nodes
    );
  }

  public static Level empty() {
    return new Level(
      new Grid(0, 0),
      new Player(Vector2.ZERO),
      new ArrayList<>()
    );
  }
}
