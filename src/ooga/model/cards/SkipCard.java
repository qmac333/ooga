package ooga.model.cards;

import ooga.model.gameState.GameState;
import ooga.model.gameState.GameStatePlayerInterface;

public class SkipCard extends Card {

  public SkipCard(String color) {
    super(color, "Skip", 20);
  }

  @Override
  public void executeAction(GameStatePlayerInterface game) {
    game.skipNextPlayer();
    game.discardCard(this);
  }
}
