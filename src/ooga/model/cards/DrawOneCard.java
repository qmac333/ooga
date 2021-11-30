package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameStatePlayerInterface;

/**
 * Card to force next player to draw one card
 *
 * @author Paul Truitt
 */
public class DrawOneCard extends Card {

  private final int DRAW_AMOUNT = 1;

  public DrawOneCard(String color, Supplier<String> supplier) {
    super(color, "DrawOne", 10, supplier);
  }

  @Override
  public void executeAction(GameStatePlayerInterface game) {
    game.addDraw(DRAW_AMOUNT);
    game.discardCard(this);
  }

  @Override
  public void flip() {
    // Do nothing
  }
}
