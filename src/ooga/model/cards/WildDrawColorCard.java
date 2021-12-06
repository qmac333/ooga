package ooga.model.cards;

import ooga.model.cards.OneSidedCard;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.player.PlayerCardInterface;

/**
 * Card that lets the user pick a color and then have the next player draw until the draw a card of
 * the correct color
 *
 * @author Paul Truitt
 */
public class WildDrawColorCard extends OneSidedCard {

  private final int DRAW_AMOUNT = -2;

  public WildDrawColorCard(String color) {
    super(color, "WildDrawColor", 60);
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
  public void executeAction(PlayerCardInterface player) {
    player.enforceDraw(DRAW_AMOUNT);
    super.setCardColor(player.getColor());
  }
}
