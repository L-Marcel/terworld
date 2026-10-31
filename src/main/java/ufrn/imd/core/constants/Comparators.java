package ufrn.imd.core.constants;

import java.util.Comparator;
import ufrn.imd.core.abstractions.Node2D;

public final class Comparators {
  public static final Comparator<Node2D> BY_LAYER = Comparator.comparingInt(Node2D::getLayer);

  private Comparators() {}
}
