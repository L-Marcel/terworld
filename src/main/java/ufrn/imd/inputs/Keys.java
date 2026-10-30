package ufrn.imd.inputs;

import java.util.Set;

public class Keys {
  public static final String HELP = "a/d: andar | k: pular | j: estocar | s: descer | ?: objetivo | Ctrl+C: sair";
  public static final Set<Integer> VALIDS = Set.of(
    3, // Control + C
    (int) 'w',
    (int) 'a',
    (int) 's',
    (int) 'd',
    (int) 'j',
    (int) 'k',
    (int) 'l'
  );
}
