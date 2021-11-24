package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameState;
import ooga.model.gameState.GameStatePlayerInterface;

public class DrawTwoCard extends Card {

  private final int DRAW_AMOUNT = 2;

  public DrawTwoCard(String color, Supplier<String> supplier) {
    super(color, "DrawTwo", 20, supplier);
  }

  @Override
  public void executeAction(GameStatePlayerInterface game) {
    game.addDraw(DRAW_AMOUNT);
    game.discardCard(this);
  }

  @Override
  public void flip() {
    // Do nothing
  }
}
