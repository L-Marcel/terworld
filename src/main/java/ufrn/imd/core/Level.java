package ufrn.imd.core;

import java.util.ArrayList;
import java.util.List;
import ufrn.imd.core.abstractions.Node;
import ufrn.imd.core.nodes.Block;
import ufrn.imd.core.nodes.EndOfLevel;
import ufrn.imd.core.nodes.Enemy;
import ufrn.imd.core.nodes.Player;
import ufrn.imd.core.nodes.Spike;

public record Level(
  int width,
  int height,
  char[][] content,
  Player player,
  List<Node> nodes
) {
  public Level copy() {
    char[][] content = new char[this.content.length][];
    for (int row = 0; row < content.length; row++)
      content[row] = this.content[row].clone();

    Player player = new Player(this.player.getPosition());
    List<Node> nodes = new ArrayList<>();

    for (Node node : this.nodes) {
      nodes.add(switch (node) {
        case Player ignored -> player;
        case Enemy enemy -> new Enemy(enemy.getPosition());
        case Spike spike -> new Spike(spike.getPosition());
        case EndOfLevel end -> new EndOfLevel(end.getPosition());
        case Block block -> new Block(block.getPosition());
        default -> throw new IllegalArgumentException("Unsupported level node: " + node.getClass().getName());
      });
    }

    return new Level(this.width, this.height, content, player, nodes);
  }

  public static Level empty() {
    return new Level(
      0,
      0,
      new char[0][0],
      new Player(Vector2.ZERO),
      new ArrayList<>()
    );
  }
}
