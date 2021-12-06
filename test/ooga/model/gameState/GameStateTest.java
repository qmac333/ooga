package ooga.model.gameState;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;

public class GameStateTest {
  GameState game;

  @BeforeEach
  void start()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    game = new GameState("Basic", new HashMap<>(), 100, false);
  }
}
