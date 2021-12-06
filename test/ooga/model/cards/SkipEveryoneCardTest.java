package ooga.model.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import ooga.model.gameState.GameState;
import ooga.model.player.player.HumanPlayer;
import ooga.model.player.player.Player;
import ooga.model.player.playerGroup.PlayerGroup;
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
  PlayerGroup realGroup;

  @Mock
  PlayerGroup mockedGroup;

  @Mock
  GameState gameMocked;

  @BeforeEach
  void start()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    gameMocked = mock(GameState.class);
    realGame = new GameState();
    realGroup = new PlayerGroup(new HashMap<>(), realGame);
    mockedGroup = mock(PlayerGroup.class);
    sec = new SkipEveryoneCard("red");
    mockedPlayer = new HumanPlayer("Paul", mockedGroup);
    realPlayer = new HumanPlayer("Paul", realGroup);
  }

  @Test
  void callsSkipEveryoneInTheGroup() {
    sec.executeAction(mockedPlayer);
    verify(mockedGroup, times(1)).skipEveryone();
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
