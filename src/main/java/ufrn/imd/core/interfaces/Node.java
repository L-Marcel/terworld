package ufrn.imd.core.interfaces;

import java.util.List;
import ufrn.imd.inputs.Key;

public interface Node {
  public void process(Key key);
  public List<Node> getChildren();
}
