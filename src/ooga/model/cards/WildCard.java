package ooga.model.cards;

import ooga.model.gameState.GameState;

public class WildCard extends Card {

  public WildCard(GameState g, String color) {
    super(g, "Black", "Wild", -1);
  }

  @Override
  public void executeAction() {
//    super.setCardColor(consumerInterface.getColorInput())
    super.getGame().discardCard(this);
  }
}
