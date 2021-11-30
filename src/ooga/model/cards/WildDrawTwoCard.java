package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameStatePlayerInterface;

public class WildDrawTwoCard extends Card{
  private final int DRAW_AMOUNT = 2;

  public WildDrawTwoCard(String color, Supplier<String> supplier) {
    super("Black", "WildDrawTwo", 50, supplier);
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
