package ufrn.imd.inputs;

import java.util.Set;

public class Keys {
  public static final long MOVEMENT_PULSE_NANOS = 150_000_000L;
  public static final int MAX_SEQUENCE_LENGTH = 64;
  public static final String ENTER_EVENT_MODE = "\u001b[>11u";
  public static final String EXIT_EVENT_MODE = "\u001b[<u";
  public static final String HELP = "a/d: andar | k: pular | j: atacar | s: descer | ?: objetivo | Ctrl+C: sair";
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
