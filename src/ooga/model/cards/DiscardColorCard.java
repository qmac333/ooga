package ooga.model.cards;

import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.PlayerInterface;

public class DiscardColorCard extends OneSidedCard {

  public DiscardColorCard(String color) {
    super(color, "DiscardColor", 20);
  }

  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    // Do nothing
  }

  @Override
  public void executeAction(PlayerInterface player) {
    player.discardColor(super.getMyColor());
  }
}
