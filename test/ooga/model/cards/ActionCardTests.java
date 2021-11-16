package ooga.model.cards;

import ooga.model.gameState.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionCardTests extends DukeApplicationTest {

  GameState game;

  @BeforeEach
  public void start() {
    game = new GameState(null, null, 100, false);
  }

  @Test
  public void drawFourCardTest() {
    Card dfc = new DrawFourCard(game, "red");
    dfc.executeAction();
    assertEquals("DrawFour", game.getLastCardThrownType());
  }

  @Test
  public void drawTwoCardTest() {
    Card dtc = new DrawTwoCard(game, "red");
    dtc.executeAction();
    assertEquals("DrawTwo", game.getLastCardThrownType());
  }

  @Test
  public void reverseCardTest() {

    Card rc = new ReverseCard(game, "red");
    rc.executeAction();
    assertEquals(-1, game.getOrder());
  }

  @Test
  public void skipCardTest() {
    Card sc = new SkipCard(game, "red");
    sc.executeAction();
    assertEquals("Skip", game.getLastCardThrownType());
  }

  @Test
  public void wildCardTest() {
    Card wc = new WildCard(game, "wild");
    // FIXME: Change this line once front-end support is in
    wc.setCardColor("red");
    assertEquals("red", wc.getMyColor());
  }
}
