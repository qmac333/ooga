package ooga.model.cards;

import ooga.model.gameState.GameStatePlayerInterface;

public class NumberCard extends Card {

  public NumberCard(String color, int num) {
    super(color, "Number", num);
  }

  @Override
  public void executeAction(GameStatePlayerInterface game) {
    game.discardCard(this);
  }

  @Override
  public void flip() {
    // Do Nothing
  }
}
