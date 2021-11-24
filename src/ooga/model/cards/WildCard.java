package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameStatePlayerInterface;

public class WildCard extends Card {

  public WildCard(String color, Supplier<String> supplier) {
    super("Black", "Wild", 50, supplier);
  }

  @Override
  public void executeAction(GameStatePlayerInterface game) {
    // FIXME: Once we have the interface figured out
    super.setCardColor(super.getSupplier().get());
    game.discardCard(this);
  }

  @Override
  public void flip() {
    // Do nothing
  }
}
