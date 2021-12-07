package ooga.model.drawRule;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import ooga.model.cards.NumberCard;
import ooga.model.cards.SkipCard;
import ooga.model.cards.WildCard;
import ooga.model.gameState.GameState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class NormalDrawRuleTest {
  NormalDrawRule normalDrawRule;
  GameState game;

  @Mock
  GameState gameStateMocked;

  @BeforeEach
  public void start()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    normalDrawRule = new NormalDrawRule();
    game = new GameState("Basic", new HashMap<>(), 100, false);
    gameStateMocked = Mockito.mock(GameState.class);
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

  @Test
  public void whenBlasterProbIs1ItGivesUsOneCardForDrawTillBlast(){
    normalDrawRule.setBlastProbability(1);
    Assertions.assertEquals(1, normalDrawRule.drawUntilBlast(game, null).size());
  }

  @Test
  public void drawTillColorStopsImmediately(){
    Mockito.when(gameStateMocked.getNextCard()).thenReturn(new SkipCard("Red"));
    assertEquals(1, normalDrawRule.drawUntilColor(gameStateMocked, "Red").size());
  }

  @Test
  public void drawTillColorKeepsGoingWhenItShould(){
    Mockito.when(gameStateMocked.getNextCard()).thenReturn(new SkipCard("Red"),new SkipCard("Blue"));
    assertEquals(2, normalDrawRule.drawUntilColor(gameStateMocked, "Blue").size());
  }

  @Test
  public void drawTillColorKeepsGoingWhenItShouldPartTwo(){
    Mockito.when(gameStateMocked.getNextCard()).thenReturn(new SkipCard("Red"),new SkipCard("Blue"), new NumberCard("Green", 9), new NumberCard("Yellow", 5));
    assertEquals(4, normalDrawRule.drawUntilColor(gameStateMocked, "Yellow").size());
  }

  @Test
  public void blackCardsDontSatisfy(){
    Mockito.when(gameStateMocked.getNextCard()).thenReturn(new SkipCard("Red"),new WildCard("Black"), new NumberCard("Green", 9), new NumberCard("Yellow", 5));
    assertEquals(4, normalDrawRule.drawUntilColor(gameStateMocked, "Yellow").size());
  }

  @Test
  public void blastedAlwaysGoesFalse(){
    assertFalse(normalDrawRule.blasted());
    Mockito.when(gameStateMocked.getNextCard()).thenReturn(new SkipCard("Red"));
    normalDrawRule.noPlayDraw(gameStateMocked);
    assertFalse(normalDrawRule.blasted());
    normalDrawRule.forcedDraw(gameStateMocked, 5);
    assertFalse(normalDrawRule.blasted());
  }

  @Test
  public void cardsInBlasterAlwaysNull(){
    assertNull(normalDrawRule.getBlasterCards());
    Mockito.when(gameStateMocked.getNextCard()).thenReturn(new SkipCard("Red"));
    normalDrawRule.noPlayDraw(gameStateMocked);
    assertNull(normalDrawRule.getBlasterCards());
    normalDrawRule.forcedDraw(gameStateMocked, 5);
    assertNull(normalDrawRule.getBlasterCards());
  }
}
