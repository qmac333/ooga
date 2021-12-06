package ooga.model.cards.individualCards;

import ooga.model.cards.OneSidedCard;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.player.PlayerCardInterface;

/**
 * Card that makes the next player draw 5 cards
 *
 * @author Paul Truitt
 */
public class DrawFiveCard extends OneSidedCard {

  private final int DRAW_AMOUNT = 5;

  public DrawFiveCard(String color) {
    super(color, "DrawFive", 20);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    game.addDraw(DRAW_AMOUNT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void executeAction(PlayerCardInterface player){
    player.enforceDraw(DRAW_AMOUNT);
  }
}
