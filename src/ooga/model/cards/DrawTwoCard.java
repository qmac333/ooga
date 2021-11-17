package ooga.model.cards;

import ooga.model.gameState.GameState;
import ooga.model.gameState.GameStatePlayerInterface;

public class DrawTwoCard extends Card {

  private final int DRAW_AMOUNT = 2;

  public DrawTwoCard(String color) {
    super(color, "DrawTwo", -1);
  }

  @Override
  public void executeAction(GameStatePlayerInterface game) {
    game.addDraw(DRAW_AMOUNT);
    game.discardCard(this);
  }
}
