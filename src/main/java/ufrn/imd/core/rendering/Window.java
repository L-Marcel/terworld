package ufrn.imd.core.rendering;

import java.util.List;

public record Window(
  int columns,
  int rows,
  List<String> content
) {}
