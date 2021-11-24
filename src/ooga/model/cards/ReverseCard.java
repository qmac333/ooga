package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameStatePlayerInterface;

public class ReverseCard extends Card {

  public ReverseCard(String color, Supplier<String> supplier) {
    super(color, "Reverse", 20, supplier);
  }

  @Override
  public void executeAction(GameStatePlayerInterface game) {
    game.reverseGamePlay();
    game.discardCard(this);
  }

  @Override
  public void flip() {
    // Do nothing
  }
}
