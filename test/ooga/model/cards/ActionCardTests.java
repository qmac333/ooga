package ooga.model.cards;

import java.util.HashMap;
import java.util.function.Supplier;
import ooga.model.gameState.GameState;
import ooga.model.player.ComputerPlayer;
import ooga.model.player.HumanPlayer;
import ooga.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ActionCardTests extends DukeApplicationTest {

  GameState game;
  Player player;

  @Mock
  Supplier colorSupplier;

  @BeforeEach
  public void start() {
    game = new GameState("Basic", new HashMap<>(), 100, false);
    colorSupplier = mock(Supplier.class);
    when(colorSupplier.get()).thenReturn("red");
    player = new HumanPlayer("Paul", game, null, colorSupplier);
  }

  @Test
  public void drawFourCardTest() {
    Card dfc = new WildDrawFourCard("black");
    dfc.executeAction(player);
    game.discardCard(dfc);
    assertEquals("DrawFour", game.getLastCardThrownType());
  }

  @Test
  public void drawTwoCardTest() {
    Card dtc = new DrawTwoCard("red");
    dtc.executeAction(player);
    game.discardCard(dtc);
    assertEquals("DrawTwo", game.getLastCardThrownType());
  }

  @Test
  public void reverseCardTest() {

    Card rc = new ReverseCard("red");
    rc.executeAction(player);
    assertEquals(-1, game.getOrder());
  }

  @Test
  public void skipCardTest() {
    Card sc = new SkipCard("red");
    sc.executeAction(player);
    game.discardCard(sc);
    assertEquals("Skip", game.getLastCardThrownType());
  }

  @Test
  public void wildCardTest() {
    Card wc = new WildCard("wild");
    // FIXME: Change this line once front-end support is in
    wc.setCardColor("red");
    assertEquals("red", wc.getMyColor());
  }
}
