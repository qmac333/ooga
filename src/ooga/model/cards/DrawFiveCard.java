package ooga.model.cards;

import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.PlayerInterface;

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

  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    game.addDraw(DRAW_AMOUNT);
  }

  @Override
  public void executeAction(PlayerInterface player){
    player.enforceDraw(DRAW_AMOUNT);
  }
}
