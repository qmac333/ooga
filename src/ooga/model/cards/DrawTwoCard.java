package ooga.model.cards;

import ooga.model.gameState.GameState;

public class DrawTwoCard extends Card {

  private final int DRAW_AMOUNT = 2;

  public DrawTwoCard(GameState g, String color) {
    super(g, color, "DrawTwo", -1);
  }

  @Override
  public void executeAction() {
    GameState game = super.getGame();
    game.addDraw(DRAW_AMOUNT);
    game.discardCard(this);
  }
}
