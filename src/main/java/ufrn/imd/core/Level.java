package ufrn.imd.core;

import java.util.List;
import ufrn.imd.core.interfaces.Node;
import ufrn.imd.core.nodes.Player;

public record Level(
  int width,
  int height,
  char[][] content,
  Player player,
  List<Node> nodes
) {}