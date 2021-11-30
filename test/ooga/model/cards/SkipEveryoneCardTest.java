package ooga.model.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ooga.model.gameState.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class SkipEveryoneCardTest {

  GameState realGame;
  SkipEveryoneCard sec;

  @Mock
  GameState gameMocked;

  @BeforeEach
  void start() {
    gameMocked = mock(GameState.class);
    realGame = new GameState();
    sec = new SkipEveryoneCard("red", null);
  }

  @Test
  void callsTheFlipMethodInTheGame() {
    sec.executeAction(gameMocked);
    verify(gameMocked, times(1)).skipEveryone();
  }

  @Test
  void allDefaultsAreSetHowTheyShouldBe() {
    assertEquals("SkipEveryone", sec.getType());
    assertEquals(30, sec.getNum());
  }

  @Test
  void colorSets() {
    assertEquals("red", sec.getMyColor());
  }

  @Test
  void successfullyDiscards() {
    sec.executeAction(realGame);
    assertEquals("SkipEveryone", realGame.getLastCardThrownType());
  }
}
