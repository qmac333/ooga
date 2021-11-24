package ooga.model.cards;

import java.util.HashMap;
import java.util.function.Supplier;
import ooga.model.gameState.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ActionCardTests extends DukeApplicationTest {

  GameState game;

  @Mock
  Supplier colorSupplier;

  @BeforeEach
  public void start() {
    game = new GameState("Basic", new HashMap<>(), 100, false);
    colorSupplier = mock(Supplier.class);
    when(colorSupplier.get()).thenReturn("red");
  }

  @Test
  public void drawFourCardTest() {
    Card dfc = new DrawFourCard("black", colorSupplier);
    dfc.executeAction(game);
    assertEquals("DrawFour", game.getLastCardThrownType());
  }

  @Test
  public void drawTwoCardTest() {
    Card dtc = new DrawTwoCard("red", null);
    dtc.executeAction(game);
    assertEquals("DrawTwo", game.getLastCardThrownType());
  }

  @Test
  public void reverseCardTest() {

    Card rc = new ReverseCard("red", null);
    rc.executeAction(game);
    assertEquals(-1, game.getOrder());
  }

  @Test
  public void skipCardTest() {
    Card sc = new SkipCard("red", null);
    sc.executeAction(game);
    assertEquals("Skip", game.getLastCardThrownType());
  }

  @Test
  public void wildCardTest() {
    Card wc = new WildCard("wild", null);
    // FIXME: Change this line once front-end support is in
    wc.setCardColor("red");
    assertEquals("red", wc.getMyColor());
  }
}
