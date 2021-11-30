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

public class WildDrawTwoCardTest {

  WildDrawTwoCard wdt;

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
    wdt = new WildDrawTwoCard(null, colorSupplier);
  }

  @Test
  void correctDefaultValues() {
    assertEquals("WildDrawTwo", wdt.getType());
    assertEquals(50, wdt.getNum());
    assertEquals("Black", wdt.getMyColor());
  }

  @Test
  void callsTheAddDrawWithCorrectArgument() {
    wdt.executeAction(mockedGameState);
    verify(mockedGameState, times(1)).addDraw(2);
  }

  @Test
  void colorOfCardChangesOnExecution() {
    wdt.executeAction(mockedGameState);
    assertEquals("red", wdt.getMyColor());
  }

  @Test
  void successfullyDiscards() {
    wdt.executeAction(gameState);
    assertEquals("WildDrawTwo", gameState.getLastCardThrownType());
  }
}