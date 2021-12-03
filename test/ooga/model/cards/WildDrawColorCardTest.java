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

public class WildDrawColorCardTest {

  WildDrawColorCard wdcc;
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
    wdcc = new WildDrawColorCard(null);
    mockedPlayer = new HumanPlayer("Paul", mockedGameState);
    mockedPlayer.setSuppliers(null, colorSupplier);
  }

  @Test
  void correctDefaultValues() {
    assertEquals("WildDrawColor", wdcc.getType());
    assertEquals(60, wdcc.getNum());
    assertEquals("Black", wdcc.getMyColor());
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
