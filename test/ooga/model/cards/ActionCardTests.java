package ooga.model.cards;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.function.Supplier;
import ooga.model.gameState.GameState;
import ooga.model.player.HumanPlayer;
import ooga.model.player.Player;
import ooga.model.player.PlayerGroup;
import ooga.model.player.PlayerGroupInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ActionCardTests extends DukeApplicationTest {

  PlayerGroupInterface group;
  Player player;
  GameState game;

  @Mock
  Supplier colorSupplier;

  @BeforeEach
  public void start()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    game = new GameState();
    group = new PlayerGroup(new HashMap<>(), game);
    colorSupplier = mock(Supplier.class);
    when(colorSupplier.get()).thenReturn("red");
    player = new HumanPlayer("Paul", group);
    player.setSuppliers(null, colorSupplier);
  }

  @Test
  public void drawFourCardTest() {
    OneSidedCard dfc = new WildDrawFourCard("black");
    dfc.executeAction(player);
    game.discardCard(dfc);
    assertEquals("WildDrawFour", game.getLastCardThrownType());
  }

  @Test
  public void drawTwoCardTest() {
    OneSidedCard dtc = new DrawTwoCard("red");
    dtc.executeAction(player);
    game.discardCard(dtc);
    assertEquals("DrawTwo", game.getLastCardThrownType());
  }

  @Test
  public void reverseCardTest() {

    OneSidedCard rc = new ReverseCard("red");
    rc.executeAction(player);
    assertEquals(-1, group.getMyOrder());
  }

  @Test
  public void skipCardTest() {
    OneSidedCard sc = new SkipCard("red");
    sc.executeAction(player);
    game.discardCard(sc);
    assertEquals("Skip", game.getLastCardThrownType());
  }

  @Test
  public void wildCardTest() {
    OneSidedCard wc = new WildCard("wild");
    // FIXME: Change this line once front-end support is in
    wc.setCardColor("red");
    assertEquals("red", wc.getMyColor());
  }
}
