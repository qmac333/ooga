package ooga.model.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
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

public class DrawOneCardTest {

  GameState realGame;
  DrawOneCard dc;
  Player player;
  PlayerGroup group;

  @Mock
  GameState gameMocked;

  @BeforeEach
  void start()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    gameMocked = mock(GameState.class);
    group = new PlayerGroup(new HashMap<>(), gameMocked);
    realGame = new GameState();
    dc = new DrawOneCard("red");
    player = new HumanPlayer("Paul", group);
  }

  @Test
  void callsTheAddDrawWithCorrectArgument() {
    dc.executeAction(player);
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
    dc.executeAction(player);
    realGame.discardCard(dc);
    assertEquals("DrawOne", realGame.getLastCardThrownType());
  }
}
