package ooga.model.cards.individualCards;

import ooga.model.cards.OneSidedCard;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.player.PlayerCardInterface;

/**
 * Card to make every Player flip their hands
 *
 * @author Paul Truitt
 */
public class FlipCard extends OneSidedCard {

  public FlipCard(String color) {
    super(color, "Flip", 20);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    game.flipCards();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void executeAction(PlayerCardInterface player) {
    player.flipGame();
  }
}
