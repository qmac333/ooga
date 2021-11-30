package ooga.model.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.function.Supplier;
import ooga.model.gameState.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class WildBlastTest {

  WildBlastCard wbc;

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
    wbc = new WildBlastCard(null, colorSupplier);
  }

  @Test
  void correctDefaultValues() {
    assertEquals("WildBlast", wbc.getType());
    assertEquals(50, wbc.getNum());
    assertEquals("Black", wbc.getMyColor());
  }

  @Test
  void callsTheAddDrawWithCorrectArgument() {
    wbc.executeAction(mockedGameState);
    verify(mockedGameState, times(1)).addDraw(-1);
  }

  @Test
  void colorOfCardChangesOnExecution() {
    wbc.executeAction(mockedGameState);
    assertEquals("red", wbc.getMyColor());
  }

  @Test
  void successfullyDiscards() {
    wbc.executeAction(gameState);
    assertEquals("WildBlast", gameState.getLastCardThrownType());
  }
}
