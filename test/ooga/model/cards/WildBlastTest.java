package ooga.model.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.function.Supplier;
import ooga.model.gameState.GameState;
import ooga.model.player.HumanPlayer;
import ooga.model.player.Player;
import ooga.model.player.PlayerGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class WildBlastTest {

  WildBlastCard wbc;
  Player realPlayer;
  Player mockedPlayer;
  PlayerGroup realGroup;
  PlayerGroup mockedGroup;

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
    realGroup = new PlayerGroup(new HashMap<>(), gameState);
    mockedGroup = new PlayerGroup(new HashMap<>(), mockedGameState);
    wbc = new WildBlastCard(null);
    realPlayer = new HumanPlayer("Paul", realGroup);
    mockedPlayer = new HumanPlayer("Paul", mockedGroup);
    realPlayer.setSuppliers(null, colorSupplier);
    mockedPlayer.setSuppliers(null, colorSupplier);
  }

  @Test
  void correctDefaultValues() {
    assertEquals("WildBlast", wbc.getType());
    assertEquals(50, wbc.getNum());
    assertEquals("Black", wbc.getMyColor());
  }

  @Test
  void callsTheAddDrawWithCorrectArgument() {
    wbc.executeAction(mockedPlayer);
    verify(mockedGameState, times(1)).addDraw(-1);
  }

  @Test
  void colorOfCardChangesOnExecution() {
    wbc.executeAction(mockedPlayer);
    assertEquals("red", wbc.getMyColor());
  }

  @Test
  void successfullyDiscards() {
    wbc.executeAction(realPlayer);
    gameState.discardCard(wbc);
    assertEquals("WildBlast", gameState.getLastCardThrownType());
  }
}
