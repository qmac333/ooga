package ooga.model.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.function.Supplier;
import ooga.model.gameState.GameState;
import ooga.model.player.HumanPlayer;
import ooga.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class WildBlastTest {

  WildBlastCard wbc;
  Player realPlayer;
  Player mockedPlayer;

  @Mock
  Supplier colorSupplier;

  @Mock
  GameState mockedGameState;

  GameState gameState;

  @BeforeEach
  void start() {
    colorSupplier = mock(Supplier.class);
    when(colorSupplier.get()).thenReturn("red");
    mockedGameState = mock(GameState.class);
    gameState = new GameState();
    wbc = new WildBlastCard(null);
    realPlayer = new HumanPlayer("Paul", gameState, null, colorSupplier);
    mockedPlayer = new HumanPlayer("Paul", mockedGameState, null, colorSupplier);
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
