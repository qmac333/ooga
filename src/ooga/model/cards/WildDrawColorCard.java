package ooga.model.cards;

import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.PlayerInterface;

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
    game.addDraw(DRAW_AMOUNT);
    super.setCardColor("Red");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void executeAction(PlayerInterface player) {
    player.enforceDraw(DRAW_AMOUNT);
    super.setCardColor(player.getColor());
  }
}
