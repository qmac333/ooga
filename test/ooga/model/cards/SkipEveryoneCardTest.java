package ooga.model.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ooga.model.gameState.GameState;
import ooga.model.player.HumanPlayer;
import ooga.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * Card that causes the game to skip everyone and go back to the user
 *
 * @author Paul Truitt
 */
public class SkipEveryoneCardTest {

  GameState realGame;
  SkipEveryoneCard sec;
  Player mockedPlayer;
  Player realPlayer;

  @Mock
  GameState gameMocked;

  @BeforeEach
  void start() {
    gameMocked = mock(GameState.class);
    realGame = new GameState();
    sec = new SkipEveryoneCard("red");
    mockedPlayer = new HumanPlayer("Paul", gameMocked, null, null);
    realPlayer = new HumanPlayer("Paul", realGame, null, null);
  }

  @Test
  void callsTheFlipMethodInTheGame() {
    sec.executeAction(mockedPlayer);
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
    sec.executeAction(realPlayer);
    realGame.discardCard(sec);
    assertEquals("SkipEveryone", realGame.getLastCardThrownType());
  }
}
