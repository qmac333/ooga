package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameStatePlayerInterface;

/**
 * Card that has the next player draw until they set off the blaster
 *
 * @author Paul Truitt
 */
public class WildBlastCard extends Card {

  private final int DRAW_AMOUNT = -1;

  public WildBlastCard(String color, Supplier<String> supplier) {
    super("Black", "WildBlast", 50, supplier);
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
