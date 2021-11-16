package ooga.model.cards;

import ooga.model.gameState.GameState;

public class NumberCard extends Card {

  public NumberCard(GameState g, String color, int num) {
    super(g, color, "Number", num);
  }

  @Override
  public void executeAction() {
    super.getGame().discardCard(this);
  }
}
