package ufrn.imd.core;

import java.util.ArrayList;
import java.util.List;
import ufrn.imd.core.interfaces.Node;
import ufrn.imd.core.nodes.Player;

public record Level(
  int width,
  int height,
  char[][] content,
  Player player,
  List<Node> nodes
) {
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