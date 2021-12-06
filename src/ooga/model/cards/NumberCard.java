package ooga.model.cards;

import ooga.model.cards.OneSidedCard;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.player.PlayerCardInterface;

public class NumberCard extends OneSidedCard {

  public NumberCard(String color, int num) {
    super(color, "Number", num);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    game.discardCard(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void executeAction(PlayerCardInterface player) {
    // Do nothing
  }
}
