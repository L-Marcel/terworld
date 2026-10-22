package ufrn.imd;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import ufrn.imd.inputs.Key;
import ufrn.imd.inputs.KeyEvent;
import ufrn.imd.inputs.KeyState;

public class Inputs {
  public static final long DEFAULT_RELEASE_TIMEOUT = 150;

  private final long releaseTimeout;
  private final Map<Key, Integer> events = new ConcurrentHashMap<>();
  private final Map<Key, Long> lastSeen = new ConcurrentHashMap<>();
  private final Set<Key> explicitKeys = ConcurrentHashMap.newKeySet();

  public Inputs() {
    this(DEFAULT_RELEASE_TIMEOUT);
  }

  public Inputs(long releaseTimeout) {
    this.releaseTimeout = releaseTimeout;
  }

  public void register(KeyEvent event) {
    this.events.put(event.key(), event.event());
    this.lastSeen.put(event.key(), System.currentTimeMillis());
    if (event.explicitRelease())
      this.explicitKeys.add(event.key());
  }

  public void clear() {
    this.events.clear();
    this.lastSeen.clear();
    this.explicitKeys.clear();
  }

  public void update() {
    long now = System.currentTimeMillis();

    for (Map.Entry<Key, Integer> entry : this.events.entrySet()) {
      Key key = entry.getKey();
      int state = entry.getValue();

      if (state == 1) {
        entry.setValue(2);
      } else if (state == 3) {
        this.events.remove(key);
        this.lastSeen.remove(key);
        this.explicitKeys.remove(key);
      } else if (state == 2 && !this.explicitKeys.contains(key)) {
        Long last = this.lastSeen.get(key);
        if (last != null && (now - last > this.releaseTimeout))
          entry.setValue(3);
      }
    }
  }

  public KeyState getState(Key key) {
    Integer event = this.events.get(key);
    if (event == null) return KeyState.NONE;

    if (event == 1) return KeyState.JUST_PRESSED;
    else if (event == 2) return KeyState.PRESSED;
    else if (event == 3) return KeyState.JUST_RELEASED;
    else return KeyState.NONE;
  }

  public KeyState getState(char character) {
    char lower = Character.toLowerCase(character);

    for (Map.Entry<Key, Integer> entry : this.events.entrySet()) {
      if (entry.getKey().character() == lower) {
        return this.getState(entry.getKey());
      }
    }

    return KeyState.NONE;
  }

  public KeyState getState(int code) {
    for (Map.Entry<Key, Integer> entry : this.events.entrySet()) {
      if (entry.getKey().keyCode() == code) {
        return this.getState(entry.getKey());
      }
    }

    return KeyState.NONE;
  }

  public boolean isJustPressed(Key key) {
    return KeyState.JUST_PRESSED.equals(this.getState(key));
  }

  public boolean isJustPressed(char character) {
    return this.isJustPressed(Key.of(character));
  }

  public boolean isJustPressed(int code) {
    return this.isJustPressed(Key.of(code));
  }

  public boolean isPressed(Key key) {
    return KeyState.JUST_PRESSED.equals(this.getState(key)) || 
      KeyState.PRESSED.equals(this.getState(key));
  }

  public boolean isPressed(char character) {
    return KeyState.JUST_PRESSED.equals(this.getState(character)) || 
      KeyState.PRESSED.equals(this.getState(character));
  }

  public boolean isPressed(int code) {
    return KeyState.JUST_PRESSED.equals(this.getState(code)) || 
      KeyState.PRESSED.equals(this.getState(code));
  }

  public boolean isJustReleased(Key key) {
    return KeyState.JUST_RELEASED.equals(this.getState(key));
  }

  public boolean isJustReleased(char character) {
    return this.isJustReleased(Key.of(character));
  }

  public boolean isJustReleased(int code) {
    return this.isJustReleased(Key.of(code));
  }

  public Set<Key> getJustPressedKeys() {
    return this.events.entrySet()
      .stream()
      .filter(entry -> this.isJustPressed(entry.getKey()))
      .map(Map.Entry::getKey)
      .collect(Collectors.toSet());
  }

  public Set<Key> getPressedKeys() {
    return this.events.entrySet()
      .stream()
      .filter(entry -> this.isPressed(entry.getKey()))
      .map(Map.Entry::getKey)
      .collect(Collectors.toSet());
  }

  public Set<Key> getJustReleasedKeys() {
    return this.events.entrySet()
      .stream()
      .filter(entry -> this.isJustReleased(entry.getKey()))
      .map(Map.Entry::getKey)
      .collect(Collectors.toSet());
  }
}
