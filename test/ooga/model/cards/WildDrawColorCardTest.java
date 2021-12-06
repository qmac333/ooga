package ooga.model.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.function.Supplier;
import ooga.model.cards.individualCards.WildDrawColorCard;
import ooga.model.gameState.GameState;
import ooga.model.player.player.HumanPlayer;
import ooga.model.player.player.Player;
import ooga.model.player.playerGroup.PlayerGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class WildDrawColorCardTest {

  WildDrawColorCard wdcc;
  Player mockedPlayer;
  PlayerGroup group;

  @Mock
  Supplier colorSupplier;

  @Mock
  GameState mockedGameState;

  GameState gameState;

  @BeforeEach
  void start()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    colorSupplier = mock(Supplier.class);
    when(colorSupplier.get()).thenReturn("red");
    mockedGameState = mock(GameState.class);
    gameState = new GameState();
    wdcc = new WildDrawColorCard("Black");
    group = new PlayerGroup(new HashMap<>(), mockedGameState);
    mockedPlayer = new HumanPlayer("Paul", group);
    mockedPlayer.setSuppliers(null, colorSupplier);
  }

  @Test
  void correctDefaultValues() {
    assertEquals("WildDrawColor", wdcc.getType());
    assertEquals(60, wdcc.getNum());
  }

  @Test
  void callsTheAddDrawWithCorrectArgument() {
    wdcc.executeAction(mockedPlayer);
    verify(mockedGameState, times(1)).addDraw(-2);
  }

  @Test
  void colorOfCardChangesOnExecution() {
    wdcc.executeAction(mockedPlayer);
    assertEquals("red", wdcc.getMyColor());
  }

  @Test
  void successfullyDiscards() {
    wdcc.executeAction(mockedPlayer);
    gameState.discardCard(wdcc);
    assertEquals("WildDrawColor", gameState.getLastCardThrownType());
  }
}
