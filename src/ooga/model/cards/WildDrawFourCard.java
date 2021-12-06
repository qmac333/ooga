package ooga.model.cards;

import ooga.model.cards.OneSidedCard;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.player.PlayerCardInterface;

public class WildDrawFourCard extends OneSidedCard {

  private final int DRAW_AMOUNT = 4;

  public WildDrawFourCard(String color) {
    super(color, "WildDrawFour", 50);
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
