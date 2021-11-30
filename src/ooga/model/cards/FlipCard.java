package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameStatePlayerInterface;

public class FlipCard extends Card{

  public FlipCard(String color, Supplier<String> supplier) {
    super(color, "Flip", 20, supplier);
  }

  @Override
  public void executeAction(GameStatePlayerInterface game) {
    game.flipCards();
    game.discardCard(this);
  }

  @Override
  public void flip() {
    // Do nothing
  }
}
