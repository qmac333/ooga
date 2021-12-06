package ooga.model.cards;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import ooga.model.gameState.GameState;
import ooga.model.player.player.HumanPlayer;
import ooga.model.player.player.Player;
import ooga.model.player.playerGroup.PlayerGroup;
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
  PlayerGroup group;

  Player player;

  @Mock
  GameState gameMocked;

  @Mock
  PlayerGroup groupMocked;

  @BeforeEach
  void start()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    gameMocked = mock(GameState.class);
    group = new PlayerGroup(new HashMap<>(), gameMocked);
    groupMocked = mock(PlayerGroup.class);
    realGame = new GameState();
    fc = new FlipCard("red");
    player = new HumanPlayer("Paul", groupMocked);
  }

  @Test
  void callsTheFlipMethodInTheGame() {
    fc.executeAction(player);
    verify(groupMocked, times(1)).flipGame();
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
