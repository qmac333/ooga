package ooga.model.cards;

import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.PlayerInterface;

/**
 * Card that allows user to choose color and makes the next player draw 2
 *
 * @author Paul Truitt
 */
public class WildDrawTwoCard extends OneSidedCard {

  private final int DRAW_AMOUNT = 2;

  public WildDrawTwoCard(String color) {
    super("Black", "WildDrawTwo", 50);
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
