package ooga.model.cards;

import ooga.model.cards.OneSidedCard;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.player.PlayerCardInterface;

public class SkipCard extends OneSidedCard {

  public SkipCard(String color) {
    super(color, "Skip", 20);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    game.skipNextPlayer();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void executeAction(PlayerCardInterface player) {
    player.skipNextPlayer();
  }
}
