package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameState;
import ooga.model.gameState.GameStatePlayerInterface;

public class SkipCard extends Card {

  public SkipCard(String color, Supplier<String> supplier) {
    super(color, "Skip", 20, supplier);
  }

  @Override
  public void executeAction(GameStatePlayerInterface game) {
    game.skipNextPlayer();
    game.discardCard(this);
  }

  @Override
  public void flip() {
    // Do nothing
  }
}
