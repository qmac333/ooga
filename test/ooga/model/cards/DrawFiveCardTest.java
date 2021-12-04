package ooga.model.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import ooga.model.gameState.GameState;
import ooga.model.player.HumanPlayer;
import ooga.model.player.Player;
import ooga.model.player.PlayerGroup;
import ooga.model.player.PlayerGroupInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class DrawFiveCardTest {

  GameState realGame;
  DrawFiveCard dfc;
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
    dfc = new DrawFiveCard("red");
    player = new HumanPlayer("Paul", group);
  }

  @Test
  void callsTheAddDrawWithCorrectArgument() {
    dfc.executeAction(player);
    verify(gameMocked, times(1)).addDraw(5);
  }

  @Test
  void defaultsProperlySet() {
    assertEquals("DrawFive", dfc.getType());
    assertEquals(20, dfc.getNum());
  }

  @Test
  void colorSets() {
    assertEquals("red", dfc.getMyColor());
  }

  @Test
  void successfullyDiscards() {
    dfc.executeAction(player);
    realGame.discardCard(dfc);
    assertEquals("DrawFive", realGame.getLastCardThrownType());
  }
}
