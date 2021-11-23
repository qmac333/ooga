package ooga.model.cards;

import ooga.model.gameState.GameStatePlayerInterface;

public class WildCard extends Card {

  public WildCard(String color) {
    super("Black", "Wild", 50);
  }

  @Override
  public void executeAction(GameStatePlayerInterface game) {
    // FIXME: Once we have the interface figured out
//    super.setCardColor(consumerInterface.getColorInput())
    game.discardCard(this);
  }

  @Override
  public void flip() {
    // Do nothing
  }
}
