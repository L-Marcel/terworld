package ufrn.imd.core;

import java.util.List;

public record Screen(
  int columns,
  int rows, 
  List<String> content
) {}
