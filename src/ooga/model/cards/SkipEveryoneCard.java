package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameStatePlayerInterface;

public class SkipEveryoneCard extends Card {

  public SkipEveryoneCard(String color, Supplier<String> supplier) {
    super(color, "SkipEveryone", 30, supplier);
  }

  @Override
  public void executeAction(GameStatePlayerInterface game) {
    game.skipEveryone();
    game.discardCard(this);
  }

  @Override
  public void flip() {
    // Do nothing
  }
}
