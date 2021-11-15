package ooga.model.cards;

import ooga.model.gameState.GameState;

public class WildCard extends ActionCard {

  public WildCard(GameState g, String color, String type) {
    super(g, color, type);
  }

  @Override
  public void executeAction() {
//    super.setCardColor(consumerInterface.getColorInput())
    super.getGame().discardCard(this);
  }
}
