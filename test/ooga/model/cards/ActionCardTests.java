package ooga.model.cards;

import java.util.HashMap;
import ooga.model.gameState.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionCardTests extends DukeApplicationTest {

  GameState game;

  @BeforeEach
  public void start() {
    game = new GameState("Basic", new HashMap<>(), 100, false);
  }

  @Test
  public void drawFourCardTest() {
    Card dfc = new DrawFourCard("red");
    dfc.executeAction(game);
    assertEquals("DrawFour", game.getLastCardThrownType());
  }

  @Test
  public void drawTwoCardTest() {
    Card dtc = new DrawTwoCard("red");
    dtc.executeAction(game);
    assertEquals("DrawTwo", game.getLastCardThrownType());
  }

  @Test
  public void reverseCardTest() {

    Card rc = new ReverseCard("red");
    rc.executeAction(game);
    assertEquals(-1, game.getOrder());
  }

  @Test
  public void skipCardTest() {
    Card sc = new SkipCard("red");
    sc.executeAction(game);
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
