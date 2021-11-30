package ooga.model.drawRule;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import ooga.model.gameState.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NormalDrawRuleTest {
  NormalDrawRule normalDrawRule;
  GameState game;

  @BeforeEach
  public void start()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    normalDrawRule = new NormalDrawRule();
    game = new GameState("Basic", new HashMap<>(), 100, false);
    game.createDeck(new HashMap<>());
    game.createPlayers(null, null);
  }

  @Test
  public void noPlayDrawAlwaysReturnsOne(){
    assertEquals(1, normalDrawRule.noPlayDraw(game).size());
  }

  @Test
  public void forcedDrawReturnsListOfCorrectSize(){
    assertEquals(2, normalDrawRule.forcedDraw(game, 2).size());
    assertEquals(5, normalDrawRule.forcedDraw(game, 5).size());
    assertEquals(1, normalDrawRule.forcedDraw(game, 1).size());
  }
}
