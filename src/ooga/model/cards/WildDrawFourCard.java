package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.PlayerInterface;

public class WildDrawFourCard extends Card {

  private final int DRAW_AMOUNT = 4;

  public WildDrawFourCard(String color) {
    super("Black", "DrawFour", 50);
  }

  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    game.addDraw(DRAW_AMOUNT);
    super.setCardColor("Red");
  }

  @Override
  public void executeAction(PlayerInterface player) {
    player.enforceDraw(DRAW_AMOUNT);
    super.setCardColor(player.getColor());
  }

  @Override
  public void flip() {
    // Do nothing
  }
}
