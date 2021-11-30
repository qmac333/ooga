package ooga.model.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ooga.model.gameState.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class DrawOneCardTest {

  GameState realGame;
  DrawOneCard dc;

  @Mock
  GameState gameMocked;

  @BeforeEach
  void start() {
    gameMocked = mock(GameState.class);
    realGame = new GameState();
    dc = new DrawOneCard("red", null);
  }

  @Test
  void callsTheAddDrawWithCorrectArgument() {
    dc.executeAction(gameMocked);
    verify(gameMocked, times(1)).addDraw(1);
  }

  @Test
  void defaultsProperlySet() {
    assertEquals("DrawOne", dc.getType());
    assertEquals(10, dc.getNum());
  }

  @Test
  void colorSets() {
    assertEquals("red", dc.getMyColor());
  }

  @Test
  void successfullyDiscards() {
    dc.executeAction(realGame);
    assertEquals("DrawOne", realGame.getLastCardThrownType());
  }
}
