package ooga.model.cards;

import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.PlayerInterface;

public class WildCard extends OneSidedCard {

  public WildCard(String color) {
    super(color, "Wild", 50);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    // Do Nothing, Deprecated
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void executeAction(PlayerInterface player) {
    super.setCardColor(player.getColor());
  }
}
