package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameStatePlayerInterface;

public class DrawFourCard extends Card {

  private final int DRAW_AMOUNT = 4;

  public DrawFourCard(String color, Supplier<String> supplier) {
    super("Black", "DrawFour", 50, supplier);
  }

  @Override
  public void executeAction(GameStatePlayerInterface game) {
    game.addDraw(DRAW_AMOUNT);
    // FIXME: Once we have the interface figured out
    super.setCardColor(super.getSupplier().get());
    game.discardCard(this);
  }

  @Override
  public void flip() {
    // Do nothing
  }
}
