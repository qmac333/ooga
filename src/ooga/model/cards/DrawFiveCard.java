package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameStatePlayerInterface;

/**
 * Card that makes the next player draw 5 cards
 *
 * @author Paul Truitt
 */
public class DrawFiveCard extends Card {

  private final int DRAW_AMOUNT = 5;

  public DrawFiveCard(String color, Supplier<String> supplier) {
    super(color, "DrawFive", 20, supplier);
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
