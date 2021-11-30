package ooga.model.cards;

import ooga.model.gameState.GameState;
import ooga.model.player.HumanPlayer;
import ooga.model.player.Player;
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

  Player player;

  @Mock
  GameState gameMocked;

  @BeforeEach
  void start() {
    gameMocked = mock(GameState.class);
    realGame = new GameState();
    fc = new FlipCard("red");
    player = new HumanPlayer("Paul", gameMocked, null, null);
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
    fc.executeAction(player);
    realGame.discardCard(fc);
    assertEquals("Flip", realGame.getLastCardThrownType());
  }
}
