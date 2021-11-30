package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameStatePlayerInterface;

/**
 * Card that lets the user pick a color and then have the next player draw until the draw a card of
 * the correct color
 *
 * @author Paul Truitt
 */
public class WildDrawColorCard extends Card {

  private final int DRAW_AMOUNT = -2;

  public WildDrawColorCard(String color, Supplier<String> supplier) {
    super("Black", "WildDrawColor", 60, supplier);
  }

  @Override
  public void executeAction(GameStatePlayerInterface game) {
    game.addDraw(DRAW_AMOUNT);
    super.setCardColor(super.getSupplier().get());
    game.discardCard(this);
  }

  @Override
  public void flip() {
    // Do nothing
  }
}
