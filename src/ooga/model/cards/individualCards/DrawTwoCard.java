package ooga.model.cards.individualCards;

import ooga.model.cards.OneSidedCard;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.player.PlayerCardInterface;

public class DrawTwoCard extends OneSidedCard {

  private final int DRAW_AMOUNT = 2;

  public DrawTwoCard(String color) {
    super(color, "DrawTwo", 20);
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
  public void executeAction(PlayerCardInterface player) {
    player.enforceDraw(DRAW_AMOUNT);
  }
}
