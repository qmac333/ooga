package ooga.model.cards;

import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.PlayerInterface;

public class DrawTwoCard extends OneSidedCard {

  private final int DRAW_AMOUNT = 2;

  public DrawTwoCard(String color) {
    super(color, "DrawTwo", 20);
  }

  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    game.addDraw(DRAW_AMOUNT);
  }

  @Override
  public void executeAction(PlayerInterface player) {
    player.enforceDraw(DRAW_AMOUNT);
  }
}
