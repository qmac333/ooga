package ooga.model.cards;

import ooga.model.gameState.GameState;

public class DrawFourCard extends ActionCard {
  private final int DRAW_AMOUNT = 4;

  public DrawFourCard(GameState g, String color, String type) {
    super(g, color, type);
  }

  @Override
  public void executeAction(){
    GameState game = super.getGame();
    game.addDraw(DRAW_AMOUNT);
    // super.setCardColor(consumerInterface.getColorInput())
    game.discardCard(this);
  }
}
