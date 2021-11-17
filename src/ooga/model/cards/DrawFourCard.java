package ooga.model.cards;

import ooga.model.gameState.GameStatePlayerInterface;

public class DrawFourCard extends Card {

  private final int DRAW_AMOUNT = 4;

  public DrawFourCard(String color) {
    super("Black", "DrawFour", -1);
  }

  @Override
  public void executeAction(GameStatePlayerInterface game) {
    game.addDraw(DRAW_AMOUNT);
    // FIXME: Once we have the interface figured out
    // super.setCardColor(consumerInterface.getColorInput())
    game.discardCard(this);
  }
}
