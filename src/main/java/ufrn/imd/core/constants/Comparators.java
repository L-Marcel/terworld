package ufrn.imd.core.constants;

import java.util.Comparator;
import ufrn.imd.core.abstractions.Node;
import ufrn.imd.core.abstractions.Node2D;

public class Comparators {
  public static final Comparator<Node> BY_LAYER = Comparator.comparingInt(
    node -> {
      if (node instanceof Node2D node2d)
        return node2d.getLayer();

      return 0;
    }
  );
}
