package ooga.model.cards;

import ooga.model.gameState.GameStatePlayerInterface;

public class ReverseCard extends Card {

  public ReverseCard(String color) {
    super(color, "Reverse", 20);
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
