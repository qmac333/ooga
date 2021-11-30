package ooga.model.cards;

import ooga.model.gameState.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FlipCardTest {

  GameState realGame;
  FlipCard fc;

  @Mock
  GameState gameMocked;

  @BeforeEach
  void start() {
    gameMocked = mock(GameState.class);
    realGame = new GameState();
    fc = new FlipCard("red", null);
  }

  @Test
  void callsTheFlipMethodInTheGame() {
    fc.executeAction(gameMocked);
    verify(gameMocked, times(1)).flipCards();
  }

  @Test
  void allDefaultsAreSetHowTheyShouldBe() {
    assertEquals("Flip", fc.getType());
    assertEquals(20, fc.getNum());
  }

  @Test
  void colorSets() {
    assertEquals("red", fc.getMyColor());
  }

  @Test
  void successfullyDiscards() {
    fc.executeAction(realGame);
    assertEquals("Flip", realGame.getLastCardThrownType());
  }
}
