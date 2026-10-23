package ufrn.imd.core;

import java.util.List;

public record Window(
  int columns,
  int rows,
  List<String> content
) {}
